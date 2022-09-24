package io.github.sugarmgp.eos.handler;

import io.github.sugarmgp.eos.EOS;
import io.github.sugarmgp.eos.item.ItemCuckooIngot;
import io.github.sugarmgp.eos.item.ItemFunnyApple;
import io.github.sugarmgp.eos.item.ItemFunnyIngot;
import io.github.sugarmgp.eos.item.armor.ItemFunnyBoots;
import io.github.sugarmgp.eos.item.armor.ItemFunnyChestplate;
import io.github.sugarmgp.eos.item.armor.ItemFunnyHelmet;
import io.github.sugarmgp.eos.item.armor.ItemFunnyLeggings;
import io.github.sugarmgp.eos.item.tool.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EOS.MODID);

    public static final RegistryObject<Item> itemCuckooIngot = ITEMS.register("cuckoo_ingot", ItemCuckooIngot::new);
    public static final RegistryObject<Item> itemFunnyApple = ITEMS.register("funny_apple", ItemFunnyApple::new);
    public static final RegistryObject<Item> itemFunnyIngot = ITEMS.register("funny_ingot", ItemFunnyIngot::new);

    public static final RegistryObject<Item> itemCuckooAxe = ITEMS.register("cuckoo_axe", ItemCuckooAxe::new);
    public static final RegistryObject<Item> itemCuckooHoe = ITEMS.register("cuckoo_hoe", ItemCuckooHoe::new);
    public static final RegistryObject<Item> itemCuckooPickaxe = ITEMS.register("cuckoo_pickaxe", ItemCuckooPickaxe::new);
    public static final RegistryObject<Item> itemCuckooShovel = ITEMS.register("cuckoo_shovel", ItemCuckooShovel::new);
    public static final RegistryObject<Item> itemCuckooSword = ITEMS.register("cuckoo_sword", ItemCuckooSword::new);

    public static final RegistryObject<Item> itemFunnyBoots = ITEMS.register("funny_boots", ItemFunnyBoots::new);
    public static final RegistryObject<Item> itemFunnyChestplate = ITEMS.register("funny_chestplate", ItemFunnyChestplate::new);
    public static final RegistryObject<Item> itemFunnyHelmet = ITEMS.register("funny_helmet", ItemFunnyHelmet::new);
    public static final RegistryObject<Item> itemFunnyLeggings = ITEMS.register("funny_leggings", ItemFunnyLeggings::new);

    public static final RegistryObject<SpawnEggItem> itemFriendSpawnEgg = ITEMS.register("friend_spawn_egg", () -> new ForgeSpawnEggItem(EntityHandler.entityFriend, 0xB3FFFF, 0x4D94FF, new Item.Properties().group(EOS.ITEMGROUP)));

    public static RegistryObject<Item> itemFunnyOre = ITEMS.register("funny_ore", () -> {
        return new BlockItem(BlockHandler.blockFunnyOre.get(), new Item.Properties().group(EOS.ITEMGROUP));
    });
    public static RegistryObject<Item> itemCuckooOre = ITEMS.register("cuckoo_ore", () -> {
        return new BlockItem(BlockHandler.blockCuckooOre.get(), new Item.Properties().group(EOS.ITEMGROUP));
    });
    public static RegistryObject<Item> itemFunnyBlock = ITEMS.register("funny_block", () -> {
        return new BlockItem(BlockHandler.blockFunnyBlock.get(), new Item.Properties().group(EOS.ITEMGROUP));
    });
    public static RegistryObject<Item> itemCuckooBlock = ITEMS.register("cuckoo_block", () -> {
        return new BlockItem(BlockHandler.blockCuckooBlock.get(), new Item.Properties().group(EOS.ITEMGROUP));
    });
}
