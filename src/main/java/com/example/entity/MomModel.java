package com.example.entity;

import com.example.MomModelMod;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MomModel extends GeoModel<MomEntity> {
    @Override
    public Identifier getModelResource(MomEntity momEntity) {
        return new Identifier(MomModelMod.MODID, "geo/mom.geo.json");
    }

    @Override
    public Identifier getTextureResource(MomEntity momEntity) {
        return new Identifier(MomModelMod.MODID, "textures/entity/mom2.png");
    }

    @Override
    public Identifier getAnimationResource(MomEntity momEntity) {
        return new Identifier(MomModelMod.MODID, "animations/mom.animation.json");
    }
}
