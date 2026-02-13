package mod.reborn.server.entity;

import mod.reborn.server.entity.ai.Mutex;
import mod.reborn.server.entity.ai.navigation.DinosaurMoveHelper;
import mod.reborn.server.entity.ai.navigation.DinosaurPathNavigate;
import mod.reborn.server.entity.animal.ai.EntityAIFindWater;
import mod.reborn.server.entity.animal.ai.EntityAIWanderNearWater;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class PenguinDinosaurEntity extends DinosaurEntity {
    private boolean getOut = false;
    private boolean getInWater = false;
    private boolean blocked;

    protected final PathNavigate navigationSwimmer;
    protected final PathNavigate navigationLand;

    private final EntityMoveHelper waterMoveHelper;
    private final DinosaurMoveHelper landMoveHelper;

    private int waterTicks;
    private int landTicks;

    public PenguinDinosaurEntity(World world) {
        super(world);
        setSize(1, 1);
        this.blocked = false;

        this.waterMoveHelper = new SwimmingMoveHelper();
        this.landMoveHelper = new DinosaurMoveHelper(this);
        this.navigationSwimmer = new PathNavigateSwimmer(this, world);
        this.navigationLand = new DinosaurPathNavigate(this, world);
        ((DinosaurPathNavigate) this.navigationLand).setCanSwim(true);

        this.moveHelper = this.landMoveHelper;
        this.navigator = this.navigationLand;

        this.tasks.addTask(5, new MoveUnderwaterGoal());
        this.tasks.addTask(10, new FindWaterGoal(this, 1.0D, 2, 30));
        this.tasks.addTask(10, new WanderGoal(this, 1.0D, 10, 2));
    }

    @Override
    public boolean isMovementBlocked() {
        return this.isCarcass() || this.isSleeping() || blocked;
    }

    @Override
    public void onEntityUpdate() {
        int air = this.getAir();

        if (!this.world.isRemote && this.isEntityAlive()) {
            if (this.isInWater()) {
                waterTicks++;
                landTicks = 0;
                this.setAir(300);
                getOut = waterTicks > 200;
                getInWater = false;
                this.navigator = this.navigationSwimmer;
            } else {
                landTicks++;
                waterTicks = 0;
                --air;
                this.setAir(air);
                if (this.getAir() <= -20) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
                getInWater = this.getAir() < 40;
                getOut = false;
                this.navigator = this.navigationLand;
            }
        }

        if (this.isInWater()) {
            if (this.moveHelper != this.waterMoveHelper) {
                this.moveHelper = this.waterMoveHelper;
            }
            if (this.navigator != this.navigationSwimmer) {
                this.navigator = this.navigationSwimmer;
            }
        } else {
            if (this.moveHelper != this.landMoveHelper) {
                this.moveHelper = this.landMoveHelper;
            }
            if (this.navigator != this.navigationLand) {
                this.navigator = this.navigationLand;
            }
        }

        super.onEntityUpdate();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.world.isRemote && this.isInWater() && (this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) > 0.005D) {
            Vec3d viewVec = this.getLookVec();
            float offsetX = MathHelper.cos(this.rotationYaw * 0.017453292F) * 0.3F;
            float offsetZ = MathHelper.sin(this.rotationYaw * 0.017453292F) * 0.3F;
            float distance = 1.2F - this.rand.nextFloat() * 0.7F;

            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - viewVec.x * (double) distance + (double) offsetX,
                        this.posY - viewVec.y,
                        this.posZ - viewVec.z * (double) distance + (double) offsetZ,
                        0.0D, 0.0D, 0.0D
                );
                this.world.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - viewVec.x * (double) distance - (double) offsetX,
                        this.posY - viewVec.y,
                        this.posZ - viewVec.z * (double) distance - (double) offsetZ,
                        0.0D, 0.0D, 0.0D
                );
            }
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        boolean noInput = strafe == 0.0F && vertical == 0.0F && forward == 0.0F;

        if (this.isServerWorld() && this.isInWater() && !this.isCarcass()) {
            this.moveRelative(strafe, vertical, forward, 0.25F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.7D;
            this.motionY *= 0.7D;
            this.motionZ *= 0.7D;
            if (noInput) {
                this.motionY -= 0.005D;
            }
        } else {
            super.travel(strafe, vertical, forward);
        }
    }

    @Override
    public boolean isInWater() {
        return this.inWater || this.world.handleMaterialAcceleration(this.getEntityBoundingBox().grow(0.0D, -0.5D, 0.0D).shrink(0.001D), Material.WATER, this);
    }

    class SwimmingMoveHelper extends EntityMoveHelper {
        private final PenguinDinosaurEntity swimmingEntity = PenguinDinosaurEntity.this;

        public SwimmingMoveHelper() {
            super(PenguinDinosaurEntity.this);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.swimmingEntity.getNavigator().noPath() && this.swimmingEntity.isInWater()) {
                double distanceX = this.posX - this.swimmingEntity.posX;
                double distanceY = this.posY - this.swimmingEntity.posY;
                double distanceZ = this.posZ - this.swimmingEntity.posZ;
                double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                distance = MathHelper.sqrt(distance);
                distanceY /= distance;
                float f = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                this.swimmingEntity.rotationYaw = this.limitAngle(this.swimmingEntity.rotationYaw, f, 30.0F);
                this.swimmingEntity.setAIMoveSpeed((float) (this.swimmingEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * this.speed));
                this.swimmingEntity.motionY += (double) this.swimmingEntity.getAIMoveSpeed() * distanceY * 0.05D;
            } else {
                this.swimmingEntity.setAIMoveSpeed(0.0F);
            }
        }
    }

    class MoveUnderwaterGoal extends EntityAIBase {
        private double x;
        private double y;
        private double z;

        public MoveUnderwaterGoal() {
            this.setMutexBits(Mutex.MOVEMENT);
        }

        @Override
        public boolean shouldExecute() {
            Random rng = PenguinDinosaurEntity.this.getRNG();
            if (PenguinDinosaurEntity.this.getAttackTarget() != null) {
                return false;
            }
            if (rng.nextFloat() < 0.50F && PenguinDinosaurEntity.this.isBusy()) {
                return false;
            }

            Vec3d target = getOut
                    ? getRandomLandPos(PenguinDinosaurEntity.this, 6, 6)
                    : getRandomWaterPos(PenguinDinosaurEntity.this, 6, 6);

            if (target == null) {
                return false;
            }
            this.x = target.x;
            this.y = target.y;
            this.z = target.z;
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (PenguinDinosaurEntity.this.getAttackTarget() != null) {
                return false;
            }
            return !PenguinDinosaurEntity.this.getNavigator().noPath();
        }

        @Override
        public void startExecuting() {
            PenguinDinosaurEntity.this.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, 1.0D);
        }

        @Override
        public boolean isInterruptible() {
            return true;
        }

        @Nullable
        private Vec3d getRandomWaterPos(EntityCreature mob, int hr, int vr) {
            for (int i = 0; i < 10; i++) {
                double x = mob.posX + mob.getRNG().nextInt(hr * 2 + 1) - hr;
                double y = mob.posY + mob.getRNG().nextInt(vr * 2 + 1) - vr;
                double z = mob.posZ + mob.getRNG().nextInt(hr * 2 + 1) - hr;
                BlockPos pos = new BlockPos(x, y, z);
                if (mob.world.getBlockState(pos).getMaterial().isLiquid()) {
                    return new Vec3d(x, y, z);
                }
            }
            return null;
        }

        @Nullable
        private Vec3d getRandomLandPos(EntityCreature mob, int hr, int vr) {
            for (int i = 0; i < 10; i++) {
                double x = mob.posX + mob.getRNG().nextInt(hr * 2 + 1) - hr;
                double y = mob.posY + mob.getRNG().nextInt(vr * 2 + 1) - vr;
                double z = mob.posZ + mob.getRNG().nextInt(hr * 2 + 1) - hr;
                BlockPos pos = new BlockPos(x, y, z);
                if (mob.world.getBlockState(pos).getMaterial().isSolid()) {
                    return new Vec3d(x, y, z);
                }
            }
            return null;
        }
    }

    class FindWaterGoal extends EntityAIFindWater {
        public FindWaterGoal(EntityCreature creatureIn, double speedIn, int chance, int walkradius) {
            super(creatureIn, speedIn, chance, walkradius);
        }

        @Override
        public boolean shouldExecute() {
            if (!getInWater) {
                return false;
            }
            return super.shouldExecute();
        }
    }

    class WanderGoal extends EntityAIWanderNearWater {
        private final PenguinDinosaurEntity creature;

        public WanderGoal(PenguinDinosaurEntity creatureIn, double speedIn, int chance, int walkradius) {
            super(creatureIn, speedIn, chance, walkradius);
            this.creature = creatureIn;
        }

        @Override
        public boolean shouldExecute() {
            if (creature.getInWater) {
                return false;
            }
            return !PenguinDinosaurEntity.this.isInWater() && super.shouldExecute();
        }
    }
}
