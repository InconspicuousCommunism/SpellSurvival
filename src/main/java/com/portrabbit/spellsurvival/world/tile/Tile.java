package com.portrabbit.spellsurvival.world.tile;

import java.util.ArrayList;

import com.portrabbit.spellsurvival.render.Texture;

public class Tile {
	
	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public static Tile GRASS = new Tile("grass.png").setSolid(true);
	public static Tile FLOWER_GRASS = new Tile("flower_grass.png").setSolid(true);
	public static Tile STONE = new Tile("stone.png").setSolid(true);
	public static Tile WATER = new Tile("water.png");
	public static Tile WOOD = new Tile("wood.png");
	public static Tile DIRT = new Tile("dirt.png").setSolid(true);
	
	private Texture texture;
	private int tId;
	
	private boolean solid;
	
	public Tile(String texture) {
		this.texture = Texture.loadTexture("src/main/resources/tiles/"+texture);
		this.tId = getNextUnassignedId();
		tiles.add(this);
		solid = false;
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
	
}
