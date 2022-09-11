package io.github.sugarmgp.eos.item.armor;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;

public class ItemFunnyChestplate extends ArmorItem {
    public ItemFunnyChestplate() {
        super(ModArmorMaterial.FUNNY, EquipmentSlotType.CHEST, new Properties().group(EOS.ITEMGROUP));
    }
}
