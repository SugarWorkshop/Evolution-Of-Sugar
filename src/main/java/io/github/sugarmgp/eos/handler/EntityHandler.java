package io.github.sugarmgp.eos.handler;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.entity.EntityNPCBase;
import io.github.sugarmgp.eos.entity.render.RendererSugarMGP;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityHandler {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, EOS.MODID);
    public static final RegistryObject<EntityType<EntityNPCBase>> entitySugarMGP = ENTITY_TYPES.register("sugarmgp", () -> EntityType.Builder.create(EntityNPCBase::new, EntityClassification.CREATURE).size(0.6F, 1.8F).build("sugarmgp"));

    @SubscribeEvent
    public static void setupAttributes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(EntityHandler.entitySugarMGP.get(), EntityNPCBase.createDefaultAttributes().create());
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupRenderer(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.entitySugarMGP.get(), (EntityRendererManager manager) -> {
            return new RendererSugarMGP(manager);
        });
    }
}
