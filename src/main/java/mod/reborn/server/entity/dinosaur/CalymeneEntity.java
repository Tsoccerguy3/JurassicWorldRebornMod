package mod.reborn.server.entity.dinosaur;

import mod.reborn.RebornMod;
import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.ai.Mutex;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.Random;

public class CalymeneEntity extends SwimmingDinosaurEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(CalymeneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> ROLLED = EntityDataManager.createKey(CalymeneEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ON_BOTTOM = EntityDataManager.createKey(CalymeneEntity.class, DataSerializers.BOOLEAN);

    // --- Behavior tuning ---
    private static final double FLOOR_CLEARANCE = 0.06D;   // ~6 cm above seafloor
    private static final double CONTACT_EPSILON = 0.10D;   // consider "on bottom" within this distance
    private static final int PANIC_ROLL_MIN = 60;      // 3s
    private static final int PANIC_ROLL_VAR = 40;      // +0–2s
    private static final int SWIM_BURST_MIN = 60;      // 3s swim burst
    private static final int SWIM_BURST_VAR = 60;      // +0–3s
    private static final float MAX_STEP_UP = 1.0F;    // climb one block while crawling

    // Timers
    private int rollTicks;        // rolling into a ball
    private int panicTicks;       // general "spooked" timer (drives bursts)
    private int swimBurstTicks;   // temporarily ignore bottom-walk and actually swim

    public CalymeneEntity(World world) {
        super(world);
        this.target(AlvarezsaurusEntity.class, BeelzebufoEntity.class, CompsognathusEntity.class, LeptictidiumEntity.class, EntitySquid.class);

        this.setVariant(this.getRNG().nextInt(4));
        this.stepHeight = MAX_STEP_UP;
        this.tasks.addTask(0, new BottomCrawlGoal(this, 1.05D, 8, 6));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(ROLLED, Boolean.FALSE);
        this.dataManager.register(ON_BOTTOM, Boolean.FALSE);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Variant", this.getVariant());
        tagCompound.setInteger("RollTicks", this.rollTicks);
        tagCompound.setInteger("PanicTicks", this.panicTicks);
        tagCompound.setInteger("SwimBurstTicks", this.swimBurstTicks);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        this.setVariant(tagCompound.getInteger("Variant"));
        this.rollTicks = tagCompound.getInteger("RollTicks");
        this.panicTicks = tagCompound.getInteger("PanicTicks");
        this.swimBurstTicks = tagCompound.getInteger("SwimBurstTicks");
        this.setRolled(this.rollTicks > 0);
    }

    public boolean isOnBottom() {
        return this.dataManager.get(ON_BOTTOM);
    }

    private void setOnBottom(boolean value) {
        this.dataManager.set(ON_BOTTOM, value);
    }

    public void setVariant(int value) {
        this.dataManager.set(VARIANT, value);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public boolean isRolled() {
        return this.dataManager.get(ROLLED);
    }

    public void setRolled(boolean value) {
        this.dataManager.set(ROLLED, value);
    }

    public boolean isPanicking() {
        return panicTicks > 0 || this.getAttackTarget() != null;
    }

    private void startSwimBurst(Random random) {
        this.swimBurstTicks = SWIM_BURST_MIN + random.nextInt(SWIM_BURST_VAR + 1);
    }

    /** Trigger defensive enrollment (rolling into a ball) for a short time. */
    public void triggerRoll(int durationTicks) {
        this.rollTicks = Math.max(this.rollTicks, durationTicks);
        this.setRolled(true);
        this.setAIMoveSpeed(this.getAIMoveSpeed() * 0.5F);
    }

    @Override
    public boolean attackEntityFrom(net.minecraft.util.DamageSource source, float amount) {
        boolean res = super.attackEntityFrom(source, amount);
        if (!this.world.isRemote && this.isInWater() && !this.isCarcass()) {
            this.panicTicks = Math.max(this.panicTicks, 80 + this.getRNG().nextInt(80));
            if (this.getHealth() / this.getMaxHealth() < 0.35F || this.getRNG().nextFloat() < 0.20F) {
                this.triggerRoll(PANIC_ROLL_MIN + this.getRNG().nextInt(PANIC_ROLL_VAR + 1));
            }
            startSwimBurst(this.getRNG());
        }
        return res;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (this.rollTicks > 0) {
            if (--this.rollTicks <= 0) {
                this.setRolled(false);
            }
        }
        if (this.panicTicks > 0) {
            this.panicTicks--;
        }
        if (this.swimBurstTicks > 0) {
            this.swimBurstTicks--;
        }

        if (!this.world.isRemote && this.isInWater() && !this.isCarcass() && this.swimBurstTicks == 0) {
            double floorY = findSeafloorY(this.posX, this.posY, this.posZ);
            if (!Double.isNaN(floorY)) {
                double targetY = floorY + FLOOR_CLEARANCE;
                double dy = targetY - this.posY;

                Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ);
                double correction = dy * 0.18D - vel.y * 0.35D;
                this.motionX = vel.x * 0.92D;
                this.motionY = vel.y + correction;
                this.motionZ = vel.z * 0.92D;
            }
        }
    }

    /**
     * Replace base travel in water with a bottom-walk biased movement that can burst-swim.
     */
    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isInWater() && !this.isCarcass()) {
            if (this.swimBurstTicks > 0) {
                this.setOnBottom(false);
                super.travel(strafe, vertical, forward);
                return;
            }

            double floorY = findSeafloorY(this.posX, this.posY, this.posZ);
            boolean hasFloor = !Double.isNaN(floorY);

            if (!hasFloor) {
                this.setOnBottom(false);
                super.travel(strafe, vertical, forward);
                return;
            }

            double targetY = floorY + FLOOR_CLEARANCE;
            double dy = targetY - this.posY;
            boolean onBottom = Math.abs(dy) <= CONTACT_EPSILON && !this.isRolled();
            this.setOnBottom(onBottom);

            if (onBottom) {
                this.moveRelative(strafe, 0.0F, forward, 0.10F);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ);
                double correction = dy * 0.22D - vel.y * 0.45D;
                vel = new Vec3d(vel.x * 0.86D, vel.y + correction, vel.z * 0.86D);
                if (dy > 0.0D && dy <= MAX_STEP_UP + 0.01D) {
                    double upBoost = Math.min(0.12D, dy * 0.55D);
                    vel = new Vec3d(vel.x, Math.max(vel.y, upBoost), vel.z);
                }
                if (Math.abs(dy) < 0.05D) {
                    vel = new Vec3d(vel.x, vel.y - 0.01D, vel.z);
                }
                this.motionX = vel.x;
                this.motionY = vel.y;
                this.motionZ = vel.z;
            } else {
                this.setOnBottom(false);
                super.travel(strafe, vertical, forward);
            }
        } else {
            this.setOnBottom(false);
            super.travel(strafe, vertical, forward);
        }
    }

    /**
     * Finds the Y of the first solid/non-water block BELOW, within a safe scan depth.
     * Returns NaN if no floor is found promptly.
     */
    private double findSeafloorY(double x, double y, double z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        int minY = 0;
        int maxScan = 24;

        int steps = 0;
        while (steps < maxScan && pos.getY() > minY) {
            IBlockState state = this.world.getBlockState(pos);
            BlockPos below = pos.down();
            IBlockState belowState = this.world.getBlockState(below);

            boolean waterHere = state.getMaterial() == Material.WATER;
            boolean solidTopBelow = belowState.getMaterial() != Material.WATER
                    && belowState.isSideSolid(this.world, below, EnumFacing.UP);

            if (waterHere && solidTopBelow) {
                return below.getY() + 1.0D;
            }

            pos.setPos(pos.getX(), pos.getY() - 1, pos.getZ());
            steps++;
        }
        return Double.NaN;
    }

    public ResourceLocation getTexture() {
        switch (getVariant()) {
            case 0:
            default:
                return texture("brown");
            case 1:
                return texture("silver");
            case 2:
                return texture("purple");
            case 3:
                return texture("tan");
        }
    }

    private ResourceLocation texture(String variant) {
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()
                ? new ResourceLocation(RebornMod.MODID, texture + "_male_" + "adult" + "_" + variant + ".png")
                : new ResourceLocation(RebornMod.MODID, texture + "_female_" + "adult" + "_" + variant + ".png");
    }

    /**
     * Random strolling that picks points ON the seafloor.
     */
    static class BottomCrawlGoal extends EntityAIBase {
        private final CalymeneEntity mob;
        private final double speed;
        private final int radius;
        private final int tries;
        private int cooldown;

        public BottomCrawlGoal(CalymeneEntity mob, double speed, int radius, int tries) {
            this.mob = mob;
            this.speed = speed;
            this.radius = radius;
            this.tries = tries;
            this.setMutexBits(Mutex.MOVEMENT);
        }

        @Override
        public boolean shouldExecute() {
            if (!mob.isInWater() || mob.isCarcass() || mob.isRolled()) {
                return false;
            }
            if (mob.swimBurstTicks > 0) {
                return false;
            }
            if (cooldown > 0) {
                cooldown--;
                return false;
            }
            return mob.getNavigator().noPath() && mob.getRNG().nextFloat() < 0.15F;
        }

        @Override
        public void startExecuting() {
            cooldown = 20 + mob.getRNG().nextInt(20);
            Random random = mob.getRNG();
            BlockPos origin = mob.getPosition();
            for (int i = 0; i < tries; i++) {
                int dx = MathHelper.getInt(random, -radius, radius);
                int dz = MathHelper.getInt(random, -radius, radius);
                double floorY = mob.findSeafloorY(origin.getX() + dx + 0.5D, origin.getY(), origin.getZ() + dz + 0.5D);
                if (!Double.isNaN(floorY)) {
                    double y = floorY + FLOOR_CLEARANCE;
                    mob.getNavigator().tryMoveToXYZ(origin.getX() + dx + 0.5D, y, origin.getZ() + dz + 0.5D, speed);
                    return;
                }
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            return mob.isInWater() && !mob.isCarcass() && mob.swimBurstTicks == 0 && !mob.getNavigator().noPath();
        }
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation) {
        return null;
    }
}