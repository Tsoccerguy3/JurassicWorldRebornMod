
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.ChasmosaurusEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ChasmosaurusAnimator extends EntityAnimator<ChasmosaurusEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, ChasmosaurusEntity entity,
                                     float limbSwing, float limbSwingAmount, float ticks,
                                     float rotationYaw, float rotationPitch, float scale) {

        // --- parts from Tabula model ---
        AdvancedModelRenderer body1 = model.getCube("body 1");
        AdvancedModelRenderer body2 = model.getCube("body 2");
        AdvancedModelRenderer body3 = model.getCube("body 3");
        AdvancedModelRenderer neck  = model.getCube("neck");
        AdvancedModelRenderer head  = model.getCube("head");

        AdvancedModelRenderer tail1 = model.getCube("tail 1");
        AdvancedModelRenderer tail2 = model.getCube("tail 2");
        AdvancedModelRenderer tail3 = model.getCube("tail 3");
        AdvancedModelRenderer tail4 = model.getCube("tail 4");
        AdvancedModelRenderer tail5 = model.getCube("tail 5");
        AdvancedModelRenderer tail6 = model.getCube("tail 6");
        AdvancedModelRenderer tail7 = model.getCube("tail 7");

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] { head, neck };
        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[] { body1, body2, body3 };
        AdvancedModelRenderer[] tailChain = new AdvancedModelRenderer[] { tail7, tail6, tail5, tail4, tail3, tail2, tail1 };

        // --- tuning ---
        float idleSpeed  = 0.10F;  // frequency
        float idleDegree = 0.08F;  // amplitude

        // --- idle breathing ---
        model.bob(body1, idleSpeed, 0.65F, false, ticks, 1.0F);
        model.chainWave(bodyChain, idleSpeed * 0.6F, idleDegree * 0.35F, 2, ticks, 1.0F);

        // --- neck & head subtle motion ---
        model.chainWave(neckChain, idleSpeed * 0.6F, idleDegree * 0.6F, -2, ticks, 1.0F);
            model.chainSwing(new AdvancedModelRenderer[]{ head }, idleSpeed * 0.9F, 0.10F, 0, ticks, 1.0F);
            model.bob(head, idleSpeed, 0.04F, false, ticks, 1.0F);


        model.chainSwing(tailChain, idleSpeed, 0.16F, -2, ticks, 1.0F);

        if (limbSwingAmount > 0.15F) {model.chainSwing(tailChain, 0.6F, 0.12F, -2, limbSwing, limbSwingAmount);}

        model.faceTarget(rotationYaw, rotationPitch, 0.9F, neck, head);

        entity.tailBuffer.applyChainSwingBuffer(tailChain);
    }
}
