
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.BaryonyxEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BaryonyxAnimator extends EntityAnimator<BaryonyxEntity> {

    private static float clamp(float v, float lo, float hi){ return v < lo ? lo : (v > hi ? hi : v); }
    private static float rad(float d){ return (float)Math.toRadians(d); }

    @Override
    protected void performAnimations(AnimatableModel model, BaryonyxEntity entity,
                                     float f, float f1, float ticks, float yaw, float pitch, float scale) {

        // Core / chest
        AdvancedModelRenderer Body1 = model.getCube("Body 1");
        AdvancedModelRenderer Body2 = model.getCube("Body 2");
        AdvancedModelRenderer Body3 = model.getCube("Body 3");
        AdvancedModelRenderer Throat1 = model.getCube("Throat 1");

        // Neck (base -> tip) and head
        AdvancedModelRenderer Neck1 = model.getCube("Neck1");
        AdvancedModelRenderer Neck2 = model.getCube("Neck2");
        AdvancedModelRenderer Neck3 = model.getCube("Neck3");
        AdvancedModelRenderer Neck4 = model.getCube("Neck4");
        AdvancedModelRenderer Neck5 = model.getCube("Neck5");
        AdvancedModelRenderer Neck6 = model.getCube("Neck6");
        AdvancedModelRenderer Head = model.getCube("Head");

        // Jaw (optional gentle idle)
        AdvancedModelRenderer LowerJawmain = model.getCube("Lower Jaw main");

        // chains
        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[]{
                Body1, Body2, Body3
        };
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{
                Neck1, Neck2, Neck3, Neck4, Neck5, Neck6, Head
        };

        // ------------ idle breathing ------------
        // soft wave on torso and neck; keep it subtle
        model.chainWave(bodyChain, 0.10F, 0.05F, 2, ticks, 0.35F);
        model.chainWave(neckChain, 0.12F, 0.04F, -3, ticks, 0.30F);
        if (!entity.isCarcass()) {

            if (Throat1 != null) Throat1.rotateAngleX += (float) Math.sin(ticks * 0.12F) * 0.04F;
            if (LowerJawmain != null) LowerJawmain.rotateAngleX += (float) Math.sin(ticks * 0.10F + 0.6F) * 0.03F;

            // ------------ head / neck look ------------
            float lookYaw = clamp(rad(yaw), rad(-45F), rad(45F));
            float lookPitch = clamp(rad(pitch), rad(-25F), rad(25F));

            // distribute look along the chain (base -> tip)
            float[] w = new float[]{0.08F, 0.10F, 0.13F, 0.17F, 0.20F, 0.20F, 0.12F};
            for (int i = 0; i < neckChain.length && i < w.length; i++) {
                AdvancedModelRenderer p = neckChain[i];
                if (p == null) continue;
                p.rotateAngleY += lookYaw * w[i];
                p.rotateAngleX += lookPitch * w[i];
            }
        }

    }
}
