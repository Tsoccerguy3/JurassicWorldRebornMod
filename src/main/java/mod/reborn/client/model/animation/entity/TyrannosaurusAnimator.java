
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.TyrannosaurusEntity;

public class TyrannosaurusAnimator extends EntityAnimator<TyrannosaurusEntity> {
    

    @Override
    protected void performAnimations(AnimatableModel model, TyrannosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // ----- Grab parts -----
        AdvancedModelRenderer body1 = model.getCube( "Body 1");
        AdvancedModelRenderer body2 = model.getCube( "Body 2");
        AdvancedModelRenderer body3 = model.getCube( "Body 3");

        AdvancedModelRenderer neck1 = model.getCube( "Neck1");
        AdvancedModelRenderer neck2 = model.getCube( "Neck2");
        AdvancedModelRenderer neck3 = model.getCube( "Neck3");
        AdvancedModelRenderer neck4 = model.getCube( "Neck4");
        AdvancedModelRenderer neck5 = model.getCube( "Neck5");
        AdvancedModelRenderer neck6 = model.getCube( "Neck6");
        AdvancedModelRenderer head  = model.getCube( "Head");
        AdvancedModelRenderer jaw   = model.getCube( "Lower Jaw");

        AdvancedModelRenderer tail1 = model.getCube( "Tail 1");
        AdvancedModelRenderer tail2 = model.getCube( "Tail 2");
        AdvancedModelRenderer tail3 = model.getCube( "Tail 3");
        AdvancedModelRenderer tail4 = model.getCube( "Tail 4");
        AdvancedModelRenderer tail5 = model.getCube( "Tail 5");
        AdvancedModelRenderer tail6 = model.getCube( "Tail 6");
        AdvancedModelRenderer tail7 = model.getCube( "Tail 7");

        AdvancedModelRenderer[] torso = new AdvancedModelRenderer[] {body1, body2, body3};
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {neck1};
        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[] {tail7, tail6, tail5, tail4, tail3, tail2, tail1};

        // ===== Idle tuning =====
        float idleSpeed  = 0.1F;
        float idleDeg    = 0.1F;
        float walkSpeed  = 0.6F;
        float walkDeg    = 0.12F;

        // Tail slowed to 1/3 speed
        float tailIdleSpeed = idleSpeed / 2.0F;
        float tailWalkSpeed = walkSpeed / 2.0F;

        // ----- Breathing -----
        if (body1 != null) model.bob(body1, idleSpeed, 0.35F, false, ticks, 1.0F);
        if (torso.length > 0) {
            model.chainWave(torso, idleSpeed, idleDeg * 0.45F, 2, ticks, 1.0F);
        }

        // ----- Neck + head idle motion -----
        if (neckChain.length > 0) {
            model.chainWave(neckChain, idleSpeed * 0.9F, idleDeg * 0.3F, -2, ticks, 1.0F);
            model.chainSwing(neckChain, idleSpeed * 0.8F, idleDeg * 0.25F, -2, ticks, 1.0F);
        }

        // ----- Face target -----
        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1);

        // ----- Jaw subtle idle -----
        if (jaw != null && !entity.isCarcass()) {
            jaw.rotateAngleX += Math.sin(ticks * 0.07F) * 0.02F;
        }

        // ----- Tail sway (slowed to 1/3 speed) -----
        if (tailChain.length > 0) {
            model.chainSwing(tailChain, tailIdleSpeed, 0.17F, -2, ticks, 1.0F);
            model.chainWave (tailChain, tailIdleSpeed * 0.8F, 0.05F, -2, ticks, 1.0F);

            if (limbSwingAmount > 0.1F) {
                model.chainSwing(tailChain, tailWalkSpeed, walkDeg, -2, limbSwing, limbSwingAmount);
            }
            entity.tailBuffer.applyChainSwingBuffer(tailChain);
        }
    }
}
