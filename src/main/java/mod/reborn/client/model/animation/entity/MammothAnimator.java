
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.MammothEntity;

public class MammothAnimator extends EntityAnimator<MammothEntity> {

    private static AdvancedModelRenderer[] nn(AdvancedModelRenderer... parts) {
        java.util.ArrayList<AdvancedModelRenderer> out = new java.util.ArrayList<>();
        for (AdvancedModelRenderer p : parts) if (p != null) out.add(p);
        return out.toArray(new AdvancedModelRenderer[0]);
    }

    @Override
    protected void performAnimations(AnimatableModel model, MammothEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // common torso names across exports
        AdvancedModelRenderer body    = model.getCube("Body");
        AdvancedModelRenderer head    = model.getCube("Head");

        // short tail
        AdvancedModelRenderer tail1 = model.getCube("Tail 1");
        AdvancedModelRenderer tail2 = model.getCube("Tail 2");
        AdvancedModelRenderer tail3 = model.getCube("Tail 3");

        // trunk chain (grab whatever exists)
        AdvancedModelRenderer trunk1 = model.getCube("trunk 1");
        AdvancedModelRenderer trunk2 = model.getCube("trunk 2");
        AdvancedModelRenderer trunk3 = model.getCube("trunk 3");
        AdvancedModelRenderer trunk4 = model.getCube("trunk 4");
        AdvancedModelRenderer trunk5 = model.getCube("trunk 5");
        AdvancedModelRenderer trunk6 = model.getCube("trunk 6");

        // neck (if present)
        AdvancedModelRenderer neck1 = model.getCube("neck1");
        AdvancedModelRenderer neck2 = model.getCube("neck2");

        AdvancedModelRenderer[] torso  = nn(body);
        AdvancedModelRenderer[] tail   = nn(tail3, tail2, tail1);
        AdvancedModelRenderer[] neck   = nn(head, neck2, neck1);
        AdvancedModelRenderer[] trunk  = nn(trunk6, trunk5, trunk4, trunk3, trunk2, trunk1);

        float idleSpeed  = 0.09F;
        float idleDegree = 0.10F;

        // deep breathing through torso
        if (body    != null) model.bob(body,    idleSpeed, 0.40F, false, ticks, 1.0F);
        if (torso.length > 1) model.chainWave(torso, idleSpeed * 0.6F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // subtle neck/head motion
        if (neck.length > 0) model.chainWave(neck, idleSpeed * 0.55F, idleDegree * 0.5F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.08F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);
        }

        // trunk sway (side-to-side) + a light wave (up/down)
        if (trunk.length > 0) {
            model.chainSwing(trunk, idleSpeed * 0.9F, 0.18F, 2, ticks, 1.0F);
            model.chainWave (trunk, idleSpeed * 0.8F, 0.10F, 2, ticks, 1.0F);
        }

        // short tail sway
        if (tail.length > 0) {
            model.chainSwing(tail, idleSpeed, 0.12F, -2, ticks, 1.0F);
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }

        // look-at via neck/head (keep gentle on big mammals)
        if (neck1 != null || head != null) {
            model.faceTarget(rotationYaw, rotationPitch, 0.75F,
                    nn(neck1, neck2, head));
        }
    }
}
