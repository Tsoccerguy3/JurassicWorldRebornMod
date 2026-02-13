
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.TherizinosaurusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TherizinosaurusAnimator extends EntityAnimator<TherizinosaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, TherizinosaurusEntity entity,
                                     float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {


        AdvancedModelRenderer body  = model.getCube("Body");
        AdvancedModelRenderer neck1 = model.getCube("Neck1");
        AdvancedModelRenderer neck2 = model.getCube("Neck2");
        AdvancedModelRenderer neck3 = model.getCube("Neck3");
        AdvancedModelRenderer neck4 = model.getCube("Neck4");
        AdvancedModelRenderer neck5 = model.getCube("Neck5");
        AdvancedModelRenderer neck6 = model.getCube("Neck6");
        AdvancedModelRenderer head  = model.getCube("Head");

        AdvancedModelRenderer tail1 = model.getCube("Tail1");
        AdvancedModelRenderer tail2 = model.getCube("Tail2");
        AdvancedModelRenderer tail3 = model.getCube("Tail3");
        AdvancedModelRenderer tail4 = model.getCube("Tail4");
        AdvancedModelRenderer tail5 = model.getCube("Tail5");
        AdvancedModelRenderer tail6 = model.getCube("Tail6");


        AdvancedModelRenderer[] tail      = new AdvancedModelRenderer[]{ tail6, tail5, tail4, tail3, tail2, tail1 };
        AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[]{ body, neck1, head };
        AdvancedModelRenderer[] headChain = new AdvancedModelRenderer[]{ neck1, neck2, neck3, neck4, neck5, neck6 };


        entity.tailBuffer.applyChainSwingBuffer(tail);

        // --- tuning ---
        float globalSpeed  = 0.6F;
        float globalDegree = 1.0F;

        // gentle body bob while moving
        if (body != null) {
            model.bob(body, globalSpeed * 0.25F, globalDegree * 1.5F, false, f, f1);
        }

        // locomotion-driven tail & body motion
        model.chainWave(tail,      globalSpeed * 0.25F, globalDegree * 0.10F, 1, f, f1);
        model.chainSwing(tail,     globalSpeed * 0.25F, globalDegree * 0.40F, 2, f, f1);
        model.chainWave(bodyParts, globalSpeed * 0.25F, globalDegree * 0.025F, 3, f, f1);

        // idle micro-motion
        model.chainWave(tail,      0.10F, 0.05F, 1, ticks, 0.25F);
        model.chainWave(bodyParts, 0.10F, -0.05F, 4, ticks, 0.25F);

        // look where we’re facing (distributes across neck to head)
        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, neck2, neck3, neck4, neck5, neck6, head);


        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
