package andronomos.androtech.event;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.entityrepulsor.EntityRepulsorBlockEntityRenderer;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AndroTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	@SubscribeEvent
	public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(BlockEntityRegistry.ENTITY_REPULSOR_BE.get(), EntityRepulsorBlockEntityRenderer::new);
	}
}
