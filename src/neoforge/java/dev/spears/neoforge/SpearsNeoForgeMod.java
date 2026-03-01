package dev.spears.neoforge;

import dev.spears.common.SpearMaterial;
import dev.spears.common.SpearsCommon;
import dev.spears.common.item.VanillaParitySpearItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(SpearsCommon.MOD_ID)
public final class SpearsNeoForgeMod {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpearsCommon.MOD_ID);

    public static final DeferredHolder<Item, Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.WOOD, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STONE_SPEAR = ITEMS.register("stone_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.STONE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_SPEAR = ITEMS.register("copper_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.COPPER, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GOLD_SPEAR = ITEMS.register("gold_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.GOLD, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.DIAMOND, new Item.Properties().fireResistant()));
    public static final DeferredHolder<Item, Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.NETHERITE, new Item.Properties().fireResistant()));

    public SpearsNeoForgeMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
    }
}
