package io.github.sugarmgp.eos.item.armor;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;

public class ItemFunnyBoots extends ArmorItem {
    public ItemFunnyBoots() {
        super(ModArmorMaterial.FUNNY, EquipmentSlotType.FEET, new Properties().group(EOS.ITEMGROUP));
    }
}
