
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.TriceratopsEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TriceratopsAnimator extends EntityAnimator<TriceratopsEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, TriceratopsEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- core cubes (from triceratops_adult_idle) ---
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

        // Frill pieces (optional subtle flutter)
        AdvancedModelRenderer frillConn = model.getCube("frill connection");
        AdvancedModelRenderer frillMidT = model.getCube("frill middle top");
        AdvancedModelRenderer frillRT   = model.getCube("frill right top");
        AdvancedModelRenderer frillLT   = model.getCube("frill left top");

        AdvancedModelRenderer[] torso = new AdvancedModelRenderer[]{ body1, body2, body3 };
        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[]{ head, neck };
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail7, tail6, tail5, tail4, tail3, tail2, tail1 };
        AdvancedModelRenderer[] frill = new AdvancedModelRenderer[]{ frillMidT, frillRT, frillLT };

        // --- tuning ---
        float idleSpeed  = 0.10F; // Hz-ish
        float idleDegree = 0.08F; // amplitude

        // --- breathing: bob + gentle torso wave ---
        model.bob(body1, idleSpeed, 0.55F, false, ticks, 1.0F);
        model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // --- neck & head subtle motion ---
        model.chainWave(neckChain, idleSpeed * 0.6F, idleDegree * 0.55F, -2, ticks, 1.0F);
        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);


        // --- tail sway (idle) + locomotion overlay on move ---
        model.chainSwing(tail, idleSpeed, 0.16F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.55F, 0.12F, -2, limbSwing, limbSwingAmount);
        }
        entity.tailBuffer.applyChainSwingBuffer(tail);

        // --- frill flutter (very subtle, mostly cosmetic) ---

            model.chainWave(frill, idleSpeed * 1.2F, 0.04F, 2, ticks, 1.0F);
            model.chainSwing(frill, idleSpeed * 1.1F, 0.05F, 2, ticks, 1.0F);

            // tiny pivot at the connection to give the shield a living feel
            model.chainWave(new AdvancedModelRenderer[]{ frillConn }, idleSpeed, 0.02F, 0, ticks, 1.0F);


        // --- face target (head last for strongest influence) ---
        model.faceTarget(rotationYaw, rotationPitch, 0.85F,
                neck, head
        );
    }
}
