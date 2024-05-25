package net.ryan.fonderciv.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.fonderciv.FonderCiv;
import net.ryan.fonderciv.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FonderCiv.MOD_ID);

    public static final RegistryObject<BlockEntityType<ElvenChestBlockEntity>> ELVEN_CHEST_BE =
            BLOCK_ENTITIES.register("elven_chest_be",
                    () -> BlockEntityType.Builder.of(ElvenChestBlockEntity::new,
                            ModBlocks.ELVEN_CHEST_BLOCK.get()).build(null));

    public static void register (IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
