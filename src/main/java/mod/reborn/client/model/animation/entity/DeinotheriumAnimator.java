
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DeinotheriumEntity;

public class DeinotheriumAnimator extends EntityAnimator<DeinotheriumEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, DeinotheriumEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso segments
        AdvancedModelRenderer bodyBack    = model.getCube("Body back");
        AdvancedModelRenderer bodyMiddle  = model.getCube("Body middle");
        AdvancedModelRenderer bodyFront   = model.getCube("Body front");

        // neck & head
        AdvancedModelRenderer neck1 = model.getCube("Neck 1");
        AdvancedModelRenderer neck2 = model.getCube("Neck 2");
        AdvancedModelRenderer head  = model.getCube("Head");
        AdvancedModelRenderer[] neck = new AdvancedModelRenderer[]{ head, neck2, neck1 };

        // tail
        AdvancedModelRenderer tail1 = model.getCube("Tail 1");
        AdvancedModelRenderer tail2 = model.getCube("Tail 2");
        AdvancedModelRenderer tail3 = model.getCube("Tail 3");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail3, tail2, tail1 };

        // --- idle tuning (large mammal: slower/larger breathing) ---
        float idleSpeed  = 0.08F;
        float idleDegree = 0.10F;

        // body breathing: bob the midsection, wave through torso
        model.bob(bodyMiddle, idleSpeed, 0.05F, false, ticks, 0.3F);
        model.chainWave(new AdvancedModelRenderer[]{ bodyBack, bodyMiddle, bodyFront }, idleSpeed * 0.2F, idleDegree * 0.1F, 2, ticks, 1.0F);

        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.5F, -2, ticks, 1.0F);
        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.08F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);


        // tail sway (short tail, keep it subtle)
        model.chainSwing(tail, idleSpeed, 0.12F, -2, ticks, 1.0F);

        // look-at: steer with necks, head last
        model.faceTarget(rotationYaw, rotationPitch, 0.85F,
                neck1, neck2, head
        );

        // smoothing if available on the entity
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
