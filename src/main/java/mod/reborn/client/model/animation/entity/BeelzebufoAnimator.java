
package mod.reborn.client.model.animation.entity;


import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import mod.reborn.client.model.AnimatableModel;
import mod.reborn.client.model.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.BeelzebufoEntity;

public class BeelzebufoAnimator extends EntityAnimator<BeelzebufoEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, BeelzebufoEntity entity,
                                     float limbSwing, float limbSwingAmount,
                                     float ticks, float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelRenderer body = model.getCube("Body MAIN");
        AdvancedModelRenderer head = model.getCube("Main Head");


        model.bob(body, 0.3F, 0.2F, false, limbSwing, limbSwingAmount);

        model.chainWave(new AdvancedModelRenderer[]{head}, 0.1F, 0.05F, 0, ticks, 1.0F);
    }
}
