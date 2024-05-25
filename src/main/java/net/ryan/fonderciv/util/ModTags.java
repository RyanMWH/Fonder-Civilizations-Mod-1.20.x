package net.ryan.fonderciv.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.ryan.fonderciv.FonderCiv;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> MYTHRIL_ORE_TAG = tag("mythril_ore_tag");
        public static final TagKey<Block> NEEDS_MYTHRIL_TOOL = tag("needs_mythril_tool");
        public static final TagKey<Block> IS_TREASURE_CHEST = tag("is_treasure_chest");


        private static TagKey<Block> tag (String name){
            return BlockTags.create(new ResourceLocation(FonderCiv.MOD_ID, name));
        }
    }


    public static class Items{



        private static TagKey<Item> tag (String name){
            return ItemTags.create(new ResourceLocation(FonderCiv.MOD_ID, name));
        }
    }
}
