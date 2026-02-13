package mod.reborn.server.entity;

import mod.reborn.client.model.animation.EntityAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * Crocodile-like dinosaur base.
 * Adds: death-roll w/ cooldown + DoT, ambush lunge in water, idle basking on land, and tail sweep on land.
 * FIX: During BASKING on land, entity is fully immobilized (no creeping).
 */
public abstract class CrocodileDinosaurEntity extends AmfibianDinosaurEntity {
    // --- Tunables ---
    private static final int DEATH_ROLL_COOLDOWN_T = 100; // ~5s
    private static final float DEATH_ROLL_DOT_DAMAGE = 2.0F; // per second while rolling
    private static final int BASKING_RAND = 500; // 1/500 chance per tick to start basking while idle
    private static final float AMBUSH_STEALTH_MULT = 0.6F; // slow approach before strike
    private static final float AMBUSH_LUNGE_MULT = 1.6F;   // burst when close
    private static final double AMBUSH_TRIGGER_DIST = 4.0D;
    private static final float TAIL_SWEEP_KNOCKBACK = 1.2F;
    private static final float TAIL_SWEEP_CHANCE = 0.25F;
    private static final int TAIL_SWEEP_COOLDOWN_T = 60; // ~3s

    // --- State ---
    private int deathRollCooldown = 0;
    private int tailSweepCooldown = 0;

    public CrocodileDinosaurEntity(World world) {
        super(world);
    }

    private boolean isBaskingNow() {
        if (this.world.isRemote) {
            return this.getAnimation() == EntityAnimation.RESTING.get();
        }
        return this.getAnimation() == EntityAnimation.RESTING.get()
                && !this.isInWater()
                && this.onGround
                && this.getAttackTarget() == null;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (deathRollCooldown > 0) {
            deathRollCooldown--;
        }
        if (tailSweepCooldown > 0) {
            tailSweepCooldown--;
        }

        if (!this.world.isRemote
                && this.getAnimation() == EntityAnimation.RESTING.get()
                && this.ticksExisted % 80 == 0) {
            this.playSound(SoundEvents.ENTITY_GENERIC_SWIM, 0.15F, 0.8F + this.rand.nextFloat() * 0.4F);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.getAnimation() == EntityAnimation.RESTING.get()) {
            if (this.getAttackTarget() != null || this.isInWater() || !this.onGround || this.hurtTime > 0) {
                this.setAnimation(EntityAnimation.IDLE.get());
            }
        }

        if (isBaskingNow()) {
            this.getNavigator().clearPath();
            this.getMoveHelper().setMoveTo(this.posX, this.posY, this.posZ, 0.0D);
            this.setAIMoveSpeed(0.0F);
            this.setSprinting(false);
            this.isJumping = false;
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;

            this.motionX = 0.0D;
            this.motionZ = 0.0D;
            this.velocityChanged = false;
        }

        if (!this.world.isRemote
                && this.isInWater()
                && this.getAnimation() == EntityAnimation.SNAP.get()
                && this.getAttackTarget() != null
                && this.getAttackTarget().isEntityAlive()) {
            if (this.ticksExisted % 20 == 0) {
                EntityLivingBase target = this.getAttackTarget();
                target.attackEntityFrom(DamageSource.causeMobDamage(this), DEATH_ROLL_DOT_DAMAGE);
                Vec3d push = this.getLookVec().scale(0.15D).addVector(0, 0.02D, 0);
                target.motionX += push.x;
                target.motionY += push.y;
                target.motionZ += push.z;
            }
        }

        if (!this.world.isRemote
                && this.getAttackTarget() == null
                && !this.isInWater()
                && this.onGround
                && this.getAnimation() == EntityAnimation.IDLE.get()) {

            boolean canSeeSky = this.world.canSeeSky(this.getPosition());
            int sky = this.world.getLightFor(EnumSkyBlock.SKY, this.getPosition());
            boolean bright = sky > 10;

            if (canSeeSky && bright && this.rand.nextInt(BASKING_RAND) == 0) {
                this.setAnimation(EntityAnimation.RESTING.get());
                this.playSound(SoundEvents.BLOCK_SAND_PLACE, 0.2F, 0.9F + this.rand.nextFloat() * 0.2F);
            }
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (isBaskingNow()) {
            return;
        }

        if (this.isInWater() && this.getAttackTarget() != null) {
            double distance = this.getDistance(this.getAttackTarget());
            double base = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null
                    ? this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() : 0.1D;

            if (distance < AMBUSH_TRIGGER_DIST) {
                this.setAIMoveSpeed((float) (base * AMBUSH_LUNGE_MULT));
                if (this.world.isRemote && this.ticksExisted % 10 == 0) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_SWIM, 0.25F, 0.9F + this.rand.nextFloat() * 0.2F);
                }
            } else {
                this.setAIMoveSpeed((float) (base * AMBUSH_STEALTH_MULT));
            }
        }

        super.travel(strafe, vertical, forward);
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        boolean flag = super.attackEntityAsMob(target);
        if (!flag) {
            return false;
        }

        if (this.isInWater() && deathRollCooldown == 0) {
            this.setAnimation(EntityAnimation.SNAP.get());
            deathRollCooldown = DEATH_ROLL_COOLDOWN_T;
            this.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 0.6F, 0.7F + this.rand.nextFloat() * 0.4F);
        } else if (!this.isInWater() && tailSweepCooldown == 0 && this.rand.nextFloat() < TAIL_SWEEP_CHANCE) {
            this.setAnimation(EntityAnimation.TAIL_DISPLAY.get());
            tailSweepCooldown = TAIL_SWEEP_COOLDOWN_T;

            double dx = this.posX - target.posX;
            double dz = this.posZ - target.posZ;
            double n = Math.sqrt(dx * dx + dz * dz);
            if (n > 0.0001D) {
                dx /= n;
                dz /= n;
                target.addVelocity(dx * TAIL_SWEEP_KNOCKBACK, 0.1D, dz * TAIL_SWEEP_KNOCKBACK);
            }

            this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.4F, 0.8F + this.rand.nextFloat() * 0.3F);
        }
        return true;
    }

    @Override
    public void addVelocity(double x, double y, double z) {
        if (isBaskingNow()) {
            return;
        }
        super.addVelocity(x, y, z);
    }

    @Override
    public boolean canBePushed() {
        return !isBaskingNow() && super.canBePushed();
    }
}