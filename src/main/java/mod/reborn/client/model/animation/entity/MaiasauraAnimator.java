
package mod.reborn.client.model.animation.entity;



import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.MaiasauraEntity;

public class MaiasauraAnimator extends EntityAnimator<MaiasauraEntity> {

    protected void performAnimations(AnimatableModel model, MaiasauraEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer head = model.getCube("Head");

        AdvancedModelRenderer neck1 = model.getCube("Neck1");

        // body parts
        AdvancedModelRenderer stomach = model.getCube("Body");
        // tail parts
        AdvancedModelRenderer tail1 = model.getCube("Tail1");
        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("Tail4");

        // right arm
        AdvancedModelRenderer upperArmRight = model.getCube("RightArm");
        AdvancedModelRenderer lowerArmRight = model.getCube("RightArm2");
        AdvancedModelRenderer rightHand = model.getCube("RightArm3");

        // left arm
        AdvancedModelRenderer upperArmLeft = model.getCube("LeftArm");
        AdvancedModelRenderer lowerArmLeft = model.getCube("LeftArm2");
        AdvancedModelRenderer leftHand = model.getCube("LeftArm3");
        AdvancedModelRenderer[] neck  = new AdvancedModelRenderer[]{ head, neck1 };
        AdvancedModelRenderer[] torso = new AdvancedModelRenderer[]{ stomach };
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] { tail4,tail3, tail2, tail1 };
        float idleSpeed  = 0.10F;
        float idleDegree = 0.08F;

        float scaleFactor = 0.6F;
        float height = 2F;
        model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);
        model.bob(stomach, idleSpeed, 0.55F, false, ticks, 1.0F);
        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.6F, -2, ticks, 1.0F);
        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);

        model.chainWave(tail, 1F * scaleFactor, -0.1F, 2, f, f1);
        model.chainSwing(tail, 0.5F * scaleFactor, 0.1F, 2, f, f1);

        model.chainWave(tail, 0.1F, -0.02F, 2, ticks, 1F);
        model.faceTarget(rotationYaw, rotationPitch, 0.95F, neck1, head);
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}