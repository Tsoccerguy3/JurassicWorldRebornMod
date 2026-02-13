
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.LeptictidiumEntity;

public class LeptictidiumAnimator extends EntityAnimator<LeptictidiumEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, LeptictidiumEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso / core
        AdvancedModelRenderer hips   = model.getCube("bodyhips");
        AdvancedModelRenderer body   = model.getCube("body");
        AdvancedModelRenderer belly  = model.getCube("belly");
        AdvancedModelRenderer head   = model.getCube("head");

        // neck chain (head last for stronger influence)
        AdvancedModelRenderer neck   = model.getCube("neck");
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer neck5  = model.getCube("neck5");
        AdvancedModelRenderer neck6  = model.getCube("neck6");
        AdvancedModelRenderer neck7  = model.getCube("neck7");
        AdvancedModelRenderer neck8  = model.getCube("neck8");
        AdvancedModelRenderer neck9  = model.getCube("neck9");
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{
                head, neck9, neck8, neck7, neck6, neck5, neck4, neck3, neck2, neck
        };

        // tail chain (tip → root)
        AdvancedModelRenderer tail1 = model.getCube("tail");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- idle tuning ---
        float idleSpeed  = 0.12F;
        float idleDegree = 0.08F;

        // breathing
        model.bob(belly, idleSpeed, 0.35F, false, ticks, 1.0F);
        model.bob(hips,  idleSpeed, 0.25F, false, ticks, 1.0F);

        model.chainWave(new AdvancedModelRenderer[]{ hips, body }, idleSpeed * 0.8F, idleDegree * 0.4F, 2, ticks, 1.0F);

        // neck/head subtle motion
        model.chainWave(neckChain, idleSpeed * 0.9F, idleDegree * 0.6F, -2, ticks, 1.0F);

        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.08F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);


        // tail sway (idle), locomotion overlay when moving
        model.chainSwing(tail, idleSpeed, 0.18F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.7F, 0.12F, -2, limbSwing, limbSwingAmount);
        }

        // look-at (idle tracking)
        model.faceTarget(rotationYaw, rotationPitch, 0.95F,
                neck, neck2, neck3, neck4, neck5, neck6, neck7, neck8, neck9, head
        );

        // smooth tail if buffer exists
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
