

package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DreadnoughtusEntity;
import mod.reborn.server.entity.dinosaur.PatagotitanEntity;

@SideOnly(Side.CLIENT)
public class PatagotitanAnimator extends EntityAnimator<PatagotitanEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, PatagotitanEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // Core parts
        final AdvancedModelRenderer body = model.getCube("Body");
        final AdvancedModelRenderer head = model.getCube("Head");
        final AdvancedModelRenderer jaw = model.getCube("Jaw");

        final AdvancedModelRenderer neck1 = model.getCube("Neck1");
        final AdvancedModelRenderer neck2 = model.getCube("Neck2");
        final AdvancedModelRenderer neck3 = model.getCube("Neck3");
        final AdvancedModelRenderer neck4 = model.getCube("Neck4");
        final AdvancedModelRenderer neck5 = model.getCube("Neck5");
        final AdvancedModelRenderer neck6 = model.getCube("Neck6");
        final AdvancedModelRenderer neck7 = model.getCube("Neck7");
        final AdvancedModelRenderer neck8 = model.getCube("Neck8");

        final AdvancedModelRenderer[] neck = new AdvancedModelRenderer[]{neck1, neck2, neck3, neck4, neck5, neck6, neck7, neck8, head};

        final AdvancedModelRenderer tail1 = model.getCube("Tail1");
        final AdvancedModelRenderer tail2 = model.getCube("Tail2");
        final AdvancedModelRenderer tail3 = model.getCube("bone");
        final AdvancedModelRenderer tail4 = model.getCube("bone3");
        final AdvancedModelRenderer tail5 = model.getCube("bone4");
        final AdvancedModelRenderer tail6 = model.getCube("bone5");
        final AdvancedModelRenderer tail7 = model.getCube("bone6");
        final AdvancedModelRenderer tail8 = model.getCube("bone7");
        final AdvancedModelRenderer tail9 = model.getCube("bone8");
        final AdvancedModelRenderer[] tail = new AdvancedModelRenderer[]{tail1,tail2,tail3,tail4,tail5,tail6,tail7,tail8,tail9};


        float idleSpeed  = 0.09F;
        float idleDegree = 0.08F;

        model.bob(body, idleSpeed, 0.6F, false, ticks, 1.0F);
        model.chainWave(neck, idleSpeed * 0.6F, idleDegree * 0.4F, -2, ticks, 1.0F);
        model.chainSwing(tail, idleSpeed, 0.16F, -2, ticks, 1.0F);
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
