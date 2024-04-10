package andronomos.androtech.block.entityrepulsor;

import andronomos.androtech.block.BaseBlockEntity;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntityRepulsorBlockEntity extends BaseBlockEntity implements MenuProvider {
	float xPos, yPos, zPos;
	float xNeg, yNeg, zNeg;

	public EntityRepulsorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.ENTITY_REPULSOR_BE.get(), pos, state, new SimpleContainerData(EntityRepulsorBlock.SLOTS));
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(EntityRepulsorBlock.SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(EntityRepulsorBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new EntityRepulsorMenu(containerId, inventory, this, this.data);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T t) {

	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		if (entity instanceof EntityRepulsorBlockEntity) {
			BlockState stateAtPos = level.getBlockState(pos);

			if (level.getGameTime() % 2 == 0 && state.getBlock() instanceof EntityRepulsorBlock) {
				if (stateAtPos.getValue(EntityRepulsorBlock.POWERED)) {
					activate();
				}
			}

			if (!level.isClientSide) {
				setAABBWithModifiers();
			}
		}

		//
		//Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
		//entity.setDeltaMovement(entity.getDeltaMovement().add(this.velocity * (direction.getStepX() * 1.5), 0, this.velocity * (direction.getStepZ() * 1.5)));
	}

	protected void activate() {
		BlockState state = getLevel().getBlockState(getBlockPos());

		if (!(state.getBlock() instanceof EntityRepulsorBlock)) {
			return;
		}

		Direction facing = state.getValue(EntityRepulsorBlock.FACING);

		List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, getAABBWithModifiers());

		for (Entity entity : list) {
			if (entity != null) {
				if (entity instanceof LivingEntity) {
					if (facing != Direction.UP && facing != Direction.DOWN) {
						entity.push(Mth.sin(facing.getOpposite().toYRot() * 3.141593F / 180.0F) * 0.5D, 0D, -Mth.cos(facing.getOpposite().toYRot() * 3.141593F / 180.0F) * 0.5D);
					} else if (facing == Direction.UP) {
						float f = 0.125F;
						Vec3 vec3d = entity.getDeltaMovement();
						entity.setDeltaMovement(vec3d.x, (double) f, vec3d.z);
						entity.push(0D, 0.25D, 0D);
						entity.fallDistance = 0;
					} else {
						entity.push(0D, -0.2D, 0D);
					}
				}
			}
		}
	}

	private void setAABBWithModifiers() {
		BlockState state = getLevel().getBlockState(getBlockPos());

		if (!(state.getBlock() instanceof EntityRepulsorBlock)) {
			return;
		}

		Direction facing = state.getValue(EntityRepulsorBlock.FACING);

		int distance;

		for (distance = 1; distance < 5 + getDistanceModifier(); distance++) {
			BlockState state2 = getLevel().getBlockState(getBlockPos().relative(facing, distance));
			if (!(state2.getBlock() instanceof AirBlock) && state2.canOcclude()) {
				break;
			}
		}

		if (facing == Direction.UP) {
			yPos = distance;
			yNeg = -1;
			xPos = getHeightModifier();
			xNeg = getHeightModifier();
			zPos = getWidthModifier();
			zNeg = getWidthModifier();
		}

		if (facing == Direction.DOWN) {
			yNeg = distance;
			yPos = -1;
			xPos = getHeightModifier();
			xNeg = getHeightModifier();
			zPos = getWidthModifier();
			zNeg = getWidthModifier();
		}

		if (facing == Direction.WEST) {
			xNeg = distance;
			xPos = -1;
			zPos = getWidthModifier();
			zNeg = getWidthModifier();
			yPos = getHeightModifier();
			yNeg = getHeightModifier();
		}

		if (facing == Direction.EAST) {
			xPos = distance;
			xNeg = -1;
			zPos = getWidthModifier();
			zNeg = getWidthModifier();
			yPos = getHeightModifier();
			yNeg = getHeightModifier();
		}

		if (facing == Direction.NORTH) {
			zNeg = distance;
			zPos = -1;
			xPos = getWidthModifier();
			xNeg = getWidthModifier();
			yPos = getHeightModifier();
			yNeg = getHeightModifier();
		}

		if (facing == Direction.SOUTH) {
			zPos = distance;
			zNeg = -1;
			xPos = getWidthModifier();
			xNeg = getWidthModifier();
			yPos = getHeightModifier();
			yNeg = getHeightModifier();
		}

		getLevel().sendBlockUpdated(getBlockPos(), state, state, 8);
	}

	public AABB getAABBWithModifiers() {
		return new AABB(getBlockPos().getX() - xNeg, getBlockPos().getY() - yNeg, getBlockPos().getZ() - zNeg, getBlockPos().getX() + 1D + xPos, getBlockPos().getY() + 1D + yPos, getBlockPos().getZ() + 1D + zPos);
	}

	private float getWidthModifier() {
		return hasWidthUpgrade() ? itemHandler.getStackInSlot(0).getCount() : 0;
	}

	private float getHeightModifier() {
		return hasHeightUpgrade() ? itemHandler.getStackInSlot(1).getCount() : 0;
	}

	private int getDistanceModifier() {
		return hasDistanceUpgrade() ? itemHandler.getStackInSlot(2).getCount() : 0;
	}

	private boolean hasWidthUpgrade() {
		//return !itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).getItem() == ItemRegistry.FAN_UPGRADE_WIDTH.get();
		return !itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).getItem() == ItemRegistry.SHARPNESS_AUGMENT.get();
	}

	private boolean hasHeightUpgrade() {
		//return !itemHandler.getStackInSlot(1).isEmpty() && itemHandler.getStackInSlot(1).getItem() == ItemRegistry.AUG.get();
		return !itemHandler.getStackInSlot(1).isEmpty() && itemHandler.getStackInSlot(1).getItem() == ItemRegistry.LOOTING_AUGMENT.get();
	}

	private boolean hasDistanceUpgrade() {
		//return !itemHandler.getStackInSlot(2).isEmpty() && itemHandler.getStackInSlot(2).getItem() == ItemRegistry.FAN_UPGRADE_SPEED.get();
		return !itemHandler.getStackInSlot(2).isEmpty() && itemHandler.getStackInSlot(2).getItem() == ItemRegistry.FIRE_AUGMENT.get();
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		xPos = tag.getFloat("xPos");
		yPos = tag.getFloat("yPos");
		zPos = tag.getFloat("zPos");
		xNeg = tag.getFloat("xNeg");
		yNeg = tag.getFloat("yNeg");
		zNeg = tag.getFloat("zNeg");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putFloat("xPos", xPos);
		tag.putFloat("yPos", yPos);
		tag.putFloat("zPos", zPos);
		tag.putFloat("xNeg", xNeg);
		tag.putFloat("yNeg", yNeg);
		tag.putFloat("zNeg", zNeg);
	}
}
