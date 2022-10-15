package io.github.sugarmgp.eos.worldgen;

import io.github.sugarmgp.eos.handler.EntityHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class ModEntityGeneration {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        RegistryKey<Biome>[] biomes = new RegistryKey[]{
                Biomes.PLAINS,
                Biomes.SUNFLOWER_PLAINS,
                Biomes.SNOWY_TUNDRA,
                Biomes.FLOWER_FOREST,
                Biomes.BIRCH_FOREST,
                Biomes.ICE_SPIKES,
                Biomes.TALL_BIRCH_FOREST
        };
        addWithList(event, EntityHandler.entityFriend.get(), 12, 1, 4, biomes);
    }

    @SafeVarargs
    private static void addWithList(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        if (Arrays.stream(biomes).map(RegistryKey::getLocation).map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()))) {
            addEntityToBiome(event, type, weight, minCount, maxCount);
        }
    }

    //在群系生成该生物
    private static void addEntityToBiome(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount) {
        List<MobSpawnInfo.Spawners> base = event.getSpawns().getSpawner(type.getClassification());
        base.add(new MobSpawnInfo.Spawners(type, weight, minCount, maxCount));
    }
}
