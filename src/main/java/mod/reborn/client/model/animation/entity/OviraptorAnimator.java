
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.OviraptorEntity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OviraptorAnimator extends EntityAnimator<OviraptorEntity> {



    @Override
    protected void performAnimations(AnimatableModel model, OviraptorEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer hips   = model.getCube("bodyHips");
        AdvancedModelRenderer body1  = model.getCube("bodyBack");
        AdvancedModelRenderer body2  = model.getCube("bodyShoulders");
        AdvancedModelRenderer head   = model.getCube("head");


        AdvancedModelRenderer tail1  = model.getCube("tail1");
        AdvancedModelRenderer tail2  = model.getCube("tail2");
        AdvancedModelRenderer tail3  = model.getCube("tail3");
        AdvancedModelRenderer tail4  = model.getCube("tail4");
        AdvancedModelRenderer tail5  = model.getCube("tail5");

        AdvancedModelRenderer[] torso =  new AdvancedModelRenderer[]{hips, body1, body2};
        AdvancedModelRenderer[] neck  =  new AdvancedModelRenderer[]{head};
        AdvancedModelRenderer[] tail  =  new AdvancedModelRenderer[]{tail5, tail4, tail3, tail2, tail1};

        float idleSpeed  = 0.14F;
        float idleDegree = 0.09F;

        // breathing
        if (body1 != null) model.bob(body1, idleSpeed, 0.35F, false, ticks, 1.0F);
        if (torso.length > 1) model.chainWave(torso, idleSpeed * 0.8F, idleDegree * 0.35F, 2, ticks, 1.0F);

        // neck/head motion
        if (neck.length > 0) model.chainWave(neck, idleSpeed * 0.9F, idleDegree * 0.6F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.03F, false, ticks, 1.0F);
        }

        // tail sway (fan gets included as tip)
        if (tail.length > 0) {
            model.chainSwing(tail, idleSpeed, 0.22F, -2, ticks, 1.0F);
            if (limbSwingAmount > 0.12F)
                model.chainSwing(tail, 0.7F, 0.14F, -2, limbSwing, limbSwingAmount);
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }

        // face target
//        model.faceTarget(rotationYaw, rotationPitch, 0.55F, nn( head));
    }
}
