package mod.reborn.server.entity.dinosaur;

import mod.reborn.client.model.animation.EntityAnimation;
import mod.reborn.client.sound.SoundHandler;
import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.animal.EntityCrab;
import mod.reborn.server.entity.animal.EntityShark;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public class LivyatanEntity extends SwimmingDinosaurEntity
{
    public LivyatanEntity(World world)
    {
        super(world);
        this.target(CoelacanthEntity.class,OrthocerasEntity.class,CamerocerasEntity.class, EndocerasEntity.class,MawsoniaEntity.class, CrassigyrinusEntity.class, AsterocerasEntity.class, ParapuzosiaEntity.class, TitanisEntity.class, AlligatorGarEntity.class, MegapiranhaEntity.class, EntityShark.class, EntityCrab.class, EntitySquid.class);
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.LIVYATAN_LIVING;
            case CALLING:
                return SoundHandler.LIVYATAN_CALL;
            case DYING:
                return SoundHandler.LIVYATAN_DEATH;
            case INJURED:
                return SoundHandler.LIVYATAN_HURT;
            case BEGGING:
                return SoundHandler.LIVYATAN_CALL;
            default:
                return null;
        }
    }
}
