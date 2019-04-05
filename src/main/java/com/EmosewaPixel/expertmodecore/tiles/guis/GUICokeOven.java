package com.EmosewaPixel.expertmodecore.tiles.guis;

import com.EmosewaPixel.expertmodecore.tiles.TileEntityCokeOven;
import net.minecraft.inventory.IInventory;

public class GUICokeOven extends GUIMachineBase {
    public GUICokeOven(IInventory playerInventory, TileEntityCokeOven te) {
        super(playerInventory, te, "expertmodecore:textures/gui/container/coke_oven.png");
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        if (((TileEntityCokeOven) te).getCreosoteAmount() > 0) {
            int amount = ((TileEntityCokeOven) te).getCreosoteAmount() / 500;
            drawTexturedModalRect(guiLeft + 148, guiTop + 49 - amount, 176, 43 - amount, 12, amount);
        }
    }
}
