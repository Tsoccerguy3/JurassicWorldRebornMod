
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.CompsognathusEntity;

public class CompsognathusAnimator extends EntityAnimator<CompsognathusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, CompsognathusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // cores
        AdvancedModelRenderer abdomen    = model.getCube("abdomen");
        AdvancedModelRenderer upperBody  = model.getCube("Upper body");
        AdvancedModelRenderer head       = model.getCube("Head");

        // neck chain (head last for biggest influence)
        AdvancedModelRenderer neck1 = model.getCube("Neck 1");
        AdvancedModelRenderer neck2 = model.getCube("Neck 2");
        AdvancedModelRenderer neck3 = model.getCube("Neck 3");
        AdvancedModelRenderer neck4 = model.getCube("Neck 4");
        AdvancedModelRenderer[] neck = new AdvancedModelRenderer[]{ head, neck4, neck3, neck2, neck1 };

        // tail chain (tip-first works nicely for chain helpers)
        AdvancedModelRenderer tail1 = model.getCube("Tail 1");
        AdvancedModelRenderer tail2 = model.getCube("Tail 2");
        AdvancedModelRenderer tail3 = model.getCube("Tail 3");
        AdvancedModelRenderer tail4 = model.getCube("Tail 4");
        AdvancedModelRenderer tail5 = model.getCube("Tail 5");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail5, tail4, tail3, tail2, tail1 };

        // --- idle tuning ---
        float idleSpeed  = 0.12F;
        float idleDegree = 0.08F;

        // breathing bob on abdomen + slight torso wave
        model.bob(abdomen, idleSpeed, 0.35F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ abdomen, upperBody }, idleSpeed * 0.8F, idleDegree * 0.4F, 2, ticks, 1.0F);

        // subtle neck undulation + tiny head sway/bob
        model.chainWave(neck, idleSpeed * 0.9F, idleDegree * 0.6F, -2, ticks, 1.0F);

            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.08F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);


        // tail sway (idle) + a little extra when moving
        model.chainSwing(tail, idleSpeed, 0.15F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.7F, 0.12F, -2, limbSwing, limbSwingAmount);
        }

        // look-at: distribute yaw/pitch down the neck chain, head last
        model.faceTarget(rotationYaw, rotationPitch, 0.95F,
                neck1, neck2, neck3, neck4, head
        );

        // dynamic smoothing if available
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
