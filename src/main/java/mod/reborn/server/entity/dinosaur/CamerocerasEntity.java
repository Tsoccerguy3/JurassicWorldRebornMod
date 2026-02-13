package mod.reborn.server.entity.dinosaur;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.animal.EntityCrab;
import mod.reborn.server.entity.animal.EntityShark;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntitySquid;

public class CamerocerasEntity extends SwimmingDinosaurEntity
{
    public CamerocerasEntity(World world)
    {
        super(world);
        this.target(EntitySquid.class, EntityShark.class, EntityCrab.class, AsterocerasEntity.class, OrthocerasEntity.class, PerisphinctesEntity.class, CoelacanthEntity.class, MawsoniaEntity.class, EndocerasEntity.class, TitanitesEntity.class, AlligatorGarEntity.class, BeelzebufoEntity.class, MegapiranhaEntity.class, CalymeneEntity.class);
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation)
    {
        return null;
    }
}
