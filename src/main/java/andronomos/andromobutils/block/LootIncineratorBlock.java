package andronomos.andromobutils.block;

import andronomos.andromobutils.AndroMobUtils;
import andronomos.andromobutils.block.entity.LootIncineratorBE;
import andronomos.andromobutils.inventory.LootIncineratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class LootIncineratorBlock extends Block implements EntityBlock {
    public static final String SCREEN_LOOT_INCINERATOR = "screen.andromobutils.loot_incinerator";

    public LootIncineratorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LootIncineratorBE(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);

            if(entity instanceof LootIncineratorBE) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public TextComponent getDisplayName() {
                        return new TextComponent(SCREEN_LOOT_INCINERATOR);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player1) {
                        return new LootIncineratorContainer(windowId, pos, inventory, player1);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
            }
        }

        return InteractionResult.SUCCESS;
    }
}
