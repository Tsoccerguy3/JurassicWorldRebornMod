
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.AllosaurusEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AllosaurusAnimator extends EntityAnimator<AllosaurusEntity> {

    private static float clamp01(float v){ return v < 0 ? 0 : (v > 1 ? 1 : v); }

    @Override
    protected void performAnimations(AnimatableModel model, AllosaurusEntity entity,
                                     float f, float f1, float ticks, float yaw, float pitch, float scale) {
        AdvancedModelRenderer bodyhips = model.getCube("bodyhips");
        AdvancedModelRenderer body     = model.getCube("body");
        AdvancedModelRenderer belly    = model.getCube("belly");
        AdvancedModelRenderer chest    = model.getCube("chest");
        AdvancedModelRenderer shoulder = model.getCube("shoulder");

        AdvancedModelRenderer tail    = model.getCube("tail");
        AdvancedModelRenderer tai2    = model.getCube("tai2");
        AdvancedModelRenderer tai3    = model.getCube("tai3");
        AdvancedModelRenderer tai4    = model.getCube("tai4");
        AdvancedModelRenderer tail5   = model.getCube("tail5");
        AdvancedModelRenderer tail6   = model.getCube("tail6");
        AdvancedModelRenderer tail7   = model.getCube("tail7");
        AdvancedModelRenderer tail8   = model.getCube("tail8");
        AdvancedModelRenderer tail9   = model.getCube("tail9");
        AdvancedModelRenderer tail10  = model.getCube("tail10");

        AdvancedModelRenderer neck  = model.getCube("neck");
        AdvancedModelRenderer neck2 = model.getCube("neck2");
        AdvancedModelRenderer neck3 = model.getCube("neck3");
        AdvancedModelRenderer neck4 = model.getCube("neck4");
        AdvancedModelRenderer neck5 = model.getCube("neck5");
        AdvancedModelRenderer neck6 = model.getCube("neck6");
        AdvancedModelRenderer neck7 = model.getCube("neck7");
        AdvancedModelRenderer neck8 = model.getCube("neck8");
        AdvancedModelRenderer neck9 = model.getCube("neck9");
        AdvancedModelRenderer head  = model.getCube("head");

        AdvancedModelRenderer leftThigh  = model.getCube("leftleg");
        AdvancedModelRenderer rightThigh = model.getCube("rightleg");
        AdvancedModelRenderer leftCalf   = model.getCube("leftcalf");
        AdvancedModelRenderer rightCalf  = model.getCube("rightcalf");

        // chains base->tip
        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[]{ tail, tai2, tai3, tai4, tail5, tail6, tail7, tail8, tail9, tail10 };
        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[]{ bodyhips, body, belly, chest, shoulder, neck, head };
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{ neck, neck2, neck3, neck4, neck5, neck6, neck7, neck8, neck9, head };

//float delta = Minecraft.getMinecraft().getRenderPartialTicks();
        // global motion
        float speed  = 0.40F;
        float degree = 0.80F;

        // --- action/keyframe weight ---

//        float actionW = clamp01(entity.getAttackAnim(ticks)); // 0..1 while attacking/playing keyframe
        // When actions play, suppress idle/walk (leave 20% so it’s never dead still)
        float idleW   = 0.20F + 0.80F * (1.0F);

        // --- idle breathing (weighted) ---
        model.bob(bodyhips, speed * 0.25F, degree * 2.00F * idleW, false, f, f1);
        model.bob(belly,    speed * 0.15F, degree * 0.40F * idleW, false, f, f1);
        model.bob(chest,    speed * 0.15F, degree * 0.40F * idleW, false, f, f1);

        // --- tail (weighted & gentle) ---
        // tiny idle curl (ticks)
        model.chainWave (tailChain, 0.08F, 0.04F * idleW, 1, ticks, 0.25F);
        // gait sway
        model.chainSwing(tailChain, speed * 0.25F, degree * 0.18F * idleW, 2, f, f1);

        // extra counterbalance only when moving, still scaled by idleW
        if (f1 > 0.01F) {
            model.chainSwing(tailChain, speed * 0.50F, degree * 0.06F * idleW, 1, f, f1);
            model.bob(bodyhips,         speed * 0.80F, degree * 0.10F * idleW, false, f, f1);
        }

        // --- body/neck subtle motion (weighted) ---
        model.chainWave(bodyChain, speed * 0.20F, degree * 0.025F * idleW, 2, f, f1);
        model.chainWave(neckChain, 0.12F,         0.05F    * idleW,       -3, ticks, 0.30F);

        // tail buffer AFTER all rotations, once
        entity.tailBuffer.applyChainSwingBuffer(tailChain);
    }
}
