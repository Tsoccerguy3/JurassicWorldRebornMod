
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.CarcharodontosaurusEntity;

public class CarcharodontosaurusAnimator extends EntityAnimator<CarcharodontosaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, CarcharodontosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso / root
        AdvancedModelRenderer body1 = model.getCube("Body 1");

        // neck chain (head-first order works best for chain helpers)
        AdvancedModelRenderer head   = model.getCube("Head");
        AdvancedModelRenderer neck6  = model.getCube("Neck6");
        AdvancedModelRenderer neck5  = model.getCube("Neck5");
        AdvancedModelRenderer neck4  = model.getCube("Neck4");
        AdvancedModelRenderer neck3  = model.getCube("Neck3");
        AdvancedModelRenderer neck2  = model.getCube("Neck2");
        AdvancedModelRenderer neck1  = model.getCube("Neck1");

        AdvancedModelRenderer[] neck = new AdvancedModelRenderer[] {
                head, neck6, neck5, neck4, neck3, neck2, neck1
        };

        // tail chain (tip-first order looks smoother)
        AdvancedModelRenderer tail1 = model.getCube("Tail 1");
        AdvancedModelRenderer tail2 = model.getCube("Tail 2");
        AdvancedModelRenderer tail3 = model.getCube("Tail 3");
        AdvancedModelRenderer tail4 = model.getCube("Tail 4");
        AdvancedModelRenderer tail5 = model.getCube("Tail 5");
        AdvancedModelRenderer tail6 = model.getCube("Tail 6");
        AdvancedModelRenderer tail7 = model.getCube("Tail 7");

        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] {
                tail7, tail6, tail5, tail4, tail3, tail2, tail1
        };

        // --- idle breathing ---
        float idleSpeed  = 0.10F;  // frequency
        float idleDegree = 0.08F;  // amplitude

        if (body1 != null) {
            // gentle body bob
            model.bob(body1, idleSpeed, 0.6F, false, ticks, 1.0F);
        }
        // subtle neck undulation (up/down)
        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.6F, -2, ticks, 1.0F);

        // --- tail sway (left/right) ---
        model.chainSwing(tail, idleSpeed, 0.18F, -2, ticks, 1.0F);
        model.faceTarget(rotationYaw, rotationPitch, 0.6F, neck1, neck3, neck4, neck5, neck6);


        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
