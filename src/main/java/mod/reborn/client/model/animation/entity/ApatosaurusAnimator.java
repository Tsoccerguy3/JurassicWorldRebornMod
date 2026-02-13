
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.ApatosaurusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ApatosaurusAnimator extends EntityAnimator<ApatosaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, ApatosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount,
                                     float ticks, float rotationYaw,
                                     float rotationPitch, float scale) {



        AdvancedModelRenderer body   = model.getCube("body");
        AdvancedModelRenderer neck1  = model.getCube("neck1");
        AdvancedModelRenderer neck2  = model.getCube("neck2");
        AdvancedModelRenderer neck3  = model.getCube("neck3");
        AdvancedModelRenderer neck4  = model.getCube("neck4");
        AdvancedModelRenderer tail1  = model.getCube("tail1");
        AdvancedModelRenderer tail2  = model.getCube("tail2");
        AdvancedModelRenderer tail3  = model.getCube("tail3");
        AdvancedModelRenderer tail4  = model.getCube("tail4");
        AdvancedModelRenderer tail5  = model.getCube("tail5");
        AdvancedModelRenderer tail6  = model.getCube("tail6");
        AdvancedModelRenderer tail7  = model.getCube("tail7");
        AdvancedModelRenderer tail8  = model.getCube("tail8");
        AdvancedModelRenderer tail9  = model.getCube("tail9");

        AdvancedModelRenderer topLegLeft  = model.getCube("toplegleft");
        AdvancedModelRenderer topLegRight = model.getCube("toplegright");


        // --- Create arrays for chain-based animations. ---

        AdvancedModelRenderer[] tailArray = {
                tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail1
        };


        AdvancedModelRenderer[] neckArray = {
                neck4, neck3, neck2, neck1
        };

        // --- Example: gentle tail swing while walking. ---
        // chainSwing(parts, speed, degree, offset, ticks, distance)
        model.chainSwing(tailArray, 0.4F, 0.05F, 3.0D, limbSwing, limbSwingAmount);

        // --- Example: wave the neck slightly up and down. ---
        // chainWave(parts, speed, degree, offset, ticks, distance)
        model.chainWave(neckArray, 0.1F, 0.05F, 2.0D, ticks, 1.0F);

        // --- Example: animate the legs "walking." ---
        // walk(part, speed, degree, invert, offset, weight, ticks, limbSwingAmount)
        if (topLegLeft != null && topLegRight != null) {
            // Move left leg and right leg in opposite phase
            model.walk(topLegLeft,  0.5F, 0.3F, false,  0.0F, 0.0F, limbSwing, limbSwingAmount);
            model.walk(topLegRight, 0.5F, 0.3F, true,   0.0F, 0.0F, limbSwing, limbSwingAmount);
        }


        // bob(part, speed, degree, ignoreFacing, ticks, distance)
        if (body != null) {
            model.bob(body, 0.5F, 2.0F, false, limbSwing, limbSwingAmount);
        }



        // entity.tailBuffer.applyChainSwingBuffer(tailArray);
    }
}
