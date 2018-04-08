package com.portrabbit.spellsurvival.item;

import com.portrabbit.spellsurvival.world.tile.Tile;

public class ItemStack {
	
	private Item item;
	private int amount;
	
	public ItemStack(Item item) {
		this(item, 1);
	}
	
	public ItemStack(Tile t){
		this(t, 1);
	}
	
	public ItemStack(Tile t, int amount){
		this(t.getTileItem(), amount);
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
		return shouldBeRemoved();
	}
	
	public void setStackAmount(int amount){
		this.amount = amount;
	}
	
	public boolean shouldBeRemoved(){
		return this.amount <= 0;
	}
	
	public void worldClicked(float mX, float mY, int type){
		this.item.onClickInWorld(mX, mY, type, this);
	}
	
}
