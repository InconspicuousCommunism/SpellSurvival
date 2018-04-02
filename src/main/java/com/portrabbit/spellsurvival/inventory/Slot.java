package com.portrabbit.spellsurvival.inventory;

import com.portrabbit.spellsurvival.item.ItemStack;
import com.portrabbit.spellsurvival.render.item.InventoryItemRenderer;

public class Slot{
	
	private ItemStack stack;
	private InventoryItemRenderer itemRender;
	private float x, y;
	
	public Slot(float x, float y) {
		itemRender = new InventoryItemRenderer();
		this.x = x;
		this.y = y;
	}
	
	public ItemStack getStack(){
		return stack;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
