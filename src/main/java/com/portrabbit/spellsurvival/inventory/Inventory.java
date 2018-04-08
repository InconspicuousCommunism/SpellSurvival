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
	
	public void addToInventory(ItemStack stack){
		int amount = stack.getAmount();
		for(Slot s : slots){
			if(s.getStack() != null && s.getStack().getItem() == stack.getItem()){
				int slotAmount = s.getStack().getAmount();
				int canBePlaced = s.getStack().getItem().getMaxSize() - slotAmount;
				if(canBePlaced > 0){
					s.getStack().setStackAmount(slotAmount + Math.min(amount, canBePlaced));
					amount -= Math.min(amount, canBePlaced);
				}
			}
		}
		if(amount != 0){
			stack.setStackAmount(amount);
			addToNextSlot(stack);
		}
	}
	
	public void updateInventory(){
		for(Slot s : slots){
			if(s.getStack()  != null && s.getStack().shouldBeRemoved()){
				s.setStack(null);
			}
		}
	}
	
}