package io.github.sugarmgp.eos.item;

import io.github.sugarmgp.eos.EOS;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemFunnyApple extends Item {
    private static final Food food = new Food.Builder()
            .saturation(0.8f)
            .hunger(3)
            .effect(() -> new EffectInstance(Effects.REGENERATION, 70, 4), 1)
            .setAlwaysEdible()
            .build();

    public ItemFunnyApple() {
        super(new Properties().food(food).group(EOS.ITEMGROUP));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        float health = entityLiving.getHealth();
        entityLiving.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 120, 3));
        entityLiving.setHealth(health); //防止添加效果后重置血量
        return itemstack;
    }
}
