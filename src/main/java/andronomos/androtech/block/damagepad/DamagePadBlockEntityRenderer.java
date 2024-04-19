package andronomos.androtech.block.damagepad;

import andronomos.androtech.block.entityrepulsor.EntityRepulsorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class DamagePadBlockEntityRenderer implements BlockEntityRenderer<DamagePadBlockEntity> {
	public DamagePadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(@NotNull DamagePadBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (entity == null || !entity.hasLevel()) {
			return;
		}

		if (!entity.showRenderBox) {
			return;
		}

		VertexConsumer ivertexbuilder = bufferSource.getBuffer(RenderType.lines());
		poseStack.pushPose();
		poseStack.translate(-0.0005D, -0.0005D, -0.0005D);
		poseStack.scale(0.999F, 0.999F, 0.999F);
		LevelRenderer.renderLineBox(poseStack, ivertexbuilder, entity.getAABBForRender(), 0F, 0F, 1F, 1F);
		poseStack.popPose();
	}
}
