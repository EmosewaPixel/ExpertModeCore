package com.EmosewaPixel.expertmodecore.items.tools;

import com.EmosewaPixel.expertmodecore.ExpertModeCore;
import com.EmosewaPixel.expertmodecore.materials.IMaterialItem;
import com.EmosewaPixel.expertmodecore.materials.Material;
import com.EmosewaPixel.expertmodecore.materials.MaterialItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class MaterialSaw extends ItemAxe implements IMaterialItem {
    private Material material;

    public MaterialSaw(Material mat) {
        super(mat.getItemTier(), 3, 3.4F - mat.getItemTier().getEfficiency() / 2 * 0.1F, new Item.Properties().defaultMaxDamage(mat.getItemTier().getMaxUses() / 3 * 2).group(ExpertModeCore.main));
        setRegistryName(mat.getName() + "_saw");
        efficiency = mat.getItemTier().getEfficiency() / 2;
        this.material = mat;
        MaterialItems.materialItems.add(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        container.setDamage(container.getDamage() + 2);
        if (container.getDamage() < container.getMaxDamage())
            return container;
        else
            return ItemStack.EMPTY;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public String getItemType() {
        return "saw";
    }
}
