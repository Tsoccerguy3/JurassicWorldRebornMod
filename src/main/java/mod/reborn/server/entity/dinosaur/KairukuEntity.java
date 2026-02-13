package mod.reborn.server.entity.dinosaur;

import mod.reborn.client.model.animation.EntityAnimation;
import mod.reborn.client.sound.SoundHandler;
import mod.reborn.server.entity.PenguinDinosaurEntity;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntitySquid;

public class KairukuEntity extends PenguinDinosaurEntity
{
    public KairukuEntity(World world)
    {
        super(world);
        this.target(EntitySquid.class);
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.KAIRUKU_LIVING;
            case CALLING:
                return SoundHandler.KAIRUKU_CALL;
            case DYING:
                return SoundHandler.KAIRUKU_DEATH;
            case INJURED:
                return SoundHandler.KAIRUKU_HURT;
            case BEGGING:
                return SoundHandler.KAIRUKU_CALL;
            default:
                return null;
        }
    }
}
