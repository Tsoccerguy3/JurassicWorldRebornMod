
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.SmilodonEntity;

@SideOnly(Side.CLIENT)
public class SmilodonAnimator extends EntityAnimator<SmilodonEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, SmilodonEntity entity,
                                     float limbSwing, float limbSwingAmount,
                                     float ticks, float rotationYaw, float rotationPitch,
                                     float scale) {

        // === MAIN BODY PIECES ===
        AdvancedModelRenderer body      = model.getCube("body");
        AdvancedModelRenderer body2     = model.getCube("body 2");
        AdvancedModelRenderer hip       = model.getCube("Hip");
        AdvancedModelRenderer neckBase  = model.getCube("Neck Base");
        AdvancedModelRenderer head      = model.getCube("Head");


        AdvancedModelRenderer tailBase  = model.getCube("shape1");


        // === CHAINS ===
        AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] {
                 tailBase
        };

        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[] {
                hip, body2, body, neckBase, head
        };

        AdvancedModelRenderer[] neckChain = new AdvancedModelRenderer[] {
                neckBase, head
        };


        float globalSpeed  = 0.6F;
        float globalDegree = 1.0F;

        // ====== IDLE/BREATHING ANIMATION ======
        // Gentle breathing on the whole torso + neck/head
        model.chainWave(bodyChain, 0.08F, 0.03F, 2, ticks, 0.5F);
        model.chainWave(neckChain, 0.08F, 0.04F, 1, ticks, 0.5F);

        // Small idle tail sway
        model.chainWave(tail, 0.08F, 0.10F, 2, ticks, 0.5F);
        model.chainSwing(tail, 0.08F, 0.15F, 2, ticks, 0.5F);

        // ====== WALK / RUN CYCLE ======
        // Body bob while moving
        model.bob(body, globalSpeed * 0.5F, globalDegree * 0.7F, false, limbSwing, limbSwingAmount);
        model.bob(hip,  globalSpeed * 0.5F, globalDegree * 0.5F, false, limbSwing, limbSwingAmount);

        // Tail reacts with the gait
        model.chainWave(tail,  globalSpeed * 0.5F, globalDegree * 0.15F, 2, limbSwing, limbSwingAmount);
        model.chainSwing(tail, globalSpeed * 0.5F, globalDegree * 0.35F, 2, limbSwing, limbSwingAmount);

        // ====== HEAD LOOKING / AIMING ======
        // Neck + head track target
        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neckBase, head);


        if (entity.tailBuffer != null) {
            entity.tailBuffer.applyChainSwingBuffer(tail);
        }
    }
}
