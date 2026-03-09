package com.example.entity;

import com.example.MomModelMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegisterEntity {
    public static final EntityType<MomEntity> MOM_ENTITY = registerEntityType("mom", EntityType.Builder.create(MomEntity::new, SpawnGroup.CREATURE).setDimensions(0.6f, 1.8f).build("mom"));

    private static EntityType<MomEntity> registerEntityType(String name, EntityType entityType){
         return Registry.register(Registries.ENTITY_TYPE, new Identifier(MomModelMod.MODID, name), entityType);
    }
    public static void register() {
        MomModelMod.LOGGER.info("Registering entities for " + MomModelMod.MODID);
        FabricDefaultAttributeRegistry.register(RegisterEntity.MOM_ENTITY, MomEntity.createMobAttributes());


    }
}
