
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.SpinosaurusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class SpinosaurusAnimator extends EntityAnimator<SpinosaurusEntity> {

    // ---- helpers: filter null parts so chain* calls never NPE ----
    private static AdvancedModelRenderer[] nn(AdvancedModelRenderer... parts) {
        List<AdvancedModelRenderer> ok = new ArrayList<>();
        for (AdvancedModelRenderer p : parts) if (p != null) ok.add(p);
        return ok.toArray(new AdvancedModelRenderer[0]);
    }

    // small wrappers that no-op on empty arrays
    private static void swing(AnimatableModel m, float s, float d, int off, float t, float w, AdvancedModelRenderer... parts) {
        AdvancedModelRenderer[] arr = nn(parts);
        if (arr.length > 0) m.chainSwing(arr, s, d, off, t, w);
    }
    private static void wave(AnimatableModel m, float s, float d, int off, float t, float w, AdvancedModelRenderer... parts) {
        AdvancedModelRenderer[] arr = nn(parts);
        if (arr.length > 0) m.chainWave(arr, s, d, off, t, w);
    }
    private static void bob(AnimatableModel m, AdvancedModelRenderer part, float s, float d, boolean bounce, float f, float f1) {
        if (part != null) m.bob(part, s, d, bounce, f, f1);
    }

    @Override
    protected void performAnimations(AnimatableModel model, SpinosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {


        AdvancedModelRenderer hips   = model.getCube("bodyhips");
        AdvancedModelRenderer body   = model.getCube("body");
        AdvancedModelRenderer chest  = model.getCube("chest");     // optional in some exports

        AdvancedModelRenderer neck   = model.getCube("neck");      // base segment
        AdvancedModelRenderer neckthroat   = model.getCube("neckthroat");      // base segment
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer neck5  = model.getCube("neck5");
        AdvancedModelRenderer neck6  = model.getCube("neck6");
        AdvancedModelRenderer neck7  = model.getCube("neck7");
        AdvancedModelRenderer neck8  = model.getCube("neck8");
        AdvancedModelRenderer head   = model.getCube("Head");

        AdvancedModelRenderer tail   = model.getCube("tail");      // first tail piece is literally "tail"
        AdvancedModelRenderer tail2  = model.getCube("tail2");
        AdvancedModelRenderer tail3  = model.getCube("tail3");
        AdvancedModelRenderer tail4  = model.getCube("tail4");
        AdvancedModelRenderer tail5  = model.getCube("tail5");
        AdvancedModelRenderer tail6  = model.getCube("tail6");
        AdvancedModelRenderer tail7  = model.getCube("tail7");
        AdvancedModelRenderer tail8  = model.getCube("tail8");
        AdvancedModelRenderer tail9  = model.getCube("tail9");
        AdvancedModelRenderer tail10 = model.getCube("tail10");
        AdvancedModelRenderer tail11 = model.getCube("tail11");
        AdvancedModelRenderer tail12 = model.getCube("tail12");
        AdvancedModelRenderer tail13 = model.getCube("tail13");
        AdvancedModelRenderer tail14 = model.getCube("tail14");

        AdvancedModelRenderer leftLeg   = model.getCube("leftleg");
        AdvancedModelRenderer rightLeg  = model.getCube("rightleg");
        AdvancedModelRenderer leftAnkle = model.getCube("leftankle");
        AdvancedModelRenderer rightAnkle= model.getCube("rightankle");
        AdvancedModelRenderer leftFoot  = model.getCube("leftfoot");
        AdvancedModelRenderer rightFoot = model.getCube("rightfoot");

        AdvancedModelRenderer leftBicep   = model.getCube("leftbicep");
        AdvancedModelRenderer rightBicep  = model.getCube("rightbicep");
        AdvancedModelRenderer leftArm   = model.getCube("leftarm");
        AdvancedModelRenderer rightArm  = model.getCube("rightarm");

        // build chains SAFELY (nulls get filtered inside helpers)
        AdvancedModelRenderer[] spine = nn(hips, body, chest);
        AdvancedModelRenderer[] neckChain = nn(neckthroat,neck, neck2, neck3, neck4, neck5, neck6, neck7, neck8, head);
        AdvancedModelRenderer[] tailChain = nn(tail14, tail13, tail12, tail11, tail10, tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail);

        // ===== idle (gentle, heavy creature) =====
        float idleSpeed = 0.35F, idleDeg = 0.6F;
        bob(model, hips, idleSpeed * 0.25F, idleDeg * 1.2F, false, limbSwing, limbSwingAmount);
        wave(model, idleSpeed * 0.25F, idleDeg * 0.05F, 2, ticks, 0.5F, spine);
        wave(model, idleSpeed * 0.25F, -idleDeg * 0.05F, 3, ticks, 0.5F, neckChain);
        wave(model, 0.10F, 0.05F, 2, ticks, 0.25F, tailChain);

        boolean swimming = entity != null && entity.isInWater();

        if (swimming) {
            // ===== exact leg tuck (copied pose) =====
            if (leftLeg  != null)  leftLeg.rotateAngleX  = 1.045897F;
            if (rightLeg != null)  rightLeg.rotateAngleX = 1.083065F;
            if (leftBicep  != null)  leftBicep.rotateAngleX  = 1.045897F;
            if (rightBicep != null)  rightBicep.rotateAngleX = 1.083065F;
            if (leftArm  != null)  leftArm.rotateAngleX  = 0.175897F;
            if (rightArm != null)  rightArm.rotateAngleX = 0.143065F;
            float ankleTuck = -0.84F;
            if (leftAnkle  != null) leftAnkle.rotateAngleX  = ankleTuck;
            if (rightAnkle != null) rightAnkle.rotateAngleX = ankleTuck;
            if (leftFoot   != null) leftFoot.rotateAngleX   = 1.0920526F;
            if (rightFoot  != null) rightFoot.rotateAngleX  = 1.2805481F;

            // ===== swimming undulation (Baryonyx feel, stretched to 14 links) =====
            float swimSpeed = 0.25F, swimDeg = 0.40F, phase = 0.25F;
            swing(model, swimSpeed, swimDeg, 2, ticks, phase, tailChain);
            wave (model, swimSpeed * 0.05F, swimDeg * 0.25F, 2, ticks, phase, tailChain);

            swing(model, swimSpeed * 0.8F, swimDeg * 0.12F, 2, ticks, phase, spine);
            wave (model, swimSpeed * 0.6F, swimDeg * 0.06F, 2, ticks, phase, spine);

            swing(model, swimSpeed * 0.8F, -swimDeg * 0.08F, 2, ticks, phase, neckChain);
            wave (model, swimSpeed * 0.6F, -swimDeg * 0.05F, 2, ticks, phase, neckChain);

            if (hips != null) hips.rotateAngleZ += (float)Math.sin(ticks * swimSpeed) * (swimDeg * 0.10F);


            if (leftArm  != null)  leftArm .rotateAngleX += (float)Math.sin(ticks * 0.2F) * 0.05F;
            if (rightArm != null)  rightArm.rotateAngleX += (float)Math.cos(ticks * 0.2F) * 0.05F;

            model.faceTarget(rotationYaw * 0.5F, rotationPitch * 0.5F, 1.0F, nn(neckthroat,neck, neck2, neck3, head));
        } else {
            model.faceTarget(rotationYaw, rotationPitch, 1.0F, nn(neckthroat,neck, neck2, neck3, head));
        }
    }
}
