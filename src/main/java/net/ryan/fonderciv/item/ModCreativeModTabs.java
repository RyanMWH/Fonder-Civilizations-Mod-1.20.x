package net.ryan.fonderciv.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.fonderciv.FonderCiv;
import net.ryan.fonderciv.block.ModBlocks;
import net.ryan.fonderciv.block.entity.ModBlockEntities;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FonderCiv.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FONDERVIC_TAB = CREATIVE_MODE_TABS.register("fonderciv_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_MYTHRIL.get()))
                    .title(Component.translatable("creativetab.fonderciv_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MYTHRIL.get());
                        pOutput.accept(ModItems.RAW_MYTHRIL.get());

                        pOutput.accept(ModBlocks.MYTHRIL_BLOCK.get());
                        pOutput.accept(ModBlocks.MYTHRIL_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_MYTHRIL_ORE.get());

                        pOutput.accept(ModItems.MYTHRIL_SWORD.get());
                        pOutput.accept(ModItems.MYTHRIL_PICKAXE.get());
                        pOutput.accept(ModItems.MYTHRIL_AXE.get());
                        pOutput.accept(ModItems.MYTHRIL_SHOVEL.get());
                        pOutput.accept(ModItems.MYTHRIL_HOE.get());

                        pOutput.accept(ModBlocks.ELVEN_CHEST_BLOCK.get());

                    })

                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
