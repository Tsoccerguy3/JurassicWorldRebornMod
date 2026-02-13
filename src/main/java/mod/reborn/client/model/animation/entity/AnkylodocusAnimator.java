
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.AnkylodocusEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnkylodocusAnimator extends EntityAnimator<AnkylodocusEntity> {

    private static float clamp01(float v){ return v < 0 ? 0 : (v > 1 ? 1 : v); }

    @Override
    protected void performAnimations(AnimatableModel model, AnkylodocusEntity entity,
                                     float f, float f1, float ticks, float yaw, float pitch, float scale) {

        // Core
        AdvancedModelRenderer body = model.getCube("body");
        AdvancedModelRenderer hips = model.getCube("hips");

        // Neck (base -> tip)
        AdvancedModelRenderer neck1  = model.getCube("neck1");
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer neck5  = model.getCube("neck5");
        AdvancedModelRenderer neck6  = model.getCube("neck6");
        AdvancedModelRenderer neck7  = model.getCube("neck7");
        AdvancedModelRenderer neck8  = model.getCube("neck8");
        AdvancedModelRenderer neck9  = model.getCube("neck9");
        AdvancedModelRenderer neck10 = model.getCube("neck10");


        AdvancedModelRenderer bottomjaw = model.getCube("bottom jaw");
        AdvancedModelRenderer jawflap   = model.getCube("jaw flap");

        // Tail (base -> tip)
        AdvancedModelRenderer tail1  = model.getCube("tail1");
        AdvancedModelRenderer tail2  = model.getCube("tail2");
        AdvancedModelRenderer tail3  = model.getCube("tail3");
        AdvancedModelRenderer tail4  = model.getCube("tail4");
        AdvancedModelRenderer tail5  = model.getCube("tail5");
        AdvancedModelRenderer tail6  = model.getCube("tail6");
        AdvancedModelRenderer tail7  = model.getCube("tail7");
        AdvancedModelRenderer tail8  = model.getCube("tail8");
        AdvancedModelRenderer tail9  = model.getCube("tail9");
        AdvancedModelRenderer tail10 = model.getCube("tail10");
        AdvancedModelRenderer tailclub   = model.getCube("tail club0");
        AdvancedModelRenderer tailclub_1 = model.getCube("tail club1");
        AdvancedModelRenderer tailclub_2 = model.getCube("tail club2");

        // Chains (match Allosaurus style: base -> tip)
        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[] {
                tail1, tail2, tail3, tail4, tail5, tail6, tail7, tail8, tail9, tail10, tailclub, tailclub_1, tailclub_2
        };
        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[] {
                hips, body, neck1 // keep it minimal; no distinct chest/shoulder cubes in this model
        };
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {
                neck1, neck2, neck3, neck4, neck5, neck6, neck7, neck8, neck9, neck10
        };

//        float delta  = Minecraft.getInstance().getDeltaFrameTime();
        float speed  = 0.40F;
        float degree = 0.80F;

        // action weight like Allosaurus: dampen idle/walk while attacking/keyframing
//        float actionW = clamp01(entity.getAttackAnim(ticks));
        float idleW   = 0.20F + 0.80F * (1.0F);

        // --- idle breathing / body bob ---
        model.bob(hips, speed * 0.25F, degree * 1.60F * idleW, false, f, f1);
        model.bob(body, speed * 0.20F, degree * 0.60F * idleW, false, f, f1);

        // --- tail: small idle curl + gait sway (weighted like Allosaurus) ---
        model.chainWave (tailChain, 0.08F, 0.04F * idleW, 1, ticks, 0.25F);              // idle
        model.chainSwing(tailChain, speed * 0.25F, degree * 0.16F * idleW, 2, f, f1);     // gait

        // when actually moving, add a bit more counterbalance & bob
        if (f1 > 0.01F) {
            model.chainSwing(tailChain, speed * 0.50F, degree * 0.05F * idleW, 1, f, f1);
            model.bob(hips,            speed * 0.80F, degree * 0.10F * idleW, false, f, f1);
        }

        // --- body/neck subtle motion (same flavor as Allosaurus) ---
        model.chainWave(bodyChain, speed * 0.20F, degree * 0.025F * idleW, 2, f, f1);
        model.chainWave(neckChain, 0.12F,         0.05F    * idleW,       -3, ticks, 0.30F);

        // jaw micro-motion so the head doesn’t feel dead
        if (bottomjaw != null) bottomjaw.rotateAngleX += (float)Math.sin(ticks * 0.05F) * 0.03F;
        if (jawflap   != null) jawflap.rotateAngleX   += (float)Math.sin(ticks * 0.05F + 0.6F) * 0.02F;


        entity.tailBuffer.applyChainSwingBuffer(tailChain);
    }
}
