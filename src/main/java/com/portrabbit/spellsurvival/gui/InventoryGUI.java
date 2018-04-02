package com.portrabbit.spellsurvival.gui;

import org.joml.Vector3f;

import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.inventory.Slot;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.item.InventoryItemRenderer;

public class InventoryGUI extends GUI{
	
	Inventory inv;
	InventoryItemRenderer itemRenderer;
	
	private float startX, startY;
	
	public InventoryGUI(Inventory inv) {
		this.inv = inv;
		itemRenderer = new InventoryItemRenderer();
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void renderGUI(Camera cam) {
		for(Slot s : inv.slotList){
			itemRenderer.render(s.getStack().getItem(), this.s, new Transform().setPos(new Vector3f(s.getX() + startX, s.getY() + startY, 0)), cam);
		}
	}
	
}
