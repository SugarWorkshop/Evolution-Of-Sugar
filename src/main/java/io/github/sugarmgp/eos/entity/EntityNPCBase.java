package io.github.sugarmgp.eos.entity;

import com.google.common.base.Predicate;
import io.github.sugarmgp.eos.handler.ItemHandler;
import io.github.sugarmgp.eos.util.EnumNPCRank;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityNPCBase extends TameableEntity {
    private static final DataParameter<Integer> RANK = EntityDataManager.createKey(EntityNPCBase.class, DataSerializers.VARINT);

    public EntityNPCBase(EntityType<? extends TameableEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
        this.setTamed(false);
        this.setExperiencePoints();
        this.setItem(this.getRank().getHand(), this.getRank().getFeet());
        this.changeAttributes();
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.7D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    protected void changeAttributes() { //在生成后覆盖属性
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getRank().getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getRank().getAttackDamage());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getRank().getMovementSpeed());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.625D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 0.525D, 8, 2, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.31D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 5));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, LivingEntity.class, 7, true, false, new Predicate<LivingEntity>() {
            public boolean apply(@Nullable LivingEntity entity) {
                return entity instanceof IMob && !entity.isInvisible(); //选择怪物进行攻击
            }
        }));
        this.targetSelector.addGoal(5, new ResetAngerGoal(this, true));
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (item.equals(ItemHandler.itemFunnyApple.get())) {
            if (this.isTamed()) {
                if (this.getHealth() < this.getMaxHealth()) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    int heal = item.getFood().getHealing();
                    this.heal(heal);
                    this.playEffect(ParticleTypes.HEART, this.getPosX(), this.getPosY() + 0.425, this.getPosZ(), 4);
                }
            } else {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.setAttackTarget(null);
                    this.playEffect(ParticleTypes.HAPPY_VILLAGER, this.getPosX(), this.getPosY() + 0.425, this.getPosZ(), 8);
                }
            }
        }
        return super.func_230254_b_(player, hand);
    }

    protected void playEffect(BasicParticleType particleTypes, Double posX, Double posY, Double posZ, int times) {
        if (!world.isRemote()) return;
        for (int i = 1; i <= times; ++i) {
            double d0 = this.rand.nextGaussian() * 0.015; //白糖自研随机算法
            double d1 = this.rand.nextGaussian() * 0.015;
            double d2 = this.rand.nextGaussian() * 0.015;
            this.world.addParticle(particleTypes,
                    posX + this.rand.nextDouble() * this.getWidth() * 1.5 - this.getWidth(),
                    posY + this.rand.nextDouble() * this.getHeight() * 0.8,
                    posZ + this.rand.nextDouble() * this.getWidth() * 1.5 - this.getWidth(),
                    d0, d1, d2
            );
        }
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) { //从WolfEntity修改来的
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof EntityNPCBase) {
                EntityNPCBase entity = (EntityNPCBase) target;
                return !entity.isTamed() || entity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();

        BasicParticleType particleType = this.getRank().getParticleType();
        if (particleType != null && (this.getMotion().x != 0.0D || this.getMotion().z != 0.0D)) { //在移动时播放粒子效果
            this.playEffect(particleType, this.getPosX(), this.getPosY() - 0.45, this.getPosZ(), 1);
        }

        int regenerationLevel = this.getRank().getRegenerationLevel();
        if (!this.isPotionActive(Effects.REGENERATION) && regenerationLevel >= 0) { //给NPC添加生命恢复
            this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 72000, regenerationLevel, false, false));
        }

        super.livingTick();
    }

    protected void setItem(Item handIn, Item feetIn) {
        ItemStack hand = new ItemStack(handIn);
        ItemStack feet = new ItemStack(feetIn);
        hand.addEnchantment(Enchantments.UNBREAKING, 50);
        feet.addEnchantment(Enchantments.UNBREAKING, 50);
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, hand);
        this.setItemStackToSlot(EquipmentSlotType.FEET, feet);
    }

    @Override
    protected void spawnDrops(DamageSource damageSourceIn) {
        ItemStack hand = new ItemStack(this.getRank().getHand());
        ItemStack feet = new ItemStack(this.getRank().getFeet());
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, hand); //在掉落时替换成无附魔的物品
        this.setItemStackToSlot(EquipmentSlotType.FEET, feet);
        super.spawnDrops(damageSourceIn);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return this.experienceValue + (int) (new Random().nextInt(10) / 9.0 * 5);
    }

    public EnumNPCRank getRank() {
        return EnumNPCRank.getByKey(this.dataManager.get(RANK));
    }

    protected void setExperiencePoints() {
        this.experienceValue = this.getRank().getExperienceValue();
    }

    @Override
    public EntityNPCBase func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        EnumNPCRank rank = EnumNPCRank.randomGet(); //随机选择Rank
        this.dataManager.register(RANK, rank.getKey());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(RANK, compound.getInt("Rank"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Rank", this.dataManager.get(RANK));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
