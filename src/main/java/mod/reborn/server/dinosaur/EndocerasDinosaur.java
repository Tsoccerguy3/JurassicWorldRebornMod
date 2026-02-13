package mod.reborn.server.dinosaur;


import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.food.FoodType;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;


public class EndocerasDinosaur extends Dinosaur {
    public static final double SPEED = 0.25F;
    public EndocerasDinosaur() {
        super();
        this.setName("Endoceras");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(EndocerasEntity.class);
        this.setTimePeriod(TimePeriod.ORDOVICIAN);
        this.setEggColorMale(0xA9472C, 0xFDCEB5);
        this.setEggColorFemale(0xD08462, 0xD6997B);
        this.setHealth(2, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(1, 3);
        this.setMaximumAge(fromDays(25));
        this.setEyeHeight(0.10F, 0.5F);
        this.setSizeX(0.5F, 2.0F);
        this.setSizeY(0.2F, 0.8F);
        this.setMarineAnimal(true);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)));
        this.setBones("beak", "shell_cover");
        this.setHeadCubeName("Head");
        this.setOffset(0,0,-3);
        this.setSkeletonOffset(0,0,0);
        this.setScale(1.2F, 0.2F);
        this.setBreeding(true, 2, 10, 20, false, false);
        this.setImprintable(false);
        String[][] recipe = {
                { "shell_cover"},
                {"beak"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));

        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
    }
}