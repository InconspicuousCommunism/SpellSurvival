package com.portrabbit.spellsurvival.world.tile;

import org.joml.Vector2f;

import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.tile.TileRenderer;
import com.portrabbit.spellsurvival.world.World;

public class TileLocation {
	
	private Tile tile;
	private int x, y;
	private AABB bounds;
	
	public TileLocation(int x, int y) {
		this.x = x;
		this.y = y;
		bounds = null;
	}
	
	public void setTile(Tile t){
		this.tile = t;
		if(t.isSolid()){
			bounds = new AABB(new Vector2f(x, y), new Vector2f(.5f,.5f));
		}else bounds = null;
	}

	public void renderTile(TileRenderer renderer, Shader shader, World world, Camera camera) {
		renderer.QueueTile(tile, x, y, 0);
	}

	public Tile getTile() {
		return tile;
	}
	
	public boolean hasBounds(){
		return bounds != null;
	}
	
	public AABB getBounds(){
		if(tile != null && !tile.isSolid())return null;
		return bounds;
	}
	
}
