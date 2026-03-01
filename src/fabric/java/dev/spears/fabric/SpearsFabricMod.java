package dev.spears.fabric;

import dev.spears.common.SpearMaterial;
import dev.spears.common.SpearsCommon;
import dev.spears.common.item.VanillaParitySpearItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class SpearsFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        register("wooden_spear", new VanillaParitySpearItem(SpearMaterial.WOOD, new Item.Properties()));
        register("stone_spear", new VanillaParitySpearItem(SpearMaterial.STONE, new Item.Properties()));
        register("copper_spear", new VanillaParitySpearItem(SpearMaterial.COPPER, new Item.Properties()));
        register("gold_spear", new VanillaParitySpearItem(SpearMaterial.GOLD, new Item.Properties()));
        register("diamond_spear", new VanillaParitySpearItem(SpearMaterial.DIAMOND, new Item.Properties().fireResistant()));
        register("netherite_spear", new VanillaParitySpearItem(SpearMaterial.NETHERITE, new Item.Properties().fireResistant()));
    }

    private static void register(String path, Item item) {
        net.minecraft.core.Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SpearsCommon.MOD_ID, path), item);
    }
}
