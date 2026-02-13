
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.TroodonEntity;

@SideOnly(Side.CLIENT)
public class TroodonAnimator extends EntityAnimator<TroodonEntity> {

    @Override
    protected void performAnimations(AnimatableModel model,
                                     TroodonEntity entity,
                                     float limbSwing,
                                     float limbSwingAmount,
                                     float ticks,
                                     float rotationYaw,
                                     float rotationPitch,
                                     float scale) {

        AdvancedModelRenderer head   = model.getCube("Head");

        AdvancedModelRenderer neck1  = model.getCube("Neck1");
        AdvancedModelRenderer neck2  = model.getCube("Neck2");
        AdvancedModelRenderer neck3  = model.getCube("Neck3");
        AdvancedModelRenderer neck4  = model.getCube("Neck4");
        AdvancedModelRenderer neck5  = model.getCube("Neck5");

        AdvancedModelRenderer chest  = model.getCube("Chest");
        AdvancedModelRenderer rear   = model.getCube("Rear");

        AdvancedModelRenderer upperArmR = model.getCube("Arm2");
        AdvancedModelRenderer upperArmL = model.getCube("Arm");

        AdvancedModelRenderer thighL = model.getCube("Leg");
        AdvancedModelRenderer kneeL  = model.getCube("Knee");
        AdvancedModelRenderer ankleL = model.getCube("Ankle");
        AdvancedModelRenderer footL  = model.getCube("Foot");

        AdvancedModelRenderer thighR = model.getCube("Leg2");
        AdvancedModelRenderer kneeR  = model.getCube("Knee2");
        AdvancedModelRenderer ankleR = model.getCube("Ankle2");
        AdvancedModelRenderer footR  = model.getCube("Foot2");

        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("Tail4");
        AdvancedModelRenderer tail5 = model.getCube("Tail5");
        AdvancedModelRenderer tail6 = model.getCube("Tail6");
        AdvancedModelRenderer tail7 = model.getCube("Tail7");

        // chains
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{head, neck5, neck4, neck3, neck2, neck1};
        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[]{tail7, tail6, tail5, tail4, tail3, tail2};
        AdvancedModelRenderer[] rightArm  = new AdvancedModelRenderer[]{upperArmR};
        AdvancedModelRenderer[] leftArm   = new AdvancedModelRenderer[]{upperArmL};

        // speeds
        float walkSpeed = 0.38F;
        float idleSpeed = 0.10F;
        float move = limbSwingAmount;

        boolean isMoving = move > 0.08F;

        if (isMoving) {
            model.bob(chest, walkSpeed * 0.5F, 0.25F * move, false, limbSwing, move);
            model.bob(rear,  walkSpeed * 0.5F, 0.20F * move, true,  limbSwing, move);
        } else {
            model.bob(chest, idleSpeed, 0.10F, false, ticks, 1.0F);
        }

        model.chainWave(neckChain, idleSpeed, 0.03F, 2, ticks, 1.0F);

        if (isMoving) {
            model.chainWave(neckChain, walkSpeed * 0.4F, -0.03F * move, 2, limbSwing, move);
        }

        model.chainSwing(leftArm,  walkSpeed * 0.75F, 0.15F * move, 0, limbSwing, move);
        model.chainSwing(rightArm, walkSpeed * 0.75F, 0.15F * move, (float)Math.PI, limbSwing, move);

        if (isMoving) {
            model.chainSwing(tailChain, walkSpeed * 0.55F, 0.35F * move, 2, limbSwing, move);
            model.chainWave (tailChain, walkSpeed * 0.40F, 0.18F * move, 2, limbSwing, move);
        } else {
            model.chainWave(tailChain, idleSpeed * 0.65F, 0.12F, 2, ticks, 1.0F);
        }
    }
}
