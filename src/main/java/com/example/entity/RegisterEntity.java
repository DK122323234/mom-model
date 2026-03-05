package com.example.entity;

import com.example.MomModelMod;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;

public class RegisterEntity {
    public static final EntityType<MomEntity> MOM = registerEntityType("mom", EntityType.Builder.create(MomEntity::new, SpawnGroup.CREATURE).setDimensions(0.6f, 1.8f).build("mom"));

    private static EntityType<MomEntity> registerEntityType(String name, EntityType entityType){
         return Registry.register(Registries.ENTITY_TYPE, new Identifier(MomModelMod.MODID, name), entityType);
    }
    public static void register() {}
}
