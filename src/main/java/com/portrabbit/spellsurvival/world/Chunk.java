package com.portrabbit.spellsurvival.world;

import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.tile.TileRenderer;
import com.portrabbit.spellsurvival.world.tile.TileLocation;

public class Chunk {
	
	private int startX, endX;
	
	private TileLocation[][] tileMap;
	
	public Chunk(int startX, int size) {
		this.startX = startX;
		if(startX > 0){
			tileMap = new TileLocation[size][255];
			this.endX = startX + size;
			for(int x = 0; x < tileMap.length; x++){
				for(int y = 0; y < tileMap[x].length; y++){
					tileMap[x][y] = new TileLocation(x + startX, y);
				}
			}
		}else if(startX < 0){
			tileMap = new TileLocation[size][255];
			this.endX = startX - size;
			for(int x = 0; x < tileMap.length; x++){
				for(int y = 0; y < tileMap[x].length; y++){
					tileMap[x][y] = new TileLocation(startX - x, y);
				}
			}
		}else{
			tileMap = new TileLocation[size*2][255];
			
			for(int x = 0; x < size; x++){
				for(int y = 0; y < tileMap[x].length; y++){
					tileMap[x+15][y] = new TileLocation(startX + x, y);
				}
			}
			for(int x = 0; x < size; x++){
				for(int y = 0; y < tileMap[x].length; y++){
					tileMap[x][y] = new TileLocation(startX - x, y);
				}
			}
		}
	}
	
	public void renderChunk(TileRenderer renderer, Shader shader, World world, Camera camera, int originX, int centerY){
		for(TileLocation[] tl : tileMap){
			if(Math.abs((tl[1] == null ? 0 : tl[1].getX()) - originX) < 27){
				for(int y = -25; y < 25; y++){
					int ay = centerY + y;
					if(ay >= 0 && ay < tl.length){
						TileLocation t = tl[ay];
						if(t != null)
							t.renderTile(renderer, shader, world, camera);
					}
				}
			}
		}
	}
	
	public TileLocation getTilesAt(int x, int y){
		if(startX == 0){
			if(x > 0){
				return tileMap[x+15][Math.abs(y)];
			}
		}
		return tileMap[Math.abs(x-startX)][Math.abs(y)];
	}
	
}
