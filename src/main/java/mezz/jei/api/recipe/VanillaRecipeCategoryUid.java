package mezz.jei.api.recipe;

import java.util.List;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.brewing.BrewingOreRecipe;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * List of built-in recipe category UIDs, so that plugins with their own recipe handlers can use them.
 */
public class VanillaRecipeCategoryUid {
	private VanillaRecipeCategoryUid() {

	}

	/**
	 * The crafting recipe category.
	 *
	 * Automatically includes all {@link ShapedRecipes}, {@link ShapelessRecipes}, {@link ShapedOreRecipe}, and {@link ShapelessOreRecipe}.
	 *
	 * To add your recipe wrapper to this category, it must implement either {@link ICraftingRecipeWrapper} or {@link IShapedCraftingRecipeWrapper}.
	 */
	public static final String CRAFTING = "minecraft.crafting";

	/**
	 * The smelting recipe category.
	 *
	 * Automatically includes everything from {@link FurnaceRecipes#getSmeltingList()}.
	 */
	public static final String SMELTING = "minecraft.smelting";

	/**
	 * The fuel recipe category.
	 *
	 * Automatically includes everything that returns a value from {@link TileEntityFurnace#getItemBurnTime(ItemStack)}.
	 */
	public static final String FUEL = "minecraft.fuel";

	/**
	 * The brewing recipe category.
	 *
	 * Automatically tries to generate all potion variations from the basic ingredients, determined by {@link PotionHelper#isReagent(ItemStack)}.
	 * You can get the list of known potion reagents from {@link IItemRegistry#getPotionIngredients()}.
	 *
	 * Also automatically adds modded potions from {@link BrewingRecipeRegistry#getRecipes()}.
	 * JEI can only understand modded potion recipes that are {@link BrewingRecipe} or {@link BrewingOreRecipe}.
	 */
	public static final String BREWING = "minecraft.brewing";

	/**
	 * The JEI description recipe category.
	 *
	 * This is a built-in category, you can add new recipes with
	 * {@link IModRegistry#addDescription(ItemStack, String...)} or {@link IModRegistry#addDescription(List, String...)}
	 */
	public static final String DESCRIPTION = "jei.description";
}
