package io.github.sugarmgp.eos.item.armor;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;

public class ItemFunnyHelmet extends ArmorItem {
    public ItemFunnyHelmet() {
        super(ModArmorMaterial.FUNNY, EquipmentSlotType.HEAD, new Properties().group(EOS.ITEMGROUP));
    }
}
