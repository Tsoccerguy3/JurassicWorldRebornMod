
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.RaphusrexEntity;

public class RaphusrexAnimator extends EntityAnimator<RaphusrexEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, RaphusrexEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- torso ---
        AdvancedModelRenderer body1 = model.getCube("Body 1");
        AdvancedModelRenderer body2 = model.getCube("Body 2");
        AdvancedModelRenderer body3 = model.getCube("Body 3");
        AdvancedModelRenderer[] torso = new AdvancedModelRenderer[]{ body1, body2, body3 };

        // --- neck/head (head last for strongest influence) ---
        AdvancedModelRenderer neck1 = model.getCube("Neck1");
        AdvancedModelRenderer neck2 = model.getCube("Neck2");
        AdvancedModelRenderer neck3 = model.getCube("Neck3");
        AdvancedModelRenderer neck4 = model.getCube("Neck4");
        AdvancedModelRenderer neck5 = model.getCube("Neck5");
        AdvancedModelRenderer neck6 = model.getCube("Neck6");         // present in file
        AdvancedModelRenderer head  = model.getCube("Head");

        AdvancedModelRenderer[] neck = new AdvancedModelRenderer[]{
                 neck1
        };

        // --- tail chain (tip → root) ---
        AdvancedModelRenderer tail1  = model.getCube("Tail 1");
        AdvancedModelRenderer tail2  = model.getCube("Tail 2");
        AdvancedModelRenderer tail3  = model.getCube("Tail 3");
        AdvancedModelRenderer tail4  = model.getCube("Tail 4");
        AdvancedModelRenderer tail5  = model.getCube("Tail 5");
        AdvancedModelRenderer tail6  = model.getCube("Tail 6");
        AdvancedModelRenderer tail7  = model.getCube("Tail 7");
        AdvancedModelRenderer tail8  = model.getCube("Tail 8");
        AdvancedModelRenderer tail9  = model.getCube("Tail 9");
        AdvancedModelRenderer tail10 = model.getCube("Tail 10");
        AdvancedModelRenderer tail11 = model.getCube("Tail 11");

        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{
                tail11, tail10, tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail1
        };



        // --- tuning ---
        float idleSpeed  = 0.12F;  // frequency
        float idleDegree = 0.08F;  // amplitude

        // --- breathing (soft bob + gentle torso wave) ---
        if (body1 != null) model.bob(body1, idleSpeed, 0.45F, false, ticks, 1.0F);
        model.chainWave(torso, idleSpeed * 0.7F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // --- neck/head subtle motion ---
        model.chainWave(neck, idleSpeed * 0.85F, idleDegree * 0.6F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);
        }

        // --- tail sway (idle) ---
        model.chainSwing(tail, idleSpeed, 0.10F, -2, ticks, 0.7F);

        // extra flutter on feather tips (very subtle)


        // add locomotion layer when moving
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.05F, 0.14F, -2, limbSwing, limbSwingAmount);
        }

        // smooth the tail after all transforms
        entity.tailBuffer.applyChainSwingBuffer(tail);

        model.faceTarget(rotationYaw, rotationPitch, 0.9F, neck1, neck2, neck3, neck4, neck5, neck6, neck6, head);
    }
}
