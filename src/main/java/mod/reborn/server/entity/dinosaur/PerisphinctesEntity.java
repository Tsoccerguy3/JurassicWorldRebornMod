package mod.reborn.server.entity.dinosaur;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntitySquid;

public class PerisphinctesEntity extends SwimmingDinosaurEntity
{
    public PerisphinctesEntity(World world)
    {
        super(world);
        this.target(EntitySquid.class);
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation)
    {
        return null;
    }
}
