package io.github.sugarmgp.eos.item.armor;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModArmorMaterial;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class ItemFunnyHelmet extends ArmorItem {
    public ItemFunnyHelmet() {
        super(ModArmorMaterial.FUNNY, EquipmentSlotType.HEAD, new Properties().group(EOS.ITEMGROUP));
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
