package com.portrabbit.spellsurvival.inventory;

import com.portrabbit.spellsurvival.item.ItemStack;
import com.portrabbit.spellsurvival.render.item.InventoryItemRenderer;

public class Slot{
	
	private ItemStack stack;
	private int id;
	
	public Slot(int id) {
		this.id = id;
	}
	
	public ItemStack getStack(){
		return stack;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setStack(ItemStack stack){
		this.stack = stack;
	}
}
