package dev.spears.forge;

import dev.spears.common.SpearMaterial;
import dev.spears.common.SpearsCommon;
import dev.spears.common.item.VanillaParitySpearItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SpearsCommon.MOD_ID)
public final class SpearsForgeMod {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpearsCommon.MOD_ID);

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.WOOD, new Item.Properties()));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.STONE, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_SPEAR = ITEMS.register("copper_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.COPPER, new Item.Properties()));
    public static final RegistryObject<Item> GOLD_SPEAR = ITEMS.register("gold_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.DIAMOND, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
        () -> new VanillaParitySpearItem(SpearMaterial.NETHERITE, new Item.Properties().fireResistant()));

    public SpearsForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
}
