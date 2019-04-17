package com.EmosewaPixel.expertmodecore.items.tools;

import com.EmosewaPixel.expertmodecore.recipes.RecipeAddition;
import net.minecraft.init.Items;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

import java.util.function.Supplier;

public enum ExpertTiers implements IItemTier {
    FLINT(0, 60, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromItems(Items.FLINT);
    }),
    BRONZE(1, 172, 4.0F, 1.0F, 5, () -> {
        return Ingredient.fromTag(RecipeAddition.INGOTS_BRONZE);
    }),
    STEEl(2, 320, 6.0F, 2.0F, 14, () -> {
        return Ingredient.fromTag(RecipeAddition.INGOTS_STEEL);
    }),
    CRYSTALLINE(3, 2030, 8.0F, 3.0F, 10, () -> {
        return Ingredient.fromTag(RecipeAddition.INGOTS_CRYSTALLINE);
    }),
    ELECTRUM(1, 96, 12.0F, 0.0F, 22, () -> {
        return Ingredient.fromTag(RecipeAddition.INGOTS_ELECTRUM);
    });

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = WOOD/GOLD)
     */
    private final int harvestLevel;
    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    private final int maxUses;
    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    private final float efficiency;
    /**
     * Damage versus entities.
     */
    private final float attackDamage;
    /**
     * Defines the natural enchantability factor of the material.
     */
    private final int enchantability;
    private final LazyLoadBase<Ingredient> repairMaterial;

    ExpertTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
