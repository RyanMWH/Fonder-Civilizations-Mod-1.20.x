package net.ryan.fonderciv.block.custom;

import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.ryan.fonderciv.block.entity.ElvenChestBlockEntity;
import net.ryan.fonderciv.block.entity.ModBlockEntities;
import net.ryan.fonderciv.item.custom.TreasureChestKeys;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


public class ElvenChestBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 14, 10, 14);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ElvenChestBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pWorld, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pWorld.isClientSide) {
            BlockEntity entity = pWorld.getBlockEntity(pPos);
            ItemStack heldItem = pPlayer.getMainHandItem();
            boolean isLocked = true;
            if (heldItem.getItem() instanceof TreasureChestKeys || !isLocked) {
                isLocked = false;
                if (entity instanceof ElvenChestBlockEntity) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (ElvenChestBlockEntity) entity, pPos);
                    return InteractionResult.CONSUME;

                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            } else {
                pPlayer.sendSystemMessage(Component.literal("You do not have the right key to unlock this."));
            }
        }

        return InteractionResult.sidedSuccess(pWorld.isClientSide());

    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pType, ModBlockEntities.ELVEN_CHEST_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }


    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return false;
    }



    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ElvenChestBlockEntity(pPos, pState);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pWorld, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pWorld, BlockPos pPos){
        BlockEntity blockEntity = pWorld.getBlockEntity(pPos);
        return blockEntity instanceof MenuProvider ? (MenuProvider)blockEntity: null;
    }


    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pWorld, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pWorld, BlockPos pPos, BlockState pNewState, boolean isMoving){
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pWorld.getBlockEntity(pPos);
            if (blockEntity instanceof ElvenChestBlockEntity){
                ((ElvenChestBlockEntity) blockEntity).drops();
            }
            super.onRemove(pState, pWorld, pPos,pNewState, isMoving);
        }
    }

    @Nullable
    public static Container getContainer(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity) {
            return (Container) blockEntity;
        }
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public void tick(BlockState pState, ServerLevel pWorld, BlockPos pPos, Random random){
        BlockEntity blockEntity = pWorld.getBlockEntity(pPos);

        if(blockEntity instanceof ElvenChestBlockEntity){
            ((ElvenChestBlockEntity) blockEntity).recheckOpen();
        }

    }
}



























