package com.portrabbit.spellsurvival.render.tile;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Model;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.world.World;
import com.portrabbit.spellsurvival.world.tile.Tile;

public class TileRenderer {
	
	private Model tileModel;
	private Model backdropModel;
	
	Texture lastText = null;
	
	private HashMap<Texture, HashMap<Vector3f, Tile>> textureRenders = new HashMap<Texture, HashMap<Vector3f, Tile>>();
	
	public TileRenderer() {
		float[] verts = new float[] {
				-.5f, .5f,
				-.5f, -.5f,
				.5f, -.5f,
				.5f, .5f
		};
		float[] verts2 = new float[] {
				-1f, 1f,
				-1f, -1f,
				1f, -1f,
				1f, 1f
		};
		float[] texture = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
		};
		int[] indicies = new int[] {0,1,2,2,3,0};
		tileModel = new Model(verts,texture,indicies);
		backdropModel = new Model(verts,texture,indicies);
	}
	
	public void QueueTile(Tile t, float x, float y, float z){
		if(t != null){
			if(!textureRenders.containsKey(t.getTexture()))textureRenders.put(t.getTexture(), new HashMap<Vector3f, Tile>());
			textureRenders.get(t.getTexture()).put(new Vector3f(x,y,z), t);
		}
	}
	
	public void renderTiles(Shader shader, World world, Camera cam){
		for(Texture t : textureRenders.keySet()){
			for(Vector3f v : textureRenders.get(t).keySet()){
				renderTile(textureRenders.get(t).get(v), v.x, v.y, v.z, shader, world, cam);
			}
		}
	}
	
	public void renderTile(Tile tile, float x, float y, float z, Shader shader, World world, Camera cam){
		if(lastText == null || lastText != tile.getTexture()){
			tile.bindTileTexture();
			lastText = tile.getTexture();
		}
		
		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x, y, 0));
		Matrix4f target = new Matrix4f();
		
		cam.getProjection().mul(world.getWorldScale(), target);
		target.mul(tile_pos);
		
		shader.setUniform("projection", target);
		
		tileModel.doRender();
	}
	
	public void renderBackdrop(Texture backdrop, Shader s, Camera cam){
		backdrop.bind();
		
		Transform trans = new Transform();
		trans.setScale(new Vector3f(4,4,0));
		
		s.setUniform("projection", new Matrix4f().scale(1920f/1000f * 2f,2f,2f));
		
		backdropModel.render();
		
	}
	
	public void prepareRendering(Shader s){
		tileModel.prepare();
		s.setUniform("sampler", 0);
	}
	
	public void finishRendering(){
		tileModel.finish();
		lastText = null;
		textureRenders.clear();
	}
	
}
