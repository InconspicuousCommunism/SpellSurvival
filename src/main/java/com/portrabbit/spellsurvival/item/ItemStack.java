package com.portrabbit.spellsurvival.item;

public class ItemStack {
	
	private Item item;
	private int amount;
	
	public ItemStack(Item item) {
		this(item, 1);
	}
	
	public ItemStack(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public Item getItem(){
		return this.item;
	}
	
	/**
	 * 
	 * @param amount to reduce by
	 * @return true / false if the resulting amount is <= 0
	 */
	public boolean decreaseStack(int amount){
		this.amount-=amount;
		return this.amount <= 0;
	}
	
}
