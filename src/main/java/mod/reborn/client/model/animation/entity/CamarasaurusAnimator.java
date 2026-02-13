
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.CamarasaurusEntity;

public class CamarasaurusAnimator extends EntityAnimator<CamarasaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, CamarasaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {


        AdvancedModelRenderer hips  = model.getCube("hips");
        AdvancedModelRenderer body  = model.getCube("body");
        AdvancedModelRenderer head  = model.getCube("head");

        // neck chain
        AdvancedModelRenderer neck1 = model.getCube("neck1");
        AdvancedModelRenderer neck2 = model.getCube("neck2");
        AdvancedModelRenderer neck3 = model.getCube("neck3");
        AdvancedModelRenderer neck4 = model.getCube("neck4");
        AdvancedModelRenderer neck5 = model.getCube("neck5");
        AdvancedModelRenderer neck6 = model.getCube("neck6");
        AdvancedModelRenderer neck7 = model.getCube("neck7");
        AdvancedModelRenderer neck8 = model.getCube("neck8");

        // tail chain
        AdvancedModelRenderer tail1 = model.getCube("tail1");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer tail7 = model.getCube("tail7");

        AdvancedModelRenderer[] neckParts = new AdvancedModelRenderer[] { head, neck8, neck7, neck6, neck5, neck4, neck3, neck2, neck1 };
        AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail7, tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- idle breathing ---
        float idleSpeed  = 0.10F;
        float idleDegree = 0.08F;

        // hips/body breathing bob
        model.bob(hips, idleSpeed, 0.6F, false, ticks, 1.0F);

        // subtle neck wave (up/down)
        model.chainWave(neckParts, idleSpeed * 0.6F, idleDegree * 0.4F, -2, ticks, 1.0F);

        // --- idle tail sway (left/right) ---
        model.chainSwing(tailParts, idleSpeed, 0.16F, -2, ticks, 1.0F);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
