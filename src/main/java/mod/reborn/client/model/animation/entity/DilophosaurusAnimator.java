
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.server.entity.dinosaur.DilophosaurusEntity;

@SideOnly(Side.CLIENT)
public class DilophosaurusAnimator extends EntityAnimator<DilophosaurusEntity> {
    @Override
    protected void performAnimations(AnimatableModel model, DilophosaurusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        AdvancedModelRenderer RightFrill = model.getCube("RightFrill1");

        AdvancedModelRenderer LeftFrill = model.getCube("LeftFrill1");

        boolean hasTarget = entity.hasTarget() && !entity.isCarcass();

        LeftFrill.showModel = hasTarget;
        RightFrill.showModel = hasTarget;

        AdvancedModelRenderer head = model.getCube("Head");

        AdvancedModelRenderer neck1 = model.getCube("Neck1");
        AdvancedModelRenderer neck2 = model.getCube("Neck2");
        AdvancedModelRenderer neck3 = model.getCube("Neck3");
        AdvancedModelRenderer neck4 = model.getCube("Neck4");

        AdvancedModelRenderer body1 = model.getCube("Body");

        AdvancedModelRenderer tail1 = model.getCube("Tail1");
        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("Tail4");
        AdvancedModelRenderer tail5 = model.getCube("Tail5");
        AdvancedModelRenderer tail6 = model.getCube("Tail6");

        AdvancedModelRenderer rightThigh = model.getCube("rightleg");
        AdvancedModelRenderer leftThigh = model.getCube("leftleg");

        AdvancedModelRenderer upperArmRight = model.getCube("RightArm");
        AdvancedModelRenderer upperArmLeft = model.getCube("LeftArm");
        AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[] { head, neck4, neck3, neck2, neck1, body1 };
        AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        AdvancedModelRenderer[] armRight = new AdvancedModelRenderer[] { upperArmRight };
        AdvancedModelRenderer[] armLeft = new AdvancedModelRenderer[] { upperArmLeft };

        float globalSpeed = 1.0F;
        float globalDegree = 1.0F;


        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, globalSpeed * 0.5F, globalDegree * 0.05F, 1, limbSwing, limbSwingAmount);
        model.chainSwing(tailParts, globalSpeed * 0.5F, globalDegree * 0.1F, 2, limbSwing, limbSwingAmount);
        model.chainWave(bodyParts, globalSpeed * 0.5F, globalDegree * 0.025F, 3, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, 0.15F, -0.03F, 2, ticks, 0.25F);
        model.chainWave(bodyParts, 0.15F, 0.03F, 3.5F, ticks, 0.25F);
        model.chainWave(armRight, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainWave(armLeft, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainSwing(tailParts, 0.15F, -0.1F, 3, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
