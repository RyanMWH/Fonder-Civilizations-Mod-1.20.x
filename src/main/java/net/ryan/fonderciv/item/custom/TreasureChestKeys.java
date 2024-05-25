package net.ryan.fonderciv.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.ryan.fonderciv.util.ModTags;

public class TreasureChestKeys extends Item {
    private final ResourceLocation TreasureChestType;

    @Override
    public InteractionResult useOn (UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            BlockPos pPos = pContext.getClickedPos();
            BlockState pState = pContext.getLevel().getBlockState(pPos);
            Player pPlayer = pContext.getPlayer();
            if(isTreasureChest(pState)){
                pContext.getItemInHand().hurtAndBreak(1, pPlayer,
                        player -> player.broadcastBreakEvent(player.getUsedItemHand()));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public TreasureChestKeys(ResourceLocation TreasureChestType, Properties pProperties) {
        super(pProperties);
        this.TreasureChestType = TreasureChestType;
    }

    public ResourceLocation getTreasureChestType() {
        return this.TreasureChestType;
    }
    private boolean isTreasureChest (BlockState state) {
        return state.is(ModTags.Blocks.IS_TREASURE_CHEST);
    }
}
