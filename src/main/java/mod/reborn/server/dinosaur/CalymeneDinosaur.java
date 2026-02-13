package mod.reborn.server.dinosaur;



import mod.reborn.server.entity.Diet;
import mod.reborn.server.entity.dinosaur.CalymeneEntity;
import mod.reborn.server.period.TimePeriod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import scala.tools.nsc.doc.base.CommentFactoryBase;

import java.util.ArrayList;

public class CalymeneDinosaur extends Dinosaur {
    public static final double SPEED = 0.15F;
    public CalymeneDinosaur() {
        super();
        this.setName("Calymene");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(CalymeneEntity.class);
        this.setTimePeriod(TimePeriod.SILURIAN);
        this.setEggColorMale(0x7C7C7A, 0x262626);
        this.setEggColorFemale(0x7C7C7B, 0x262629);
        this.setHealth(2, 5);
        this.setSpeed((SPEED), SPEED);
        this.setStrength(0.5, 1);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.02F, 0.05F);
        this.setSizeX(0.1F, 0.3F);
        this.setSizeY(0.2F, 0.3F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("cephalon", "thorax", "pygidium");
        this.setHeadCubeName("Head");
        this.setScale(0.1F, 0.05F);
        this.setBirthType(BirthType.EGG_LAYING);
        this.setAttackBias(1);
        this.setImprintable(false);
        this.setBreeding(false, 6, 16, 20, false, true); // more eggs than a vertebrate
        this.setMarineAnimal(true);
        String[][] recipe = {
                {  "cephalon" },
                {  "thorax"},
                { "pygidium" }};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<Biome> biomeList = new ArrayList<Biome>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));

        this.setSpawn(1, biomeList.toArray(new Biome[biomeList.size()]));
this.init();
    }
}