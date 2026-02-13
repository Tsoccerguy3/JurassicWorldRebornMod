
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.LivyatanEntity;

@SideOnly(Side.CLIENT)
public class LivyatanAnimator extends EntityAnimator<LivyatanEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, LivyatanEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {
        final AdvancedModelRenderer body         = model.getCube("Body");
        final AdvancedModelRenderer shoulders    = model.getCube("Shoulders");
        final AdvancedModelRenderer neck         = model.getCube("Neck");
        final AdvancedModelRenderer head         = model.getCube("Head");
        final AdvancedModelRenderer jaw          = model.getCube("Jaw");
        final AdvancedModelRenderer leftFlipper  = model.getCube("LeftFlipper");
        final AdvancedModelRenderer rightFlipper = model.getCube("RightFlipper");

        final boolean inWater = entity.isInWater();
        if (entity.isCarcass()) return;

        float intensity;
        if (inWater) {
            final float speed = MathHelper.sqrt(
                    (float)(entity.motionX * entity.motionX
                            + entity.motionY * entity.motionY
                            + entity.motionZ * entity.motionZ)
            );
            intensity = (Math.max(0.18F, speed * 2.5F)) * 0.5F;
        } else {
            intensity = 0.12F;
        }

        if (body != null) {
            final float bobFreq = 0.25F + 0.25F * intensity;
            final float bobAmp  = 0.06F + 0.06F * intensity;
            model.bob(body, bobFreq, bobAmp, false, ticks, 1.0F);
        }

        if (neck != null && head != null) {
            final float neckPhase = ticks * (0.18F + 0.12F * intensity);
            final float s = MathHelper.sin(neckPhase);
            final float lead = s * (0.04F * intensity);
            neck.rotateAngleX += -lead * 0.5F;
            head.rotateAngleX +=  lead;
        }

        if (jaw != null) {
            final boolean hunting = (entity.getAttackTarget() != null) || (intensity > 0.8F);
            final float base  = inWater ? 0.04F : 0.0F;
            final float extra = hunting ? 0.18F : 0.06F * intensity;
            final float s = 0.5F + 0.5F * MathHelper.sin(ticks * 0.9F);
            jaw.rotateAngleX += base + extra * s;
        }

        if (leftFlipper != null && rightFlipper != null) {
            final float paddleFreq = (0.8F + intensity * 0.7F) * 0.1F;
            final float phase = ticks * paddleFreq;
            final float s = MathHelper.sin(phase);
            final float c = MathHelper.cos(phase);

            final float paddleZ = 0.30F + 0.25F * intensity;
            final float paddleX = 0.12F + 0.10F * intensity;

            leftFlipper.rotateAngleZ  +=  s * paddleZ - 0.35F;
            rightFlipper.rotateAngleZ += -s * paddleZ + 0.35F;

            leftFlipper.rotateAngleX  +=  c * paddleX;
            rightFlipper.rotateAngleX += -c * paddleX;

            final float steer = rotationYaw * 0.0025F; // tiny; safe for extremes
            leftFlipper.rotateAngleY  += -steer * 0.6F;
            rightFlipper.rotateAngleY +=  steer * 0.6F;
            if (!inWater) {
                leftFlipper.rotateAngleZ  -= 0.35F;
                rightFlipper.rotateAngleZ += 0.35F;
            }
        }
    }
}
