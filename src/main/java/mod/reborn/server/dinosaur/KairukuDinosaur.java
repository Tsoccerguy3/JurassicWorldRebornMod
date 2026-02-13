package mod.reborn.server.dinosaur;

import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.food.FoodType;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;

public class KairukuDinosaur extends Dinosaur {
    public static final double SPEED = 0.18F;

    public KairukuDinosaur() {
        super();

        this.setName("Kairuku");
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setDinosaurClass(KairukuEntity.class);
        this.setTimePeriod(TimePeriod.PALEOGENE);
        this.setEggColorMale(0x252522, 0xd8c7b6);
        this.setEggColorFemale(0x1f1f1d, 0xf0eeeb);
        this.setHealth(6, 20);
        this.setSpeed((SPEED - 0.05), SPEED);
        this.setStorage(27);
        this.setMarineAnimal(true);
        this.setStrength(1, 6);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.30F, 0.9F);
        this.setSizeX(0.20F, 0.9F);
        this.setSizeY(0.40F, 1.1F);
        this.setDiet(Diet.PISCIVORE.get());
        this.setBones("skull", "arm_bones", "leg_bones", "neck_vertebrae",
                "ribcage", "shoulder", "tail_vertebrae", "foot_bones");
        this.setHeadCubeName("Head");
        this.setScale(0.5F, 0.1F);
        this.setAttackBias(10);
        this.setAttackSpeed(0.8F);
        this.setDefendOwner(false);
        this.setBirthType(BirthType.EGG_LAYING);
        this.setImprintable(true);
        this.setBreeding(false, 1, 2, 24, false, true);
        String[][] recipe = {
                {"skull", "neck_vertebrae", ""},
                {"arm_bones","ribcage","shoulder"},
                {"","leg_bones","tail_vertebrae"},
                {"","foot_bones", ""}
        };
        this.setRecipe(recipe);
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));

        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
        this.enableSkeleton();
        this.init();
    }
}
