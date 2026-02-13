
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.NigersaurusEntity;

public class NigersaurusAnimator extends EntityAnimator<NigersaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, NigersaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer hips  = model.getCube("Niger");
        AdvancedModelRenderer body  = model.getCube("Body");
        AdvancedModelRenderer head  = model.getCube("Head");

        AdvancedModelRenderer neck1 = model.getCube("Neck1");
        AdvancedModelRenderer neck2 = model.getCube("Neck2");
        AdvancedModelRenderer neck3 = model.getCube("Neck3");
        AdvancedModelRenderer neck4 = model.getCube("Neck4");

        AdvancedModelRenderer tail1 = model.getCube("Tail1");
        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("bone");
        AdvancedModelRenderer tail5 = model.getCube("bone2");
        AdvancedModelRenderer tail6 = model.getCube("bone12");

        AdvancedModelRenderer[] neckParts = new AdvancedModelRenderer[] { head, neck4, neck3, neck2, neck1 };
        AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        float idleSpeed  = 0.10F;
        float idleDegree = 0.08F;

        model.bob(hips, idleSpeed, 0.6F, false, ticks, 1.0F);

        model.chainWave(neckParts, idleSpeed * 0.6F, idleDegree * 0.4F, -2, ticks, 1.0F);

        model.chainSwing(tailParts, idleSpeed, 0.16F, -2, ticks, 1.0F);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}

