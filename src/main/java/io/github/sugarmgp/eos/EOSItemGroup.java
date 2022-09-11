package io.github.sugarmgp.eos;

import io.github.sugarmgp.eos.handler.ItemHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class EOSItemGroup extends ItemGroup {

    public EOSItemGroup() {
        super("eos_group");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemHandler.itemCuckooIngot.get());
    }
}
