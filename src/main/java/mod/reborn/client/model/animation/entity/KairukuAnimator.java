
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mod.reborn.server.entity.dinosaur.KairukuEntity;

@SideOnly(Side.CLIENT)
public class KairukuAnimator extends EntityAnimator<KairukuEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, KairukuEntity entity,
                                     float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {

        // --- parts (match Tabula names) ---
        AdvancedModelRenderer body      = model.getCube("Body");
        AdvancedModelRenderer leftFoot  = model.getCube("LeftFoot");
        AdvancedModelRenderer rightFoot = model.getCube("RightFoot");
        AdvancedModelRenderer leftWing  = model.getCube("LeftWing");
        AdvancedModelRenderer rightWing = model.getCube("RightWing");
        AdvancedModelRenderer neck      = model.getCube("Neck");
        AdvancedModelRenderer head      = model.getCube("Head");
        AdvancedModelRenderer beak      = model.getCube("Beak");

        AdvancedModelRenderer[] bodyChain = new AdvancedModelRenderer[] { body, neck, head };

        // --- motion tuning ---
        float speed  = 0.9F;   // walk cycle speed
        float deg    = 1.0F;   // overall amplitude

        // Cute vertical bob while walking
            model.bob(body, speed * 0.50F, deg * 0.60F, false, f, f1);


        // Penguin waddle: gentle side sway of the torso
        // (flap = rotation around Z, swing = around Y)
        model.flap(body, speed * 0.50F, deg * 0.20F, true, 0.0F, 0.0F, f, f1);     // side tilt
        model.swing(body, speed * 0.50F, deg * 0.15F, true, 0.0F, 0.0F, f, f1);    // tiny yaw

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck, head);
    }
}
