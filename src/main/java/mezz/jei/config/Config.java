package mezz.jei.config;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import mezz.jei.util.StackUtil;

public class Config {
	public static Configuration configFile;

	public static final String categoryMode = Configuration.CATEGORY_GENERAL + ".mode";
	public static final String categoryInterface = Configuration.CATEGORY_GENERAL + ".interface";
	public static final String categoryAdvanced = Configuration.CATEGORY_GENERAL + ".zz_advanced";

	public static boolean cheatItemsEnabled = false;
	public static boolean editModeEnabled = false;
	public static boolean tooltipModNameEnabled = true;
	public static Set<String> nbtKeyIgnoreList = new HashSet<>();
	public static Set<String> itemBlacklist = new HashSet<>();

	public static final String[] defaultItemBlacklist = new String[]{};
	public static final String[] defaultNbtKeyIgnoreList = new String[]{"BlockEntityTag", "CanPlaceOn"};

	public static void preInit(@Nonnull FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile());

		syncConfig();
	}

	public static void syncConfig() {
		String cheatItemsEnabledDescription = StatCollector.translateToLocal("config.jei.cheatItemsEnabled.description");
		cheatItemsEnabled = configFile.getBoolean("cheatItemsEnabled", categoryMode, cheatItemsEnabled, cheatItemsEnabledDescription, "config.jei.cheatItemsEnabled");

		String editModeEnabledDescription = StatCollector.translateToLocal("config.jei.editEnabled.description");
		editModeEnabled = configFile.getBoolean("editEnabled", categoryMode, editModeEnabled, editModeEnabledDescription, "config.jei.editEnabled");

		String tooltipModNameEnabledDescription = StatCollector.translateToLocal("config.jei.tooltipModName.description");
		tooltipModNameEnabled = configFile.getBoolean("tooltipModName", categoryInterface, tooltipModNameEnabled, tooltipModNameEnabledDescription, "config.jei.tooltipModName");

		String nbtKeyIgnoreListDescription = StatCollector.translateToLocal("config.jei.nbtKeyIgnoreList.description");
		String[] nbtKeyIgnoreListArray = configFile.getStringList("nbtKeyIgnoreList", categoryAdvanced, defaultNbtKeyIgnoreList, nbtKeyIgnoreListDescription, null, "config.jei.nbtKeyIgnoreList");
		nbtKeyIgnoreList.clear();
		Collections.addAll(nbtKeyIgnoreList, nbtKeyIgnoreListArray);

		String itemBlacklistDescription = StatCollector.translateToLocal("config.jei.itemBlacklist.description");
		String[] itemBlacklistArray = configFile.getStringList("itemBlacklist", categoryAdvanced, defaultItemBlacklist, itemBlacklistDescription, null, "config.jei.itemBlacklist");
		itemBlacklist.clear();
		Collections.addAll(itemBlacklist, itemBlacklistArray);

		if (configFile.hasChanged()) {
			configFile.save();
		}
	}

	private static void updateBlacklist() {
		Property property = configFile.get(categoryAdvanced, "itemBlacklist", defaultItemBlacklist);

		String[] currentBlacklist = itemBlacklist.toArray(new String[itemBlacklist.size()]);
		property.set(currentBlacklist);

		if (configFile.hasChanged()) {
			configFile.save();
		}
	}

	public static void addItemToConfigBlacklist(ItemStack itemStack, boolean wildcard) {
		if (itemStack == null) {
			return;
		}
		String uid = StackUtil.getUniqueIdentifierForStack(itemStack, wildcard);
		if (itemBlacklist.add(uid)) {
			updateBlacklist();
		}
	}

	public static void removeItemFromConfigBlacklist(ItemStack itemStack, boolean wildcard) {
		if (itemStack == null) {
			return;
		}
		String uid = StackUtil.getUniqueIdentifierForStack(itemStack, wildcard);
		if (itemBlacklist.remove(uid)) {
			updateBlacklist();
		}
	}

	public static boolean isItemOnConfigBlacklist(ItemStack itemStack, boolean wildcard) {
		String uid = StackUtil.getUniqueIdentifierForStack(itemStack, wildcard);
		return itemBlacklist.contains(uid);
	}
}
