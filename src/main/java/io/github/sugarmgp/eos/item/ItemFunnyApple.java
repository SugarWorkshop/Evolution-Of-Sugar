package io.github.sugarmgp.eos.item;

import io.github.sugarmgp.eos.EOS;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemFunnyApple extends Item {
    private static final Food FUNNY_APPLE = new Food.Builder()
            .saturation(1.2f)
            .hunger(4)
            .effect(new EffectInstance(Effects.REGENERATION, 300, 1), 1)
            .effect(new EffectInstance(Effects.RESISTANCE, 4000, 0), 1)
            .setAlwaysEdible()
            .build();

    public ItemFunnyApple() {
        super(new Properties().food(FUNNY_APPLE).rarity(Rarity.RARE).group(EOS.ITEMGROUP));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        float health = entityLiving.getHealth();
        entityLiving.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 2400, 2));
        entityLiving.setHealth(health); //防止添加效果后重置血量
        return itemstack;
    }
}
