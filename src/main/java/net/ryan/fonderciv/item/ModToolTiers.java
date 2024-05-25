package net.ryan.fonderciv.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.ryan.fonderciv.FonderCiv;
import net.ryan.fonderciv.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier MYTHRIL = TierSortingRegistry.registerTier(
            new ForgeTier(3,1850, 8.0f,3f, 14,
                    ModTags.Blocks.NEEDS_MYTHRIL_TOOL, () -> Ingredient.of(ModItems.MYTHRIL.get())),
            new ResourceLocation(FonderCiv.MOD_ID, "mythril"), List.of(Tiers.DIAMOND), List.of());
}
