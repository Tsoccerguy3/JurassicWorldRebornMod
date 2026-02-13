
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.server.entity.dinosaur.MajungasaurusEntity;

@SideOnly(Side.CLIENT)
public class MajungasaurusAnimator extends EntityAnimator<MajungasaurusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, MajungasaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelRenderer tail1 = model.getCube("tail");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer tail7 = model.getCube("tail7");
        AdvancedModelRenderer tail8 = model.getCube("tail8");

        AdvancedModelRenderer bodyRear = model.getCube("bodyhips");
        AdvancedModelRenderer bodyMid = model.getCube("body");
        AdvancedModelRenderer bodyFront = model.getCube("shoulder");

        AdvancedModelRenderer neck1 = model.getCube("neck");
        AdvancedModelRenderer neck2 = model.getCube("neck2");
        AdvancedModelRenderer neck3 = model.getCube("neck3");
        AdvancedModelRenderer neck4 = model.getCube("neck4");

        AdvancedModelRenderer head = model.getCube("head");


        AdvancedModelRenderer upperArmRight = model.getCube("rbicep");
        AdvancedModelRenderer upperArmLeft = model.getCube("lbicep");

        AdvancedModelRenderer lowerArmRight = model.getCube("rarm");
        AdvancedModelRenderer lowerArmLeft = model.getCube("larm");

        AdvancedModelRenderer handRight = model.getCube("rlowerfinger");
        AdvancedModelRenderer handLeft = model.getCube("llowerfinger");

        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] { tail1, tail2, tail3, tail4, tail5, tail6 };

        AdvancedModelRenderer[] armLeft = new AdvancedModelRenderer[] { upperArmLeft, lowerArmLeft, handLeft };
        AdvancedModelRenderer[] armRight = new AdvancedModelRenderer[] { upperArmRight, lowerArmRight, handRight };

        AdvancedModelRenderer[] body = new AdvancedModelRenderer[] { bodyRear, bodyMid, bodyFront, neck1, neck2, neck3, neck4, head };

        float globalSpeed = 0.5F;
        float globalHeight = 1.0F;

        model.bob(bodyRear, globalSpeed * 1F, globalHeight * 0.8F, false, f, f1);

//        model.chainWave(body, globalSpeed * 1F, globalHeight * -0.02F, -3, f, f1);
        model.chainWave(tail, globalSpeed * 1F, globalHeight * 0.05F, -2, f, f1);

        model.chainWave(armRight, globalSpeed * 1F, globalHeight * -0.25F, -3, f, f1);
        model.chainWave(armLeft, globalSpeed * 1F, globalHeight * -0.25F, -3, f, f1);

        model.chainWave(tail, 0.1F, 0.05F, -2, ticks, 0.25F);
        model.chainWave(body, 0.1F, 0.03F, -5, ticks, 0.25F);
        model.chainWave(armRight, 0.1F, 0.1F, -4, ticks, 0.25F);
        model.chainWave(armLeft, 0.1F, 0.1F, -4, ticks, 0.25F);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
