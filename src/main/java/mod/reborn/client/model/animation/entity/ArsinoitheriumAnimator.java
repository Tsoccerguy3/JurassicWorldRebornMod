
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.ArsinoitheriumEntity;

public class ArsinoitheriumAnimator extends EntityAnimator<ArsinoitheriumEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, ArsinoitheriumEntity entity,
                                     float limbSwing, float limbSwingAmount,
                                     float ticks, float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer head = model.getCube("Head");
        AdvancedModelRenderer bodymiddle = model.getCube("Body middle");
        AdvancedModelRenderer neck = model.getCube("Neck 1");
        AdvancedModelRenderer tailbase = model.getCube("tail_base");
        model.chainSwing(new AdvancedModelRenderer[]{tailbase}, 0.1F, 0.15F, 0, ticks, 1.0F);
        model.bob(head, 0.1F, 0.05F, false, ticks, 1.0F);
        model.chainWave(new AdvancedModelRenderer[]{bodymiddle,neck, head}, 0.05F, 0.03F, -2, ticks, 1.0F);
    }
}
