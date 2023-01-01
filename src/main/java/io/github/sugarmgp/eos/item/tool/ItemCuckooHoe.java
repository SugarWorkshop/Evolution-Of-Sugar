package io.github.sugarmgp.eos.item.tool;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModItemTier;
import net.minecraft.item.HoeItem;

public class ItemCuckooHoe extends HoeItem {
    public ItemCuckooHoe() {
        super(
                ModItemTier.CUCKOO,
                -3,
                0.0F,
                new Properties().group(EOS.ITEMGROUP)
        );
    }
}
