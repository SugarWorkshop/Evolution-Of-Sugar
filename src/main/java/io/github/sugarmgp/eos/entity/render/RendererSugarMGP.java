package io.github.sugarmgp.eos.entity.render;

import io.github.sugarmgp.eos.EOS;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RendererSugarMGP extends RendererNPCBase {
    public RendererSugarMGP(EntityRendererManager managerIn) {
        super(managerIn, false);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(EOS.MODID, "textures/entity/sugarmgp.png");
    }
}
