package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.block.base.BaseMenu;
import andronomos.androtech.inventory.RestrictedSlotHandler;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class DamagePadMenu extends BaseMenu {
	public DamagePadMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public DamagePadMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.DAMAGE_PAD_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof DamagePadBlockEntity damagePadBlockEntity) {
			damagePadBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
				addSlot(new RestrictedSlotHandler(iItemHandler, 0, 30, 30, ItemRegistry.SHARPNESS_AUGMENT.get().getDefaultInstance(), 10));
				addSlot(new RestrictedSlotHandler(iItemHandler, 1, 55, 30, ItemRegistry.LOOTING_AUGMENT.get().getDefaultInstance(), 10 ));
				addSlot(new RestrictedSlotHandler(iItemHandler, 2, 80, 30, ItemRegistry.FIRE_AUGMENT.get().getDefaultInstance(), 10 ));
				addSlot(new RestrictedSlotHandler(iItemHandler, 3, 105, 30, ItemRegistry.SMITE_AUGMENT.get().getDefaultInstance(), 10 ));
			});
		}
		setSlotIndexes(DamagePadBlock.PAD_SLOTS);
		addDataSlots(data);
	}

	//@Override
	//public ItemStack quickMoveStack(Player player, int slotIndex) {
	//	Slot sourceSlot = slots.get(slotIndex);
	//	if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
	//	ItemStack sourceStack = sourceSlot.getItem();
	//	ItemStack sourceStackCopy = sourceStack.copy();
	//
	//	//if(slotIndex >= playerInventoryFirstSlot && sourceStack.getItem() instanceof SwordItem) {
	//	//	if (!this.moveItemStackTo(sourceStack, blockEntityFirstSlot, blockEntityLastSlot, false)) {
	//	//		return ItemStack.EMPTY;
	//	//	}
	//	//} else {
	//	//	if(!moveItemStackTo(sourceStack, playerInventoryFirstSlot, playerInventoryLastSlot, false)) {
	//	//		return ItemStack.EMPTY;
	//	//	}
	//	//}
	//
	//	// If stack size == 0 (the entire stack was moved) set slot contents to null
	//	if (sourceStack.getCount() == 0) {
	//		sourceSlot.set(ItemStack.EMPTY);
	//	} else {
	//		sourceSlot.setChanged();
	//	}
	//	sourceSlot.onTake(player, sourceStack);
	//	return sourceStackCopy;
	//}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, blockEntity.getBlockPos()), player, BlockRegistry.DAMAGE_PAD.get());
	}
}
