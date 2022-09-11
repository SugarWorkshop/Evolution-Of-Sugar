package io.github.sugarmgp.eos.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class BlockCuckooBlock extends Block {
    public BlockCuckooBlock() {
        super(Properties
                .create(Material.IRON, MaterialColor.IRON)
                .hardnessAndResistance(4, 6)
                .sound(SoundType.METAL)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .setRequiresTool()
        );
    }
}
