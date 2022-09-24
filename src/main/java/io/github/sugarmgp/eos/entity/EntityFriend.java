package io.github.sugarmgp.eos.entity;

import com.google.common.base.Predicate;
import io.github.sugarmgp.eos.handler.ItemHandler;
import io.github.sugarmgp.eos.util.EnumFriendMembers;
import io.github.sugarmgp.eos.util.EnumFriendRanks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityFriend extends TameableEntity {
    private static final DataParameter<Integer> RANK = EntityDataManager.createKey(EntityFriend.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MEMBER = EntityDataManager.createKey(EntityFriend.class, DataSerializers.VARINT);

    public EntityFriend(EntityType<? extends TameableEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
        this.setTamed(false);
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.7D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
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
            if (target instanceof EntityFriend) {
                EntityFriend entity = (EntityFriend) target;
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
        if (regenerationLevel >= 0 && !this.isPotionActive(Effects.REGENERATION)) { //给NPC添加生命恢复
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

    public EnumFriendRanks getRank() {
        return EnumFriendRanks.getByKey(this.dataManager.get(RANK));
    }

    protected void setRank(int rankIn) {
        this.dataManager.set(RANK, rankIn);
        EnumFriendRanks rank = EnumFriendRanks.getByKey(rankIn);
        this.experienceValue = rank.getExperienceValue();
        this.setItem(rank.getHand(), rank.getFeet());
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(rank.getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(rank.getAttackDamage());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(rank.getMovementSpeed());
    }

    public EnumFriendMembers getMember() {
        return EnumFriendMembers.getByKey(this.dataManager.get(MEMBER));
    }

    protected void setMember(int memberIn) {
        this.dataManager.set(MEMBER, memberIn);
        this.setCustomName(ITextComponent.getTextComponentOrEmpty(EnumFriendMembers.getByKey(memberIn).name()));
    }

    @Override
    public EntityFriend func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RANK, 2);
        this.dataManager.register(MEMBER, 0);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(RANK, compound.getInt("Rank"));
        this.dataManager.set(MEMBER, compound.getInt("Member"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Rank", this.dataManager.get(RANK));
        compound.putInt("Member", this.dataManager.get(MEMBER));
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableEntity.AgeableData(false);
        }
        int rank = EnumFriendRanks.randomGetKey(this.rand);
        int member = EnumFriendMembers.randomGetKey(this.rand);
        this.setRank(rank);
        this.setMember(member);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
