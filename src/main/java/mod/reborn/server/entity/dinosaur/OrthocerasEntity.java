package mod.reborn.server.entity.dinosaur;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.animal.EntityCrab;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntitySquid;

public class OrthocerasEntity extends SwimmingDinosaurEntity
{
    public OrthocerasEntity(World world)
    {
        super(world);
        this.target(EntitySquid.class, DiplocaulusEntity.class, CalymeneEntity.class, EntityCrab.class, CalymeneEntity.class);
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation)
    {
        return null;
    }
}
