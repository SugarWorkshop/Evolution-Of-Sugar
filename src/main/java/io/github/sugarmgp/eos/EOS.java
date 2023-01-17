package io.github.sugarmgp.eos;

import io.github.sugarmgp.eos.handler.BlockHandler;
import io.github.sugarmgp.eos.handler.ConfigHandler;
import io.github.sugarmgp.eos.handler.EntityHandler;
import io.github.sugarmgp.eos.handler.ItemHandler;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EOS.MODID)
public class EOS {
    public static final String MODID = "eos";
    public static final ItemGroup ITEMGROUP = new EOSItemGroup();
    private static final Logger LOGGER = LogManager.getLogger("Evolution Of Sugar");

    public EOS() {
        ItemHandler.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockHandler.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityHandler.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.commonConfig);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}