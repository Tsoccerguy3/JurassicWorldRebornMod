package mod.reborn.server.dinosaur;

import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.dinosaur.*;
import mod.reborn.server.food.FoodType;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;


public class DeinosuchusDinosaur extends Dinosaur {

    public static final double SPEED = 0.28F;

    public DeinosuchusDinosaur() {
        super();

        this.setName("Deinosuchus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(DeinosuchusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x272520, 0xbfa07a);
        this.setEggColorFemale(0x3c3426, 0xc5a885);
        this.setHealth(20, 80);
        this.setSpeed((SPEED - 0.05), SPEED);
        this.setStorage(27);
        this.setPaleoPadScale(2);
        this.setStrength(4, 22);
        this.setMaximumAge(fromDays(60));
        this.setEyeHeight(0.20F, 0.9F);
        this.setSizeX(0.30F, 2.5F);
        this.setSizeY(0.25F, 2.0F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("skull", "teeth", "femur", "leg_bones", "neck_vertebrae",
                "ribcage", "shoulder_blade", "tail_vertebrae", "arm_bones");
        this.setHeadCubeName("Head");
        this.setScale(0.8F, 0.1F);
        this.setAttackBias(90);
        this.setMarineAnimal(true);
        this.setBreeding(false, 2, 6, 20, false, true);
        String[][] recipe = {
                {"", "", "neck_vertebrae", "skull","teeth"},
                {"tail_vertebrae", "femur", "ribcage", "shoulder_blade",""},
                {"leg_bones", "", "", "arm_bones",""}
        };
        this.setRecipe(recipe);
        this.enableSkeleton();
        this.init();
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP));

        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
    }
}
