
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.ElasmotheriumEntity;

public class ElasmotheriumAnimator extends EntityAnimator<ElasmotheriumEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, ElasmotheriumEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // torso segments
        AdvancedModelRenderer back   = model.getCube("back");
        AdvancedModelRenderer rear   = model.getCube("rear");
        AdvancedModelRenderer gut    = model.getCube("gut");
        AdvancedModelRenderer shoulder = model.getCube("shoulder");

        // neck & head
        AdvancedModelRenderer neckThicc = model.getCube("neck_thicc"); // base pad
        AdvancedModelRenderer neck1 = model.getCube("neck1");
        AdvancedModelRenderer neck2 = model.getCube("neck2");
        AdvancedModelRenderer neck3 = model.getCube("neck3");
        AdvancedModelRenderer head  = model.getCube("head");

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {
                head, neck3, neck2, neck1, neckThicc
        };

        // tail (tip-first)
        AdvancedModelRenderer tailBase  = model.getCube("tail_base");
        AdvancedModelRenderer tail1     = model.getCube("tail1");
        AdvancedModelRenderer tail2     = model.getCube("tail2");
        AdvancedModelRenderer tail4     = model.getCube("tail4"); // model skips "tail3" in this export
        AdvancedModelRenderer tailFluff = model.getCube("tail_fluff");

        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] {
                tailFluff, tail4, tail2, tail1, tailBase
        };

        // ---- idle tuning (big mammal) ----
        float idleSpeed  = 0.09F;
        float idleDegree = 0.10F;

        // deep breathing across torso
        if (gut != null) model.bob(gut, idleSpeed, 0.55F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ back, rear, gut, shoulder }, idleSpeed * 0.6F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // neck & head subtle motion
        model.chainWave(neckChain, idleSpeed * 0.55F, idleDegree * 0.5F, -2, ticks, 1.0F);

        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.08F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);

        // tail sway (short tail; keep subtle)
        model.chainSwing(tail, idleSpeed, 0.12F, -2, ticks, 1.0F);

        // look-at: steer with necks, head last
        model.faceTarget(rotationYaw, rotationPitch, 0.85F, head);

        // smoothing if available
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
