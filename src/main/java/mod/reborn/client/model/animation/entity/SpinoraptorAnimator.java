
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.SpinoraptorEntity;

@SideOnly(Side.CLIENT)
public class SpinoraptorAnimator extends EntityAnimator<SpinoraptorEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, SpinoraptorEntity entity,
                                     float f, float f1, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- body cores ---
        AdvancedModelRenderer bodyRear   = model.getCube("Body Rear");
        AdvancedModelRenderer bodyMiddle = model.getCube("Body Middle");

        // --- tail chain (tip -> base) ---
        AdvancedModelRenderer tailBase = model.getCube("Tail Base");
        AdvancedModelRenderer tail2    = model.getCube("Tail 2");
        AdvancedModelRenderer tail3    = model.getCube("Tail 3");
        AdvancedModelRenderer tail4    = model.getCube("Tail 4");
        AdvancedModelRenderer tail6    = model.getCube("Tail 6");
        AdvancedModelRenderer tail7    = model.getCube("Tail 7");
        AdvancedModelRenderer tail8    = model.getCube("Tail 8");
        AdvancedModelRenderer tail9    = model.getCube("Tail 9");
        AdvancedModelRenderer tail10   = model.getCube("Tail 10");

        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{
                tail10, tail9, tail8, tail7, tail6, tail4, tail3, tail2, tailBase
        };

        // --- neck/head chain ---
        AdvancedModelRenderer neckBase = model.getCube("Neck Base");
        AdvancedModelRenderer neck2    = model.getCube("Neck 2");
        AdvancedModelRenderer neck3    = model.getCube("Neck 3");
        AdvancedModelRenderer neck4    = model.getCube("Neck 4");
        AdvancedModelRenderer neck5    = model.getCube("Neck 5");
        AdvancedModelRenderer neck6    = model.getCube("Neck 6");
        AdvancedModelRenderer neck7    = model.getCube("Neck 7");
        AdvancedModelRenderer neck8    = model.getCube("Neck 8");
        AdvancedModelRenderer neck9    = model.getCube("Neck 9");
        AdvancedModelRenderer head     = model.getCube("Head");

        AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[]{bodyRear, bodyMiddle, neckBase, head};

        // --- jaws (kept for possible blends/poses) ---
        AdvancedModelRenderer upperJaw = model.getCube("Upper Jaw");
        AdvancedModelRenderer lowerJaw = model.getCube("lower jaw");

        // --- tail buffer like Alvarezsaurus ---
        entity.tailBuffer.applyChainSwingBuffer(tail);

        float globalSpeed  = 0.6F;
        float globalDegree = 1.0F;

        // --- breathing / locomotion coupling (walk-parameter driven) ---
//        model.bob(bodyMiddle, globalSpeed * 0.25F, globalDegree * 1.5F, false, f, f1);

        model.chainWave(tail,      globalSpeed * 0.25F, globalDegree * 0.10F, 1, f, f1);
        model.chainSwing(tail,     globalSpeed * 0.25F, globalDegree * 0.20F, 2, f, f1);
        model.chainWave(bodyParts, globalSpeed * 0.25F, globalDegree * 0.025F, 3, f, f1);

        // --- idle breathing (tick-based, always on) ---
        model.chainWave(tail,      0.10F, 0.05F, 1, ticks, 0.25F);
        model.chainWave(bodyParts, 0.10F, -0.05F, 4, ticks, 0.25F);

        // --- head/neck target tracking ---
        model.faceTarget(rotationYaw, rotationPitch, 1.0F,
                neckBase, neck9, head);


        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
