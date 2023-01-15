package io.github.sugarmgp.eos.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.entity.EntityFriend;
import io.github.sugarmgp.eos.entity.layer.LayerArrow;
import io.github.sugarmgp.eos.entity.model.ModelFriend;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererFriend extends LivingRenderer {
    private boolean slim = false;

    public RendererFriend(EntityRendererManager managerIn) {
        super(managerIn, new ModelFriend(0.0F, false), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new HeadLayer<>(this));
    }

    @Override
    public void render(LivingEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        boolean slimIn = ((EntityFriend) entityIn).getMember().getSlim();
        if (slimIn != this.slim) {
            this.slim = slimIn;
            this.entityModel = new ModelFriend(0.0F, slimIn);
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntityIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        EntityFriend friend = (EntityFriend) entity;
        String path = "textures/entity/" + friend.getMember().getId() + ".png";
        return new ResourceLocation(EOS.MODID, path);
    }
}
