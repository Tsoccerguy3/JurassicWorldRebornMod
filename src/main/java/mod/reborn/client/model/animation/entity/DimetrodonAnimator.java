
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DimetrodonEntity;

public class DimetrodonAnimator extends EntityAnimator<DimetrodonEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, DimetrodonEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso / roots
        AdvancedModelRenderer hips  = model.getCube("Bodyhips");
        AdvancedModelRenderer belly = model.getCube("belly");
        AdvancedModelRenderer body  = model.getCube("body");     // may be null on some exports; optional

        AdvancedModelRenderer head = model.getCube("headsupport"); // fallback small piece near the head

        // tail chain (tip-first for chain helpers)
        AdvancedModelRenderer tail1 = model.getCube("tail");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- idle tuning ---
        float idleSpeed  = 0.10F;
        float idleDegree = 0.08F;

        // breathing: bob belly/hips, slight torso wave
        model.bob(belly, idleSpeed, 0.45F, false, ticks, 1.0F);
        model.bob(hips,  idleSpeed, 0.30F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ hips, belly, body }, idleSpeed * 0.6F, idleDegree * 0.5F, 2, ticks, 1.0F);

        // tail idle sway; add locomotion layer when moving
        model.chainSwing(tail, idleSpeed, 0.18F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.7F, 0.12F, -2, limbSwing, limbSwingAmount);
        }


//            model.faceTarget(rotationYaw, rotationPitch, 0.5F, head);
            model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);


        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
