package com.example.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static software.bernie.geckolib.constant.DefaultAnimations.WALK;


public class MomEntity extends MobEntity implements GeoEntity {
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MomEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes().add(
                        EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> {
            if (state.isMoving() || (this.hasPassengers() && this.getVelocity().horizontalLengthSquared() > 0.001)) {
                return state.setAndContinue(WALK_ANIM);
            }
            return PlayState.CONTINUE;
        }));
        ;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MomGoal(this));

    }
    @Override
    public void travel(net.minecraft.util.math.Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity player) {

                float sideways = player.sidewaysSpeed;
                float forward = player.forwardSpeed;

                if (forward <= 0.0F) {
                        forward = forward * 0.5F;
                }

                this.setMovementSpeed(0.5F);

                super.travel(new net.minecraft.util.math.Vec3d(sideways, movementInput.y, forward));

            } else {
                super.travel(movementInput);
            }
        }
        }

    @Override
    @org.jetbrains.annotations.Nullable
    public net.minecraft.entity.LivingEntity getControllingPassenger() {
        return (net.minecraft.entity.LivingEntity) this.getFirstPassenger();
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            return player.startRiding(this) ? ActionResult.SUCCESS : ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}
