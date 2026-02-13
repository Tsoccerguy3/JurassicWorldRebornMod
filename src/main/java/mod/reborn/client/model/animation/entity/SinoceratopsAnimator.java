
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.SinoceratopsEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SinoceratopsAnimator extends EntityAnimator<SinoceratopsEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, SinoceratopsEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- core cubes (common Tabula naming) ---
        AdvancedModelRenderer body1 = model.getCube("body 1");
        AdvancedModelRenderer body2 = model.getCube("body 2");
        AdvancedModelRenderer body3 = model.getCube("body 3");
        AdvancedModelRenderer neck  = model.getCube("neck");
        AdvancedModelRenderer head  = model.getCube("head");

        AdvancedModelRenderer tail1 = model.getCube("tail 1");
        AdvancedModelRenderer tail2 = model.getCube("tail 2");
        AdvancedModelRenderer tail3 = model.getCube("tail 3");
        AdvancedModelRenderer tail4 = model.getCube("tail 4");
        AdvancedModelRenderer tail5 = model.getCube("tail 5");
        AdvancedModelRenderer tail6 = model.getCube("tail 6");
        AdvancedModelRenderer tail7 = model.getCube("tail 7");

        // Frill pieces (names may vary per export; these are common)
        AdvancedModelRenderer frillConn = model.getCube("frill connection");
        AdvancedModelRenderer frillMidT = model.getCube("frill middle top");
        AdvancedModelRenderer frillRT   = model.getCube("frill right top");
        AdvancedModelRenderer frillLT   = model.getCube("frill left top");

        AdvancedModelRenderer[] torso     = arr(body1, body2, body3);
        AdvancedModelRenderer[] neckChain = arr(head, neck); // head first for chain helpers
        AdvancedModelRenderer[] tail      = arr(tail7, tail6, tail5, tail4, tail3, tail2, tail1); // tip→root
        AdvancedModelRenderer[] frillSet  = arr(frillMidT, frillRT, frillLT);

        // --- tuning ---
        float idleSpeed  = 0.10F; // frequency
        float idleDegree = 0.08F; // amplitude

        // --- breathing: torso bob + gentle torso wave ---
        if (body1 != null) model.bob(body1, idleSpeed, 0.55F, false, ticks, 1.0F);
        if (torso.length > 1) model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // --- neck & head subtle motion ---
        if (neckChain.length > 0) model.chainWave(neckChain, idleSpeed * 0.6F, idleDegree * 0.55F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);
        }

        // --- tail sway (idle) + locomotion overlay when moving ---
        if (tail.length > 0) {
            model.chainSwing(tail, idleSpeed, 0.16F, -2, ticks, 1.0F);
            if (limbSwingAmount > 0.12F) {
                model.chainSwing(tail, 0.55F, 0.12F, -2, limbSwing, limbSwingAmount);
            }
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }

        // --- frill flutter (very subtle, mostly cosmetic) ---
        if (frillSet.length > 0) {
            model.chainWave(frillSet, idleSpeed * 1.2F, 0.04F, 2, ticks, 1.0F);
            model.chainSwing(frillSet, idleSpeed * 1.1F, 0.05F, 2, ticks, 1.0F);
        }
        if (frillConn != null) {
            model.chainWave(new AdvancedModelRenderer[]{ frillConn }, idleSpeed, 0.02F, 0, ticks, 1.0F);
        }

        // --- face target (head last for strongest influence) ---
        if (neck != null || head != null) {
            model.faceTarget(rotationYaw, rotationPitch, 0.85F,
                    arr(neck, head)
            );
        }
    }

    private static AdvancedModelRenderer[] arr(AdvancedModelRenderer... parts) {
        java.util.ArrayList<AdvancedModelRenderer> out = new java.util.ArrayList<>();
        for (AdvancedModelRenderer p : parts) if (p != null) out.add(p);
        return out.toArray(new AdvancedModelRenderer[0]);
    }
}
