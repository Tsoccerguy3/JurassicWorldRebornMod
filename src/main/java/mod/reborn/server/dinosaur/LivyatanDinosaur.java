package mod.reborn.server.dinosaur;

import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.food.FoodType;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;

public class LivyatanDinosaur extends Dinosaur {
    public static final double SPEED = 0.45F;
    public LivyatanDinosaur(){
        super();
        this.setName("Livyatan");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(LivyatanEntity.class);
        this.setTimePeriod(TimePeriod.NEOGENE);
        this.setEggColorMale(0xE2E2E2, 0xF1F1F1);
        this.setEggColorFemale(0x2B2B2B, 0x3A3A3A);
        this.setHealth(20, 150);
        this.setSpeed((SPEED -0.15), SPEED);
        this.setStrength(20, 50);
        this.setMaximumAge(fromDays(70));
        this.setMarineAnimal(true);
        this.setEyeHeight(0.225F, 3.5F);
        this.setSizeX(1.8F, 3.6F);
        this.setSizeY(1.0F, 3.6F);
        this.setStorage(27);
        this.setDiet(Diet.PCARNIVORE.get());
        this.setBones("flipper_bones", "ribcage", "skull", "tail_vertebrae", "teeth");
        this.setHeadCubeName("Main head");
        this.setScale(1.15F, 0.12F);
        this.setMarineAnimal(true);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.setAttackBias(1200);
        this.setAttackSpeed(2);
        this.setStorage(12);
        this.setPaleoPadScale(3);
        this.setImprintable(false);
        this.setMammal(true);
        this.setBreeding(true, 1, 3, 80, false, true);
        String[][] recipe = {
                { "skull", "ribcage", "tail_vertebrae" },
                { "flipper_bones", "teeth", "" }
        };
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));

        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
this.init();
    }
}