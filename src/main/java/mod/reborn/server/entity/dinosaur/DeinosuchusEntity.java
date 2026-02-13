package mod.reborn.server.entity.dinosaur;

import mod.reborn.client.model.animation.EntityAnimation;
import mod.reborn.client.sound.SoundHandler;
import mod.reborn.server.entity.AmfibianDinosaurEntity;
import mod.reborn.server.entity.CrocodileDinosaurEntity;
import mod.reborn.server.entity.DinosaurEntity;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class DeinosuchusEntity extends CrocodileDinosaurEntity
{
    public DeinosuchusEntity(World world)
    {
        super(world);
        this.target(AchillobatorEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChilesaurusEntity.class, KairukuEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, CalymeneEntity.class, DodoEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, VelociraptorEchoEntity.class, DilophosaurusEntity.class, VelociraptorDeltaEntity.class, HyaenodonEntity.class, OrnithomimusEntity.class, GuanlongEntity.class, MetriacanthosaurusEntity.class, ProceratosaurusEntity.class, RugopsEntity.class, VelociraptorEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class );
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case CALLING:
                return SoundHandler.DEINOSUCHUS_CALL;
            case SPEAK:
                return SoundHandler.DEINOSUCHUS_LIVING;
            case DYING:
                return SoundHandler.DEINOSUCHUS_DEATH;
            case INJURED:
                return SoundHandler.DEINOSUCHUS_INJURED;
            case ATTACKING:
                return SoundHandler.DEINOSUCHUS_ATTACK;
            case DEATH_ROLL:
                return SoundHandler.DEINOSUCHUS_DEATH_ROLL;
            default:
                return null;
        }
    }
}
