package mod.reborn.server.entity.dinosaur;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.animal.EntityCrab;
import mod.reborn.server.entity.animal.EntityShark;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public class MegalodonEntity extends SwimmingDinosaurEntity
{
    public MegalodonEntity(World world)
    {
        super(world);
        this.target(CoelacanthEntity.class, EntityCrab.class, EntityShark.class, BeelzebufoEntity.class,LivyatanEntity.class,DiplocaulusEntity.class, TylosaurusEntity.class, OrthocerasEntity.class,CamerocerasEntity.class, EndocerasEntity.class,MawsoniaEntity.class, CrassigyrinusEntity.class, AsterocerasEntity.class, ParapuzosiaEntity.class, TitanisEntity.class, AlligatorGarEntity.class, MegapiranhaEntity.class, EntitySquid.class);
    }

    @Override
    public net.minecraft.util.SoundEvent getSoundForAnimation(net.ilexiconn.llibrary.server.animation.Animation animation)
    {
        return null;
    }
}
