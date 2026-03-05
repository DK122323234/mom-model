package com.example;

import com.example.entity.RegisterEntity;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MomModelMod implements ModInitializer {
    public static final String MODID = "mom_model";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        RegisterEntity.register();
    }
}