package io.github.sugarmgp.eos;

import io.github.sugarmgp.eos.handler.BlockHandler;
import io.github.sugarmgp.eos.handler.ConfigHandler;
import io.github.sugarmgp.eos.handler.EntityHandler;
import io.github.sugarmgp.eos.handler.ItemHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EOS.MODID)
public class EOS {
    public static final String MODID = "eos";
    public static final ItemGroup ITEMGROUP = new EOSItemGroup();
    private static final Logger LOGGER = LogManager.getLogger("Evolution Of Sugar");

    public EOS() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemHandler.ITEMS.register(bus);
        BlockHandler.BLOCKS.register(bus);
        EntityHandler.ENTITY_TYPES.register(bus);
        bus.addListener(this::onSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.commonConfig);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void onSetup(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("torcherino") || ModList.get().isLoaded("projecte")) {
            CrashReport report = CrashReport.makeCrashReport(new IllegalAccessError(),
                    "The FOREST BAT is ANGRY because some mods are loaded");
            throw new ReportedException(report);
        }
    }
}