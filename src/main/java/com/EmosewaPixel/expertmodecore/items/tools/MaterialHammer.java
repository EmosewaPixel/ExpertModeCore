package com.EmosewaPixel.expertmodecore.items.tools;

import com.EmosewaPixel.expertmodecore.ExpertModeCore;
import com.EmosewaPixel.expertmodecore.materials.MaterialAddition;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialHammer extends ItemPickaxe implements IMaterialItem {
    private Material material;

    public MaterialHammer(Material mat) {
        super(mat.getItemTier(), 6, -3.4F + mat.getItemTier().getEfficiency() / 2 * 0.1F, new Properties().defaultMaxDamage(mat.getItemTier().getMaxUses()).group(ExpertModeCore.main));
        setRegistryName("expertmodecore:" + mat.getName() + "_hammer");
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
    public ObjectType getObjType() {
        return MaterialAddition.HAMMER;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.hammer.name", material.getTranslationKey());
    }
}