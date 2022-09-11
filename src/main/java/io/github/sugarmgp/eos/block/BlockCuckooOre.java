package io.github.sugarmgp.eos.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockCuckooOre extends OreBlock {
    public BlockCuckooOre() {
        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(2.5F)
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
