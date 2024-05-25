package net.ryan.fonderciv.item;

import com.ibm.icu.impl.locale.XCldrStub;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.fonderciv.FonderCiv;
import net.ryan.fonderciv.item.custom.TreasureChestKeys;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FonderCiv.MOD_ID);

    public static final RegistryObject<Item> MYTHRIL = ITEMS.register("mythril",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_MYTHRIL = ITEMS.register("raw_mythril",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> MYTHRIL_SWORD = ITEMS.register("mythril_sword",
            () -> new SwordItem(ModToolTiers.MYTHRIL, 3, -2, new Item.Properties()));
    public static final RegistryObject<Item> MYTHRIL_PICKAXE = ITEMS.register("mythril_pickaxe",
            () -> new PickaxeItem(ModToolTiers.MYTHRIL, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> MYTHRIL_AXE = ITEMS.register("mythril_axe",
            () -> new AxeItem(ModToolTiers.MYTHRIL, 5.5f, -2.6F, new Item.Properties()));
    public static final RegistryObject<Item> MYTHRIL_SHOVEL = ITEMS.register("mythril_shovel",
            () -> new ShovelItem(ModToolTiers.MYTHRIL, 1.5F, -3, new Item.Properties()));
    public static final RegistryObject<Item> MYTHRIL_HOE = ITEMS.register("mythril_hoe",
            () -> new HoeItem(ModToolTiers.MYTHRIL, -3, 0, new Item.Properties()));


    public static final RegistryObject<Item> ELVEN_CHEST_KEY = ITEMS.register("elven_chest_key",
            () -> new TreasureChestKeys(new ResourceLocation(FonderCiv.MOD_ID, "elven"), new Item.Properties().stacksTo(1).durability(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
