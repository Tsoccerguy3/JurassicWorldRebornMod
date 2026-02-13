
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.server.entity.dinosaur.MamenchisaurusEntity;

@SideOnly(Side.CLIENT)
public class MamenchisaurusAnimator extends EntityAnimator<MamenchisaurusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, MamenchisaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelRenderer head = model.getCube("Head");

        AdvancedModelRenderer neck1 = model.getCube("neck1");
        AdvancedModelRenderer neck2 = model.getCube("neck2");
        AdvancedModelRenderer neck3 = model.getCube("neck3");
        AdvancedModelRenderer neck4 = model.getCube("neck4");
        AdvancedModelRenderer neck5 = model.getCube("neck5");
        AdvancedModelRenderer neck6 = model.getCube("neck6");
        AdvancedModelRenderer neck7 = model.getCube("neck7");
        AdvancedModelRenderer neck8 = model.getCube("neck8");
        AdvancedModelRenderer neck9 = model.getCube("neck9");
        AdvancedModelRenderer neck10 = model.getCube("neck10");
        AdvancedModelRenderer neck11 = model.getCube("neck11");

        AdvancedModelRenderer waist = model.getCube("hips");
        AdvancedModelRenderer tail1 = model.getCube("tail1");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer tail6 = model.getCube("tail6");
        AdvancedModelRenderer tail7 = model.getCube("tail7");
        AdvancedModelRenderer tail8 = model.getCube("tail8");
        AdvancedModelRenderer tail9 = model.getCube("tail9");
        AdvancedModelRenderer tail10 = model.getCube("tail10");


        AdvancedModelRenderer lowerThighLeft = model.getCube("bottom front left leg");
        AdvancedModelRenderer lowerThighRight = model.getCube("bottom front right leg");

        AdvancedModelRenderer footLeft = model.getCube("left back foot");
        AdvancedModelRenderer footRight = model.getCube("left right foot");

        AdvancedModelRenderer armRight = model.getCube("front right top leg");
        AdvancedModelRenderer armLeft = model.getCube("front left top leg");

        AdvancedModelRenderer lowerArmRight = model.getCube("bottom front right leg");
        AdvancedModelRenderer lowerArmLeft = model.getCube("bottom front left leg");

        AdvancedModelRenderer handRight = model.getCube("front right foot");
        AdvancedModelRenderer handLeft = model.getCube("front left foot");

        AdvancedModelRenderer backLeftCalf = model.getCube("bottom leg left");
        AdvancedModelRenderer backLeftThigh = model.getCube("top leg left");

        AdvancedModelRenderer backRightThigh = model.getCube("top leg right");

        AdvancedModelRenderer backRightCalf = model.getCube("bottom leg right");

        AdvancedModelRenderer stomach = model.getCube("Stomach");
        AdvancedModelRenderer body = model.getCube("body");

        AdvancedModelRenderer[] neckParts = new AdvancedModelRenderer[] { head,  neck11, neck10, neck9, neck8, neck7,neck6, neck5, neck4, neck3, neck2, neck1, body };
        AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail10, tail9, tail8, tail7, tail6,tail5, tail4, tail3, tail2, tail1 };
float delta = Minecraft.getMinecraft().getRenderPartialTicks();

        LegArticulator.articulateQuadruped(entity, entity.legSolver, waist, neck1,
                backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, armLeft, lowerArmLeft, armRight, lowerArmRight,
                0.25F, 0.4F, -0.2F, -0.3F,
                delta
        );


        float globalSpeed = 0.5F;
        float globalHeight = 0.5F;
        float globalDegree = 0.5F;
        float frontOffset = 1.0F;
        float idleSpeed  = 0.08F;
        float idleDegree = 0.08F;
        model.bob(waist, globalSpeed * 1.0F, globalHeight * 4.0F, false, f, f1);
        model.bob(backLeftThigh, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);
        model.bob(backRightThigh, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);

        model.chainWave(neckParts, globalSpeed * 0.25F, globalHeight * 0.25F, -4, ticks, 0.025F);

        model.chainSwing(tailParts, idleSpeed, 0.17F, -2, ticks, 1.0F);
        if (f1 > 0.12F) {
            model.chainSwing(tailParts, 0.55F, 0.12F, -2, f, f1);
        }
        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
