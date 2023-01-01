package io.github.sugarmgp.eos.item.tool;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModItemTier;
import net.minecraft.item.ShovelItem;

public class ItemCuckooShovel extends ShovelItem {
    public ItemCuckooShovel() {
        super(
                ModItemTier.CUCKOO,
                1.5F,
                -3.0F,
                new Properties().group(EOS.ITEMGROUP)
        );
    }
}
