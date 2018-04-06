package com.portrabbit.spellsurvival.gui.player;

import com.portrabbit.spellsurvival.gui.GUI;
import com.portrabbit.spellsurvival.gui.InventoryGUI;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Texture;

public class PlayerInventoryGUI extends InventoryGUI{
	
	private static Texture invTexture = Texture.loadTexture(GUI.RESOURCE_LOCATION + "player_inventory.png");
	
	public PlayerInventoryGUI(Inventory inv) {
		super(inv);
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 6; x++){
				registerSlot(x + y * 6, -212 + 84.5f * x, 295 + -84.5f * y);
			}
		}
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void drawGuiLayer(Camera cam) {
		renderGUI(invTexture, 0f, 0f, 807f, 1073f, 8, cam);
	}
	
}
