package com.portrabbit.spellsurvival.inventory;

import com.portrabbit.spellsurvival.item.ItemStack;

public class Inventory{
	
	public Slot[] slots;
	
	public Inventory(int size) {
		slots = new Slot[size];
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(i);
		}
	}
	
	public void setSlotStack(ItemStack stack, int slot){
		slots[slot].setStack(stack);
	}
	
	public boolean hasEmptySlot(){
		for(Slot s : slots){
			if(s.getStack() == null)return true;
		}
		return false;
	}
	
	public void addToNextSlot(ItemStack stack){
		for(Slot s : slots){
			if(s.getStack() == null){
				s.setStack(stack);
				return;
			}
		}
	}
	
}
