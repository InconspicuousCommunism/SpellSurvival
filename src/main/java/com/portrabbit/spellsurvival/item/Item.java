package com.portrabbit.spellsurvival.item;

import java.util.ArrayList;

import com.portrabbit.spellsurvival.render.Texture;

public class Item {
	
	private static final String RESOURCE_LOCATION = "src/main/resources/items/";
	
	private static ArrayList<Item> items = new ArrayList<Item>();

	public static Item COAL = new Item("coal.png");
	public static Item GOLD_ORE = new Item("gold_ore.png");
	public static Item IRON_ORE = new Item("iron_ore.png");
	public static Item LEATHER = new Item("leather.png");
	public static Item WOOD = new Item("wood.png");
	public static Item PLYWOOD = new Item("plywood.png");
	public static Item STICK = new Item("stick.png");
	public static Item STONE = new Item("stone.png");
	
	private Texture texture;
	private int tId;
	
	public Item(Texture texture){
		this.texture = texture;
		tId = getNextUnassignedId();
		items.add(this);
	}
	
	public Item(String tLoc){
		this(Texture.loadTexture("src/main/resources/items/" + tLoc));
	}
	
	public static int getNextUnassignedId(){
		return items.size();
	}
	
	/**
	 * 
	 * @return Maximum amount in one stack
	 */
	public int getMaxSize(){
		return 100;
	}
	
	public Texture getTexture(){
		return this.texture;
	}
	
	public void onClickInWorld(float mX, float mY, int type, ItemStack stack){}
	
}
