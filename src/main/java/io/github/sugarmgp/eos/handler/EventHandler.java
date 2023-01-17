package io.github.sugarmgp.eos.handler;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && !entity.world.isRemote) {
            PlayerEntity player = (PlayerEntity) entity;
            String message = "eos.welcome";
            TranslationTextComponent text = new TranslationTextComponent(message, player.getDisplayName());
            player.sendStatusMessage(text, false);
        }
    }

    @SubscribeEvent
    public void onSetup(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("torcherino") || ModList.get().isLoaded("projecte")) {
            CrashReport report = CrashReport.makeCrashReport(new IllegalAccessError(),
                    "You have ENRAGED the FOREST BAT because some mods are loaded");
            throw new ReportedException(report);
        }
    }
}
