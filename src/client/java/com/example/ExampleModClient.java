package com.example;

import com.example.entity.MomEntity;
import com.example.entity.MomRender;
import com.example.entity.RegisterEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RegisterEntity.MOM_ENTITY, context -> new MomRender(context));
    }
}