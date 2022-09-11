package io.github.sugarmgp.eos.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockFunnyOre extends OreBlock {
    public BlockFunnyOre() {
        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .setRequiresTool()
        );
    }

    @Override
    protected int getExperience(Random rand) {
        return MathHelper.nextInt(rand, 2, 6);
    }
}
