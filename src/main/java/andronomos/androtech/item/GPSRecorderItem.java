package andronomos.androtech.item;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.AbstractDeviceItem;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.util.ChatHelper;
import andronomos.androtech.util.ItemStackHelper;
import andronomos.androtech.util.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GPSRecorderItem extends AbstractDeviceItem {
	public static final String TOOLTIP_GPS_MODULE = "tooltip.androtech.gps_module_location";
	public static final String TOOLTIP_GPS_MODULE_COORDS = "tooltip.androtech.gps_module_coords";
	public static final String GPS_MODULE_SAVED = "item.androtech.gps_module.saved";

	public GPSRecorderItem(Properties properties) {
		super(properties, AndroTechConfig.GPS_RECORDER_TAKE_DAMAGE.get());
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return ItemStackHelper.getBlockPos(stack) == null ? 64 : 1;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.GPS_RECORDER_DURABILITY.get();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		BlockPos pos = ItemStackHelper.getBlockPos(stack);
		if(pos != null) {
			tooltip.add(Component.translatable(TOOLTIP_GPS_MODULE).withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.translatable(GPS_MODULE_SAVED, ChatHelper.blockPosToString(pos)).withStyle(ChatFormatting.BLUE));
		}
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		recordPos(hand, pos, player);
		player.swing(hand);
		ChatHelper.sendStatusMessage(player, Component.translatable(GPS_MODULE_SAVED, ChatHelper.blockPosToString(pos)));
		return InteractionResult.SUCCESS;
	}

	private void recordPos(InteractionHand hand, BlockPos pos, Player player) {
		ItemStack held = player.getItemInHand(hand);

		if(held.getCount() == 1) {
			setBlockPos(held, pos);
			if(hasDurability) doDamage(held, player, 1, AndroTechConfig.GPS_RECORDER_CAN_BREAK.get());
		} else {
			ItemStack drop = new ItemStack(ItemRegistry.GPS_RECORDER.get());
			setBlockPos(drop, pos);
			if(hasDurability) doDamage(drop, player, 1, AndroTechConfig.GPS_RECORDER_CAN_BREAK.get());
			if(!player.addItem(drop)) ItemStackHelper.drop(player.level(), player.blockPosition(), drop);
		}
	}

	private void setBlockPos(ItemStack stack, BlockPos pos) {
		if (pos == null || stack.isEmpty()) {
			return;
		}
		NBTHelper.setIntVal(stack, "xpos", pos.getX());
		NBTHelper.setIntVal(stack, "ypos", pos.getY());
		NBTHelper.setIntVal(stack, "zpos", pos.getZ());
	}
}
