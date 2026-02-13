
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.HerrerasaurusEntity;

public class HerrerasaurusAnimator extends EntityAnimator<HerrerasaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, HerrerasaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso
        AdvancedModelRenderer body1 = model.getCube("Body 1");
        AdvancedModelRenderer body2 = model.getCube("Body 2");
        AdvancedModelRenderer body3 = model.getCube("Body 3");

        // neck & head (head last for strongest influence)
        AdvancedModelRenderer neck1 = model.getCube("Neck 1");
        AdvancedModelRenderer neck2 = model.getCube("Neck 2");
        AdvancedModelRenderer neck3 = model.getCube("Neck 3");
        AdvancedModelRenderer neck4 = model.getCube("Neck 4");
        AdvancedModelRenderer neck5 = model.getCube("Neck 5");
        AdvancedModelRenderer neck6 = model.getCube("Neck 6");
        AdvancedModelRenderer neck7 = model.getCube("Neck 7");
        AdvancedModelRenderer head  = model.getCube("Head");

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {
                head, neck7, neck6, neck5, neck4, neck3, neck2, neck1
        };

        // tail (tip-first → root)
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

        // ---- idle tuning ----
        float idleSpeed  = 0.12F;
        float idleDegree = 0.08F;

        // breathing through torso
        if (body1 != null) model.bob(body1, idleSpeed, 0.45F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ body1, body2, body3 }, idleSpeed * 0.7F, idleDegree * 0.4F, 2, ticks, 1.0F);

        // neck/head subtle motion
        model.chainWave(neckChain, idleSpeed * 0.8F, idleDegree * 0.6F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);
        }

        // tail sway (idle), plus locomotion overlay when moving
        model.chainSwing(tail, idleSpeed, 0.10F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.7F, 0.12F, -2, limbSwing, limbSwingAmount);
        }

        // look-at: distribute yaw/pitch down the neck; head last
        model.faceTarget(rotationYaw, rotationPitch, 0.9F, neck1);

        // smoothing (if entity provides it)
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
