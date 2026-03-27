package com.example.sound;


import com.example.MomModelMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class RegisterSound {
    public static final SoundEvent MOM_SOUND = registerSound("mom_sound");

    private static SoundEvent registerSound(String name){
        Identifier identifier = new Identifier(MomModelMod.MODID, name);
    return Registry.register(Registries.SOUND_EVENT, identifier ,SoundEvent.of(identifier));
    }

    public static void register(){

    }
}
