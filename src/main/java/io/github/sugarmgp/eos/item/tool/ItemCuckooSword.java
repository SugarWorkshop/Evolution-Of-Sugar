package io.github.sugarmgp.eos.item.tool;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModItemTier;
import net.minecraft.item.SwordItem;

public class ItemCuckooSword extends SwordItem implements ICuckooTool {
    public ItemCuckooSword() {
        super(
                ModItemTier.CUCKOO,
                3,
                -2.4F,
                new Properties().group(EOS.ITEMGROUP)
        );
    }
}
