package io.github.sugarmgp.eos.item.tool;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModItemTier;
import net.minecraft.item.AxeItem;

public class ItemCuckooAxe extends AxeItem {
    public ItemCuckooAxe() {
        super(
                ModItemTier.CUCKOO,
                5.0F,
                -3.0F,
                new Properties().group(EOS.ITEMGROUP)
        );
    }
}
