
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.CeratosaurusEntity;

public class CeratosaurusAnimator extends EntityAnimator<CeratosaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, CeratosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // hips/torso root
        AdvancedModelRenderer bodyHips = model.getCube("bodyhips");

        // neck chain
        AdvancedModelRenderer head = model.getCube("head");
        AdvancedModelRenderer neck11 = model.getCube("neck11");
        AdvancedModelRenderer neck10 = model.getCube("neck10");
        AdvancedModelRenderer neck9  = model.getCube("neck9");
        AdvancedModelRenderer neck8  = model.getCube("neck8");
        AdvancedModelRenderer neck7  = model.getCube("neck7");
        AdvancedModelRenderer neck6  = model.getCube("neck6");
        AdvancedModelRenderer neck5  = model.getCube("neck5");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck   = model.getCube("neck");

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {
                head, neck11, neck10, neck9, neck8, neck7, neck6, neck5, neck4, neck3, neck2, neck
        };

        // tail chain
        AdvancedModelRenderer tail  = model.getCube("tail");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer tail7 = model.getCube("tail7");
        AdvancedModelRenderer tail8 = model.getCube("tail8");
        AdvancedModelRenderer tail9 = model.getCube("tail9");

        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[] {
                tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail
        };

        // --- idle breathing ---
        float idleSpeed  = 0.12F;
        float idleDegree = 0.08F;


        model.bob(bodyHips, idleSpeed, 0.55F, false, ticks, 1.0F);

        model.chainWave(neckChain, idleSpeed * 0.6F, idleDegree * 0.3F, -2, ticks, 1.0F);

        // --- tail sway ---
        model.chainSwing(tailChain, idleSpeed, 0.20F, -2, ticks, 1.0F);
        model.faceTarget(rotationYaw, rotationPitch, 0.6F, neck, neck3, neck4, neck5, neck6, neck7, neck8, neck9, neck10,neck11);
        entity.tailBuffer.applyChainSwingBuffer(tailChain);
    }
}
