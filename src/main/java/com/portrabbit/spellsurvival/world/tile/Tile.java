package com.portrabbit.spellsurvival.world.tile;

import java.util.ArrayList;

import com.portrabbit.spellsurvival.item.TileItem;
import com.portrabbit.spellsurvival.render.Texture;

public class Tile {
	
	public static String RESOURCE_LOCATION = "src/main/resources/tiles/";
	
	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public static Tile GRASS = new Tile("grass.png").setSolid(true);
	public static Tile FLOWER_GRASS = new Tile("flower_grass.png").setSolid(true);
	public static Tile STONE = new Tile("stone.png").setSolid(true);
	public static Tile WATER = new Tile("water.png").setCanBeBroken(false);
	public static Tile WOOD = new Tile("wood.png");
	public static Tile DIRT = new Tile("dirt.png").setSolid(true);
	
	private TileItem tileItem;
	private Texture texture;
	private int tId;
	
	private boolean solid;
	private boolean canBeBroken;
	
	public Tile(String texture) {
		this.texture = Texture.loadTexture(RESOURCE_LOCATION+texture);
		this.tId = getNextUnassignedId();
		this.tileItem = new TileItem(this);
		this.solid = false;
		canBeBroken = true;
		tiles.add(this);
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public void bindTileTexture(){
		texture.bind();
	}
	
	public int getTileId(){
		return tId;
	}
	
	public Tile setSolid(boolean solid){
		this.solid = solid;
		return this;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public static int getNextUnassignedId(){
		return tiles.size();
	}
	
	public TileItem getTileItem(){
		return this.tileItem;
	}
	
	public boolean canBeBroken(){
		return canBeBroken;
	}
	
	public Tile setCanBeBroken(boolean broken){
		canBeBroken = broken;
		return this;
	}
	
}
