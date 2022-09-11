package io.github.sugarmgp.eos.item.tool;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.util.ModItemTier;
import net.minecraft.item.PickaxeItem;

public class ItemCuckooPickaxe extends PickaxeItem implements ICuckooTool {
    public ItemCuckooPickaxe() {
        super(
                ModItemTier.CUCKOO,
                1,
                -2.8F,
                new Properties().group(EOS.ITEMGROUP)
        );
    }
}
