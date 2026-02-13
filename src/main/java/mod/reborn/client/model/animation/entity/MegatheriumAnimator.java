
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.MegatheriumEntity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MegatheriumAnimator extends EntityAnimator<MegatheriumEntity> {

    private static AdvancedModelRenderer[] nn(AdvancedModelRenderer... parts) {
        java.util.ArrayList<AdvancedModelRenderer> out = new java.util.ArrayList<>();
        for (AdvancedModelRenderer p : parts) if (p != null) out.add(p);
        return out.toArray(new AdvancedModelRenderer[0]);
    }

    @Override
    protected void performAnimations(AnimatableModel model, MegatheriumEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer hips   = model.getCube("hips");
        AdvancedModelRenderer body   = model.getCube("body");
        AdvancedModelRenderer belly  = model.getCube("belly");
        AdvancedModelRenderer chest  = model.getCube("chest");

        AdvancedModelRenderer neck1  = model.getCube("neck1");
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer throat3= model.getCube("throat3");
        AdvancedModelRenderer throat4= model.getCube("throat4");
        AdvancedModelRenderer head   = model.getCube("head");

        AdvancedModelRenderer tail1  = model.getCube("tail1");
        AdvancedModelRenderer tail2  = model.getCube("tail2");
        AdvancedModelRenderer tail3  = model.getCube("tail3");
        AdvancedModelRenderer tail4  = model.getCube("tail4");
        AdvancedModelRenderer tail5  = model.getCube("tail5");

        AdvancedModelRenderer[] torso = nn(hips, belly, body, chest);
        AdvancedModelRenderer[] neck  = nn(head, neck4, neck3, neck2, neck1, throat4, throat3);
        AdvancedModelRenderer[] tail  = nn(tail5, tail4, tail3, tail2, tail1);

        float idleSpeed  = 0.10F;
        float idleDegree = 0.10F;

        // breathing across torso
        if (belly != null) model.bob(belly, idleSpeed, 0.50F, false, ticks, 1.0F);
        if (hips  != null) model.bob(hips,  idleSpeed, 0.35F, false, ticks, 1.0F);
        if (torso.length > 1) model.chainWave(torso, idleSpeed * 0.65F, idleDegree * 0.45F, 2, ticks, 1.0F);

        // neck/head
        if (neck.length > 0) model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.5F, -2, ticks, 1.0F);
        if (head != null) {
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.08F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);
        }

        // tail sway
        if (tail.length > 0) {
            model.chainSwing(tail, idleSpeed, 0.14F, -2, ticks, 1.0F);
            if (limbSwingAmount > 0.12F)
                model.chainSwing(tail, 0.55F, 0.10F, -2, limbSwing, limbSwingAmount);
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }

        // look-at
        model.faceTarget(rotationYaw, rotationPitch, 0.85F,
                nn(neck1, neck2, neck3, neck4, head));
    }
}
