package com.EmosewaPixel.expertmodecore.items.tools;

import com.EmosewaPixel.expertmodecore.ExpertModeCore;
import com.EmosewaPixel.expertmodecore.materials.MaterialAddition;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialSaw extends ItemAxe implements IMaterialItem {
    private Material material;

    public MaterialSaw(Material mat) {
        super(mat.getItemTier(), 3, 3.4F - mat.getItemTier().getEfficiency() / 2 * 0.1F, new Item.Properties().defaultMaxDamage(mat.getItemTier().getMaxUses() / 3 * 2).group(ExpertModeCore.main));
        setRegistryName("expertmodecore:" + mat.getName() + "_saw");
        efficiency = mat.getItemTier().getEfficiency() / 2;
        this.material = mat;
        MaterialItems.addItem(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        container.setDamage(container.getDamage() + 1);
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
    public ObjectType getObjType() {
        return MaterialAddition.SAW;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.saw.name", material.getTranslationKey());
    }
}
