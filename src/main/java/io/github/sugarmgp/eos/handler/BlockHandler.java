package io.github.sugarmgp.eos.handler;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.block.BlockCuckooBlock;
import io.github.sugarmgp.eos.block.BlockCuckooOre;
import io.github.sugarmgp.eos.block.BlockFunnyBlock;
import io.github.sugarmgp.eos.block.BlockFunnyOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockHandler {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EOS.MODID);

    public static RegistryObject<Block> blockFunnyOre = BLOCKS.register("funny_ore", BlockFunnyOre::new);
    public static RegistryObject<Block> blockCuckooOre = BLOCKS.register("cuckoo_ore", BlockCuckooOre::new);
    public static RegistryObject<Block> blockFunnyBlock = BLOCKS.register("funny_block", BlockFunnyBlock::new);
    public static RegistryObject<Block> blockCuckooBlock = BLOCKS.register("cuckoo_block", BlockCuckooBlock::new);
}
