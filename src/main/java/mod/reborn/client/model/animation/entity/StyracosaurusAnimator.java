
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.StyracosaurusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StyracosaurusAnimator extends EntityAnimator<StyracosaurusEntity> {

    private static AdvancedModelRenderer[] arr(AdvancedModelRenderer... parts) {
        java.util.ArrayList<AdvancedModelRenderer> out = new java.util.ArrayList<>();
        for (AdvancedModelRenderer p : parts) if (p != null) out.add(p);
        return out.toArray(new AdvancedModelRenderer[0]);
    }

    @Override
    protected void performAnimations(AnimatableModel model, StyracosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // Core torso/neck/head
        AdvancedModelRenderer body1 = model.getCube("body 1");
        AdvancedModelRenderer body2 = model.getCube("body 2");
        AdvancedModelRenderer body3 = model.getCube("body 3");                 // :contentReference[oaicite:4]{index=4}
        AdvancedModelRenderer neck  = model.getCube("neck");
        AdvancedModelRenderer head  = model.getCube("head");                  // :contentReference[oaicite:5]{index=5}

        // Tail chain (tip → root)
        AdvancedModelRenderer tail1 = model.getCube("tail 1");
        AdvancedModelRenderer tail2 = model.getCube("tail 2");
        AdvancedModelRenderer tail3 = model.getCube("tail 3");
        AdvancedModelRenderer tail4 = model.getCube("tail 4");
        AdvancedModelRenderer tail5 = model.getCube("tail 5");
        AdvancedModelRenderer tail6 = model.getCube("tail 6");
        AdvancedModelRenderer tail7 = model.getCube("tail 7");                 // :contentReference[oaicite:6]{index=6}


        AdvancedModelRenderer frillConn   = model.getCube("frill connection");
        AdvancedModelRenderer frillTopR   = model.getCube("frill top right");
        AdvancedModelRenderer frillBotR   = model.getCube("frill bottom right");
        AdvancedModelRenderer frillBotSideR = model.getCube("frill bottom side right");
        AdvancedModelRenderer frillTopSideR = model.getCube("frill top side right");  // :contentReference[oaicite:7]{index=7}

        // Arrays
        AdvancedModelRenderer[] torso    = arr(body1, body2, body3);
        AdvancedModelRenderer[] neckHead = arr(head, neck); // head last will get strongest faceTarget influence
        AdvancedModelRenderer[] tail     = arr(tail7, tail6, tail5, tail4, tail3, tail2, tail1);
        AdvancedModelRenderer[] frillSet = arr(frillTopR, frillBotR, frillBotSideR, frillTopSideR);

        // Tuning
        float idleSpeed  = 0.10F;
        float idleDegree = 0.08F;

        // Breathing: torso bob + gentle torso wave
        model.bob(body1, idleSpeed, 0.55F, false, ticks, 1.0F);
        if (torso.length > 1)
            model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // Neck & head subtle motion
        if (neckHead.length > 0)
            model.chainWave(neckHead, idleSpeed * 0.6F, idleDegree * 0.55F, -2, ticks, 1.0F);

            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);


        // Tail sway (idle) + locomotion overlay when moving
        if (tail.length > 0) {
            model.chainSwing(tail, idleSpeed, 0.16F, -2, ticks, 1.0F);
            if (limbSwingAmount > 0.12F) {
                model.chainSwing(tail, 0.55F, 0.12F, -2, limbSwing, limbSwingAmount);
            }
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }

        // Frill flutter (very subtle)
        if (frillSet.length > 0) {
            model.chainWave(frillSet, idleSpeed * 1.2F, 0.04F, 2, ticks, 1.0F);
            model.chainSwing(frillSet, idleSpeed * 1.1F, 0.05F, 2, ticks, 1.0F);
        }
            model.chainWave(new AdvancedModelRenderer[]{ frillConn }, idleSpeed, 0.02F, 0, ticks, 1.0F);

        // Face target (idle tracking)
        if (neck != null || head != null) {
            model.faceTarget(rotationYaw, rotationPitch, 0.85F, arr(neck, head));
        }
    }
}
