package mod.reborn.server.entity.dinosaur;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.animal.EntityCrab;
import mod.reborn.server.entity.animal.EntityShark;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntitySquid;

public class EndocerasEntity extends SwimmingDinosaurEntity
{
    public EndocerasEntity(World world)
    {
        super(world);
        this.target(EntitySquid.class, AsterocerasEntity.class, EntityCrab.class, EntityShark.class, OrthocerasEntity.class, PerisphinctesEntity.class, CalymeneEntity.class, CoelacanthEntity.class, MawsoniaEntity.class, CamerocerasEntity.class, TitanitesEntity.class, AlligatorGarEntity.class, BeelzebufoEntity.class, MegapiranhaEntity.class);
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation)
    {
        return null;
    }
}
