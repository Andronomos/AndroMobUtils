package andronomos.andromobutils.network;

import andronomos.andromobutils.AndroMobUtils;
import andronomos.andromobutils.network.packet.SyncSpawnerMagicLeadDrop;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MobToolsPacketHandler {
	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(AndroMobUtils.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void register() {
		int messageId = 0;

		INSTANCE.registerMessage(messageId++,
				SyncSpawnerMagicLeadDrop.class,
				SyncSpawnerMagicLeadDrop::encode,
				SyncSpawnerMagicLeadDrop::decode,
				SyncSpawnerMagicLeadDrop::handle);
	}

}
