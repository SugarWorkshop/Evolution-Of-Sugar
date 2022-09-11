package io.github.sugarmgp.eos.worldgen;

import io.github.sugarmgp.eos.handler.BlockHandler;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModOreGeneration {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.THEEND) || event.getCategory().equals(Biome.Category.NETHER)) {
            return;
        }
        generateOverWorldOre(event.getGeneration(), BlockHandler.blockCuckooOre.get().getDefaultState(), 9, 48, 10);
        generateOverWorldOre(event.getGeneration(), BlockHandler.blockFunnyOre.get().getDefaultState(), 8, 24, 1);
    }

    private static void generateOverWorldOre(BiomeGenerationSettingsBuilder settings, BlockState state, int veinSize, int range, int spread) {
        settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, state, veinSize)).range(range).square().func_242731_b(spread));
    }
}