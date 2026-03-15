package com.example.entity;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

import static com.mojang.text2speech.Narrator.LOGGER;

public class MomGoal extends Goal {
    MomEntity mom;
    MomGoal(MomEntity mom) {
        this.mom = mom;
    }


    @Override
    public boolean canStart() {
        World world  = this.mom.getWorld();
        PlayerEntity player = world.getPlayerByUuid(this.mom.getUuid());
        return radiusCheck(5, world, Items.DIAMOND, player);
    }
    @Override
    public void start() {
        LOGGER.info("Мама рада");
    }

    private static boolean radiusCheck(double radius, World world, Item targetItem, PlayerEntity player) {
        Box searchBox = player.getBoundingBox().expand(radius);
        List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class,searchBox,(ItemEntity) -> {
            return ItemEntity.getStack().isOf(targetItem);
        });
        return !items.isEmpty();
    }
}
