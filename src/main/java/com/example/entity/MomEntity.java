package com.example.entity;

import com.example.sound.RegisterSound;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static software.bernie.geckolib.constant.DefaultAnimations.WALK;


public class MomEntity extends TameableEntity implements GeoEntity {
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MomEntity(EntityType<? extends MobEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>) entityType, world);
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
            return null;
        }));
        ;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new FollowOwnerGoal (this, 1.2D, 1, 1, false));

        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }
    @Override

    public void travel(net.minecraft.util.math.Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity player) {
                this.setYaw(player.getYaw());
                this.prevYaw = this.getYaw();
                this.setPitch(player.getPitch() * 0.5F);
                this.setRotation(this.getYaw(), this.getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
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
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.DIAMOND) && !this.isTamed()) {
            if (!this.getWorld().isClient) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
                    this.setSitting(false);
                } else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        if (itemStack.isEmpty() && !this.isSitting()) {
            if (!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
    @Nullable
    @Override
    public LivingEntity getOwner() {
        try {
            return super.getOwner();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.age % 100 == 0) {
            this.getWorld().playSound(null,
                    this.getBlockPos(),
                    RegisterSound.MOM_SOUND,
                    SoundCategory.NEUTRAL,
                    1.0f,
                    1.0f);
        }
    }
    protected void jump() {
        super.jump();

        if (this.getWorld().isClient()) {
            for (int i = 0; i < 8; i++) {
                this.getWorld().addParticle(
                        ParticleTypes.FLAME,
                        this.getX() + (this.random.nextDouble() - 0.5) * 0.5, // Немного разброса по X
                        this.getY(),                                         // У ног
                        this.getZ() + (this.random.nextDouble() - 0.5) * 0.5, // Немного разброса по Z
                        0.0, 0.1, 0.0                                        // Скорость вверх
                );
            }
        }
    }
}
