
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DiplodocusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DiplodocusAnimator extends EntityAnimator<DiplodocusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, DiplodocusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer hips = model.getCube("hips");
        AdvancedModelRenderer body = model.getCube("body");
        AdvancedModelRenderer head = model.getCube("Head");

        // neck chain (head first for chain helpers)
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

        AdvancedModelRenderer[] neck = new AdvancedModelRenderer[]{
                head, neck10, neck9, neck8, neck7, neck6, neck5, neck4, neck3, neck2, neck1
        };

        // Tail segments in this export aren’t named plainly (lots of backcubes);

        AdvancedModelRenderer tail1 = model.getCube("tail1");
        AdvancedModelRenderer tail2 = model.getCube("tail2");
        AdvancedModelRenderer tail3 = model.getCube("tail3");
        AdvancedModelRenderer tail4 = model.getCube("tail4");
        AdvancedModelRenderer tail5 = model.getCube("tail5");
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{ tail5, tail4, tail3, tail2 };

        float idleSpeed  = 0.08F;
        float idleDegree = 0.08F;

        // breathing on hips/body
        model.bob(hips, idleSpeed, 0.60F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{ hips, body }, idleSpeed * 0.6F, idleDegree * 0.4F, 2, ticks, 1.0F);

        // neck undulation and head subtle sway/bob
        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.6F, -2, ticks, 1.0F);

        model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
        model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);


        // tail sway (if tail cubes are present)

            model.chainSwing(tail, idleSpeed, 0.17F, -2, ticks, 1.0F);
            if (limbSwingAmount > 0.12F) {
                model.chainSwing(tail, 0.55F, 0.12F, -2, limbSwing, limbSwingAmount);
            }
            entity.tailBuffer.applyChainSwingBuffer(tail);


        model.faceTarget(rotationYaw, rotationPitch, 0.9F, neck1, neck2, neck3, neck4, neck5, neck6, neck7, neck8, neck9, neck10, head
        );
    }
}
