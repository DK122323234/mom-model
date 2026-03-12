package com.example.entity;

import com.example.MomModelMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MomRender extends GeoEntityRenderer<MomEntity> {


    public MomRender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MomModel());
    }

    @Override
    public Identifier getTextureLocation(MomEntity animatable) {
        return new Identifier(MomModelMod.MODID, "textures/entity/mom2.png");
    }
}