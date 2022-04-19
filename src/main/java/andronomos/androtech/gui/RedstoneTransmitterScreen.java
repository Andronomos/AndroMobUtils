package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.RedstoneTransmitterContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RedstoneTransmitterScreen extends BaseScreen<RedstoneTransmitterContainer> {
    public RedstoneTransmitterScreen(RedstoneTransmitterContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int i, int j) {
        this.drawName(poseStack, title.getString());
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        this.drawBackground(poseStack, INVENTORY);

        //draw the redstone receiver card slots
        for (int slotCounter = 0; slotCounter < 9; slotCounter++) {
            this.drawSlot(poseStack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * slotCounter, 26, SLOT_CARD, Const.SCREEN_SLOT_SIZE);
        }
    }

}
