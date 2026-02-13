
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DunkleosteusEntity;

@SideOnly(Side.CLIENT)
public class DunkleosteusAnimator extends EntityAnimator<DunkleosteusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, DunkleosteusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        {

            AdvancedModelRenderer head   = model.getCube("Head");
            AdvancedModelRenderer jaw    = model.getCube("Jaw");

            AdvancedModelRenderer body   = model.getCube("Body");
            AdvancedModelRenderer body2  = model.getCube("Body2");
            AdvancedModelRenderer body3  = model.getCube("Body3");

            AdvancedModelRenderer tail1  = model.getCube("Tail1");
            AdvancedModelRenderer tail2  = model.getCube("Tail2");

            AdvancedModelRenderer leftFrontFlipper   = model.getCube("LeftFrontFlipper");
            AdvancedModelRenderer rightFrontFlipper  = model.getCube("RightFrontFlipper");
            AdvancedModelRenderer middleLeftFlipper  = model.getCube("MiddleLeftFlipper");
            AdvancedModelRenderer middleRightFlipper = model.getCube("MiddleRightFlipper");
            AdvancedModelRenderer backLeftFlipper    = model.getCube("BackLeftFlipper");
            AdvancedModelRenderer backRightFlipper   = model.getCube("BackRightFlipper");

            AdvancedModelRenderer[] tail  = new AdvancedModelRenderer[]{ tail2, tail1 };
            AdvancedModelRenderer[] spine = new AdvancedModelRenderer[]{ body3, body2, body }; // keep Head out

            final boolean inWater = entity.isInWater();

            // Slightly stronger swim vs. previous version
            float swimSpeed   = 0.26F;  // was 0.22F
            float swimDegree  = 0.14F;  // was 0.10F
            float waveFactor  = 0.08F;  // was 0.06F

            float bobSpeed    = 0.18F;
            float bobDegree   = 0.035F; // was 0.025F

            float flopSpeed   = 0.30F;
            float flopDegree  = 0.12F;

            if (body != null) {
                model.bob(body, inWater ? bobSpeed : 0.45F, inWater ? bobDegree : 0.10F, false, ticks, 1.0F);
            }

            if (inWater) {
                model.chainSwing(tail,  swimSpeed,             swimDegree,                2, ticks, 1.0F);
                model.chainWave (tail,  swimSpeed * 0.55F,     swimDegree * waveFactor,   2, ticks, 1.0F);

                model.chainSwing(spine, swimSpeed * 0.70F,    -swimDegree * 0.20F,       2, ticks + 0.5F, 0.9F);

                // Fin motion bumped slightly
                flapPair(leftFrontFlipper,  rightFrontFlipper, 0.44F, 0.10F, ticks);
                flapPair(middleLeftFlipper, middleRightFlipper,0.40F, 0.09F, ticks + 0.25F);
                flapPair(backLeftFlipper,   backRightFlipper,  0.37F, 0.08F, ticks + 0.50F);

            } else {
                model.chainSwing(tail,  flopSpeed,          flopDegree,           2, ticks, 1.0F);
                model.chainSwing(spine, flopSpeed * 0.70F, -flopDegree * 0.18F,  2, ticks, 0.9F);

                if (leftFrontFlipper  != null) { leftFrontFlipper.rotateAngleZ  -= 0.22F; leftFrontFlipper.rotateAngleX  += 0.15F; }
                if (rightFrontFlipper != null) { rightFrontFlipper.rotateAngleZ += 0.22F; rightFrontFlipper.rotateAngleX += 0.15F; }
                if (middleLeftFlipper != null) { middleLeftFlipper.rotateAngleZ -= 0.18F; }
                if (middleRightFlipper!= null) { middleRightFlipper.rotateAngleZ+= 0.18F; }
                if (backLeftFlipper   != null) { backLeftFlipper.rotateAngleZ   -= 0.14F; }
                if (backRightFlipper  != null) { backRightFlipper.rotateAngleZ  += 0.14F; }
            }

            // Head: only faceTarget, damped + clamped
            if (head != null) {
                float damp = 0.30F;
                model.faceTarget(rotationYaw * damp, rotationPitch * damp, 0.35F, head);

                float maxYaw   = (float)Math.toRadians(10.0);
                float maxPitch = (float)Math.toRadians(8.0);
                if (head.rotateAngleY >  maxYaw)   head.rotateAngleY =  maxYaw;
                if (head.rotateAngleY < -maxYaw)   head.rotateAngleY = -maxYaw;
                if (head.rotateAngleX >  maxPitch) head.rotateAngleX =  maxPitch;
                if (head.rotateAngleX < -maxPitch) head.rotateAngleX = -maxPitch;
            }

        }
    }

    private static void flapPair(AdvancedModelRenderer left, AdvancedModelRenderer right, float freq, float amp, float t) {
        if (left != null && right != null) {
            float s = (float)Math.sin(t * freq);
            float c = (float)Math.cos(t * freq);
            left.rotateAngleZ  +=  s * (amp * 0.65F) - 0.18F;
            right.rotateAngleZ += -s * (amp * 0.65F) + 0.18F;
            left.rotateAngleX  +=  c * (amp * 0.20F);
            right.rotateAngleX += -c * (amp * 0.20F);
        }
    }
}
