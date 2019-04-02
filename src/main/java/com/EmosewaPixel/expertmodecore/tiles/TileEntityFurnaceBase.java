package com.EmosewaPixel.expertmodecore.tiles;

import com.EmosewaPixel.expertmodecore.blocks.BlockFurnaceBase;
import com.EmosewaPixel.expertmodecore.recipes.MachineRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityFurnaceBase extends TileEntity implements ITickable {
    private int progress = 0;
    protected int burnTime = 0;
    protected int maxBurnTime = 0;
    private int inputCount;
    public int slotCount;

    private ArrayList<MachineRecipe> recipes;

    public void setProgress(int i) {
        progress = i;
    }

    public int getProgress() {
        return progress;
    }

    public void setBurnTime(int i) {
        burnTime = i;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setMaxBurnTime(int i) {
        maxBurnTime = i;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public TileEntityFurnaceBase(TileEntityType type, int inputCount, ArrayList<MachineRecipe> recipes) {
        super(type);
        this.inputCount = inputCount;
        slotCount = 2 + inputCount;
        this.recipes = recipes;
    }

    protected ItemStackHandler input;

    protected ItemStackHandler fuel_input;

    protected ItemStackHandler output;

    protected CombinedInvWrapper combinedHandler;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (burnTime > 0) {
                burnTime--;
                world.setBlockState(pos, world.getBlockState(pos).with(BlockFurnaceBase.LIT, true));
                if (progress > 0) {
                    progress--;
                    if (progress == 0)
                        smelt();
                } else
                    startSmelting();
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(BlockFurnaceBase.LIT, false));
                if (input.getStackInSlot(0) != ItemStack.EMPTY)
                    consumeFuel();
            }
            markDirty();
        }
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty())
            return 0;
        int rec = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, rec == -1 ? TileEntityFurnace.getBurnTimes().getOrDefault(stack.getItem(), 0) : rec);
    }

    private void startSmelting() {
        MachineRecipe recipe = getRecipeByInput();
        if (recipe != null)
            if (output.insertItem(0, recipe.getOutput().copy(), true).isEmpty()) {
                progress = 200;
            }
    }

    private void smelt() {
        MachineRecipe recipe = getRecipeByInput();
        if (recipe != null)
            if (output.insertItem(0, recipe.getOutput().copy(), false).isEmpty()) {
                for (int i = 0; i < inputCount; i++)
                    input.extractItem(i, recipe.getinput(i).copy().getCount(), false);
            }
    }

    protected void consumeFuel() {
        burnTime = maxBurnTime = getItemBurnTime(fuel_input.getStackInSlot(0));
        if (burnTime > 0)
            fuel_input.extractItem(0, 1, false);
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.hasKey("InputItems")) {
            input.deserializeNBT((NBTTagCompound) compound.getTag("input_items"));
        }
        if (compound.hasKey("FuelItems")) {
            fuel_input.deserializeNBT((NBTTagCompound) compound.getTag("fuel_items"));
        }
        if (compound.hasKey("OutputItems")) {
            output.deserializeNBT((NBTTagCompound) compound.getTag("output_items"));
        }
        compound.getInt("Progress");
        compound.getInt("BurnTime");
        compound.getInt("MaxBurnTime");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setTag("InputItems", input.serializeNBT());
        compound.setTag("FuelItems", fuel_input.serializeNBT());
        compound.setTag("OutputItems", output.serializeNBT());
        compound.setInt("Progress", progress);
        compound.setInt("BurnTime", burnTime);
        compound.setInt("MaxBurnTime", maxBurnTime);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null)
                return LazyOptional.of(() -> combinedHandler).cast();
            if (side == EnumFacing.UP)
                return LazyOptional.of(() -> this.input).cast();
            if (side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                return LazyOptional.of(() -> this.fuel_input).cast();

            if (side == EnumFacing.DOWN)
                return LazyOptional.of(() -> this.output).cast();
        }
        return LazyOptional.empty();
    }

    private MachineRecipe getRecipeByInput() {
        ItemStack[] stacks = new ItemStack[inputCount];
        for (int i = 0; i < inputCount; i++)
            stacks[i] = input.getStackInSlot(i);

        for (MachineRecipe recipe : recipes) {
            if (recipe.isInputValid(stacks))
                return recipe;
        }
        return null;
    }
}
