
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.ParasaurolophusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParasaurolophusAnimator extends EntityAnimator<ParasaurolophusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, ParasaurolophusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- core parts (names from parasaurolophus_adult_idle) ---
        AdvancedModelRenderer body1 = model.getCube("Body1");
        AdvancedModelRenderer body2 = model.getCube("Body2");
        AdvancedModelRenderer body3 = model.getCube("Body3");                              // body chain
        AdvancedModelRenderer head  = model.getCube("Head");                               // head
        AdvancedModelRenderer neck1 = model.getCube("Neck1");
        AdvancedModelRenderer neck2 = model.getCube("Neck2");
        AdvancedModelRenderer neck3 = model.getCube("Neck3");
        AdvancedModelRenderer neck4 = model.getCube("Neck4");
        AdvancedModelRenderer neck5 = model.getCube("Neck5");
        AdvancedModelRenderer neck6 = model.getCube("Neck6");
        AdvancedModelRenderer neck7 = model.getCube("Neck7");
        AdvancedModelRenderer neck8 = model.getCube("Neck8");
        AdvancedModelRenderer neck9 = model.getCube("Neck9");
        AdvancedModelRenderer neck10 = model.getCube("Neck10");
        AdvancedModelRenderer neck11 = model.getCube("Neck11");
        AdvancedModelRenderer neck12 = model.getCube("Neck12");
        AdvancedModelRenderer neck13 = model.getCube("Neck13");                             // long neck chain
        AdvancedModelRenderer tail1 = model.getCube("Tail1");
        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("Tail4");
        AdvancedModelRenderer tail5 = model.getCube("Tail5");
        AdvancedModelRenderer tail6 = model.getCube("Tail6");
        AdvancedModelRenderer tail7 = model.getCube("Tail7");                               // tail chain


        AdvancedModelRenderer[] torso = new AdvancedModelRenderer[]{ body1, body2, body3 };
        AdvancedModelRenderer[] neck  = new AdvancedModelRenderer[]{ head, neck13, neck12, neck11, neck10, neck9, neck8, neck7, neck6, neck5, neck4, neck3, neck2, neck1 };
        AdvancedModelRenderer[] tail  = new AdvancedModelRenderer[]{ tail7, tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- tuning ---
        float idleSpeed  = 0.10F; // frequency
        float idleDegree = 0.08F; // amplitude

        // --- breathing (torso bob + gentle wave) ---
        if (body1 != null) model.bob(body1, idleSpeed, 0.55F, false, ticks, 1.0F);
        model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // --- neck & head subtle motion ---
        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.6F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.035F, false, ticks, 1.0F);
        }

        // --- tail sway (idle) + locomotion layer when moving ---
        model.chainSwing(tail, idleSpeed, 0.18F, -2, ticks, 1.0F);
        if (limbSwingAmount > 0.12F) {
            model.chainSwing(tail, 0.60F, 0.13F, -2, limbSwing, limbSwingAmount);
        }
        entity.tailBuffer.applyChainSwingBuffer(tail);

        // --- look-at (face target) along the long neck; head last for strongest influence ---
        model.faceTarget(rotationYaw, rotationPitch, 0.9F,
                neck1, neck2, neck3, neck4, neck5, neck6, neck7, neck8, neck9, neck10, neck11, neck12, neck13, head
        );
    }
}
