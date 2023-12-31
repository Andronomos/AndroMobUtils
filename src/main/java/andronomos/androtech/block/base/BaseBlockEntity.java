package andronomos.androtech.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBlockEntity extends BlockEntity {
	protected final ItemStackHandler itemHandler = createInventoryItemHandler();
	protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	protected final ContainerData data;

	public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ContainerData data) {
		super(type, pos, state);
		this.data = data;
	}

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
		if (itemHandler != null) {
			lazyItemHandler.invalidate();
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		if (itemHandler != null) {
			tag.put("Inventory", itemHandler.serializeNBT());
		}
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (itemHandler != null) {
			itemHandler.deserializeNBT(tag.getCompound("Inventory"));
		}
	}

	protected abstract ItemStackHandler createInventoryItemHandler();
}
