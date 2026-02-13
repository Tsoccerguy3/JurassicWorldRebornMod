
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.IndoraptorEntity;

public class IndoraptorAnimator extends EntityAnimator<IndoraptorEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, IndoraptorEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // cores
        AdvancedModelRenderer hips   = model.getCube("bodyhips");
        AdvancedModelRenderer body   = model.getCube("body");
        AdvancedModelRenderer body2  = model.getCube("body2");
        AdvancedModelRenderer belly  = model.getCube("belly");
        AdvancedModelRenderer head   = model.getCube("Head");

        // neck chain (base → tip, head last for strongest influence in faceTarget)
        AdvancedModelRenderer neckBase = model.getCube("Neck Base");

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{
                head, neckBase
        };

        // tail chain (tip → root)
        AdvancedModelRenderer tail1 = model.getCube("tail");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer tail7 = model.getCube("tail7");
        AdvancedModelRenderer tail8 = model.getCube("tail8");
        AdvancedModelRenderer tail9 = model.getCube("tail9");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- idle tuning ---
        float idleSpeed  = 0.12F;
        float idleDegree = 0.08F;

        // breathing through torso
        model.bob(belly, idleSpeed, 0.45F, false, ticks, 1.0F);
        model.bob(hips,  idleSpeed, 0.30F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ hips, body, body2 }, idleSpeed * 0.7F, idleDegree * 0.4F, 2, ticks, 1.0F);

        // neck/head subtle motion
        model.chainWave(neckChain, idleSpeed * 0.8F, idleDegree * 0.6F, -2, ticks, 1.0F);
        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.10F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);

        // tail sway (idle) + locomotion layer
        model.chainSwing(tail, idleSpeed, 0.20F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.3F, 0.05F, -2, limbSwing, limbSwingAmount);
        }

        model.faceTarget(rotationYaw, rotationPitch, 0.9F, neckBase, head);

        // tail smoothing
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
