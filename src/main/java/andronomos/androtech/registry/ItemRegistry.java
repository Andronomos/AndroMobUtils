package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.*;
import andronomos.androtech.item.base.AbstractDeviceItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	public static final Item.Properties DEVICE_PROPERTIES = new Item.Properties().durability(AbstractDeviceItem.DURABILITY);
	public static final Item.Properties NANITE_TOOL_PROPERTIES = new Item.Properties().fireResistant();

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AndroTech.MODID);

	public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
	public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
	public static final RegistryObject<Item> ELITE_CHIP = register("elite_chip");

	public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword",
			() -> new FakeSword(new Item.Properties()));

	public static final RegistryObject<Item> SHARPNESS_AUGMENT = ITEMS.register("sharpness_augment",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> LOOTING_AUGMENT = ITEMS.register("looting_augment",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> FIRE_AUGMENT = ITEMS.register("fire_augment",
			() -> new Item(new Item.Properties()));

	//public static final RegistryObject<Item> SMITE_AUGMENT = ITEMS.register("smite_augment",
	//		() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> GPS_RECORDER = ITEMS.register("gps_recorder",
			() -> new GPSRecorderItem(DEVICE_PROPERTIES));

	public static final RegistryObject<Item> PORTABLE_ENTITY_VACUUM = ITEMS.register("portable_entity_vacuum",
			() -> new PortableEntityVacuumItem(DEVICE_PROPERTIES));

	public static final RegistryObject<Item> MOB_STORAGE_DEVICE = ITEMS.register("mob_storage_device",
			() -> new MobStorageDevice(DEVICE_PROPERTIES));

	public static final RegistryObject<Item> FLUID_EVAPORATOR = ITEMS.register("fluid_evaporator",
			() -> new FluidEvaporator(DEVICE_PROPERTIES));

	public static final RegistryObject<Item> REPULSOR_WIDTH_UPGRADE = ITEMS.register("repulsor_width_upgrade",
			() -> new RepulsorUpgradeItem(new Item.Properties()));

	public static final RegistryObject<Item> REPULSOR_HEIGHT_UPGRADE = ITEMS.register("repulsor_height_upgrade",
			() -> new RepulsorUpgradeItem(new Item.Properties()));

	public static final RegistryObject<Item> REPULSOR_DISTANCE_UPGRADE = ITEMS.register("repulsor_distance_upgrade",
			() -> new RepulsorUpgradeItem(new Item.Properties()));

	//public static final RegistryObject<Item> NANITE_ENHANCED_PICKAXE = ITEMS.register("nanite_enhanced_pickaxe",
	//		() -> new NaniteEnhancedPickAxe(Tiers.NETHERITE, 1, -2.8F, NANITE_TOOL_PROPERTIES));
	//
	//public static final RegistryObject<Item> NANITE_ENHANCED_AXE = ITEMS.register("nanite_enhanced_axe",
	//		() -> new NaniteEnhancedAxe(Tiers.NETHERITE, 5.0F, -3.0F, NANITE_TOOL_PROPERTIES));
	//
	//public static final RegistryObject<Item> NANITE_ENHANCED_SHOVEL = ITEMS.register("nanite_enhanced_shovel",
	//		() -> new NaniteEnhancedShovel(Tiers.NETHERITE, 5.0F, -3.0F, NANITE_TOOL_PROPERTIES));
	//
	//public static final RegistryObject<Item> NANITE_ENHANCED_SWORD = ITEMS.register("nanite_enhanced_sword",
	//		() -> new NaniteEnhancedSword(Tiers.NETHERITE, 3, -2.4F, NANITE_TOOL_PROPERTIES));

	public static final RegistryObject<Item> LIQUID_XP_BUCKET = ITEMS.register("liquid_xp_bucket",
			() -> new BucketItem(FluidRegistry.LIQUID_XP, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

	private static RegistryObject<Item> register(String name) {
		return ITEMS.register(name, () -> new Item(new Item.Properties()));
	}
}
