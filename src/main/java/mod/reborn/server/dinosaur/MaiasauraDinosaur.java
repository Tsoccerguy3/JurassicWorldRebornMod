package mod.reborn.server.dinosaur;

import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.SleepTime;
import mod.reborn.server.entity.dinosaur.TitanisEntity;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import mod.reborn.server.entity.dinosaur.*;
public class MaiasauraDinosaur extends Dinosaur
{
    public static final double SPEED = 0.41F;
    public MaiasauraDinosaur()
    {
        super();
        this.setName("Maiasaura");
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setDinosaurClass(MaiasauraEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x1f1f1f, 0x888888);
        this.setEggColorFemale(0x28231f, 0x958e8a);
        this.setHealth(10, 60);
        this.setStrength(5, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(50));
        this.setEyeHeight(0.56F, 2.75F);
        this.setSizeX(0.3F, 2.4F);
        this.setSizeY(0.6F, 3.0F);
        this.setStorage(45);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("cheek_teeth", "pelvis", "skull", "front_leg_bones", "hind_leg_bones", "ribcage", "shoulder", "tail_vertebrae", "neck_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(0.95F, 0.1F);
        this.setAttackBias(80);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setFlockSpeed(1.5F);
        this.setBreeding(false, 4, 6, 40, false, true);
        String[][] recipe = {
                {"tail_vertebrae", "pelvis", "ribcage","neck_vertebrae","skull"},
                {"hind_leg_bones", "", "", "shoulder", "cheek_teeth"},
                {"", "", "", "", "front_leg_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SANDY));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MESA));
        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
        this.init();
    }
}