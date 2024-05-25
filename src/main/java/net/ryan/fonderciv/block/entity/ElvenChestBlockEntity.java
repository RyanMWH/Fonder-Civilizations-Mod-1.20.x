package net.ryan.fonderciv.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.ryan.fonderciv.screen.ElvenChestMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ElvenChestBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {
    protected ContainerData data;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private NonNullList<ItemStack> items;
    private final ChestLidController chestLidController = new ChestLidController();
    public int getContainerSize(){
        return 27;
    }
    private final ItemStackHandler itemHandler = new ItemStackHandler(27){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ElvenChestBlockEntity(BlockPos pPos, BlockState pState) {
        super(ModBlockEntities.ELVEN_CHEST_BE.get(), pPos, pState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return 0;
            }

            @Override
            public void set(int i, int i1) {

            }

            @Override
            public int getCount() {
                return 27;
            }
        };

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = NonNullList.<ItemStack>withSize(this.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {
            if (i < this.items.size()) {
                this.getItems().set(i, itemsIn.get(i));
            }
        }
    }

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
            ElvenChestBlockEntity.playSound(pLevel, pPos, pState, SoundEvents.CHEST_OPEN);
        }

        protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
            ElvenChestBlockEntity.playSound(pLevel, pPos, pState, SoundEvents.CHEST_CLOSE);
        }


        @Override
        protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int previousCount, int newCount) {
            ElvenChestBlockEntity.this.signalOpenCount(pLevel, pPos, pState, previousCount, newCount);
        }

        protected boolean isOwnContainer(Player pPlayer) {
            if (!(pPlayer.containerMenu instanceof ElvenChestMenu)) {
                return false;
            } else {
                Container container = ((ElvenChestMenu) pPlayer.containerMenu).getContainer();
                return container instanceof ElvenChestBlockEntity;
            }
        }
    };

    protected void signalOpenCount(Level pLevel, BlockPos pPos, BlockState pState, int previousCount, int newCount) {
        Block block = pState.getBlock();
        pLevel.blockEvent(pPos, block, 1, newCount);
    }

    static void playSound(Level pLevel, BlockPos pPos, BlockState pState, SoundEvent pSoundEvent){
        double d0 = (double) pPos.getX() + 0.5D;
        double d1 = (double) pPos.getY() + 0.5D;
        double d2 = (double) pPos.getZ() + 0.5D;

        pLevel.playSound((Player) null, d0, d1, d2, pSoundEvent, SoundSource.BLOCKS, 0.5f, pLevel.random.nextFloat() * 0.1F + 0.9F);
    }


    //Might be wrong come back too **
    public Component getDefaultName() {
        return Component.translatable ("Elven Treasure Chest");
    }

    @Override
    public void saveAdditional(CompoundTag pTag){

        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag){
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));

    }

    public AbstractContainerMenu createMenu(int pContainerID, Inventory pPlayerInventory, Player pPlayer){
        return new ElvenChestMenu(pContainerID, pPlayerInventory, this, this.data);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    //Forge Required Methods
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }


    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    public static void lidAnimateTick(Level level, BlockPos blockPos, BlockState blockState, ElvenChestBlockEntity chestBlockEntity) {
        chestBlockEntity.chestLidController.tickLid();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        setChanged(pLevel, pPos, pState);

    }


}
