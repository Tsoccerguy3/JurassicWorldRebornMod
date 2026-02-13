package mod.reborn.server.dinosaur;

import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.SleepTime;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;


public class PatagotitanDinosaur extends Dinosaur {
    public static final double SPEED = 0.22F;
    public PatagotitanDinosaur() {
        super();
        this.setName("Patagotitan");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(PatagotitanEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x272a2b, 0x94c2c2);
        this.setEggColorFemale(0x272a2b, 0xa0bfb7);
        this.setHealth(10, 180);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 20);
        this.setMaximumAge(this.fromDays(85));
        this.setEyeHeight(0.4F, 3.6F);
        this.setSizeX(0.4F, 6.1F);
        this.setSizeY(0.5F, 6.6F);
        this.setStorage(54);
        this.setPaleoPadScale(5);
        this.setDiet(Diet.HERBIVORE.get());
        this.setBones("front_leg_bones", "hind_leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(1.15F, 0.1F);
        this.setAttackBias(1200.0);
        this.setMaxHerdSize(4);
        this.setBreeding(false, 4, 8, 70, true, false);
        String[][] recipe =     {{"", "", "", "", "skull"},
                {"", "", "", "neck_vertebrae","tooth"},
                {"tail_vertebrae","pelvis","ribcage","shoulder",""},
                {"","hind_leg_bones","hind_leg_bones","front_leg_bones","front_leg_bones"}};
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