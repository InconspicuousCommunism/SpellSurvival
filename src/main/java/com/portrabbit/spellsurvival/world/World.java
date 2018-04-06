package com.portrabbit.spellsurvival.world;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.entity.Entity;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Input;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.tile.TileRenderer;
import com.portrabbit.spellsurvival.world.tile.Tile;

public class World {
	
	public static World world;
	
	private Chunk[] chunks;
	private int chunkSize;
	
	private TileRenderer renderer;
	private Shader shader;
	private Camera camera;
	private Matrix4f worldScale;
	private Input input;
	private WorldGen generator;
	private Texture backdrop;
	private long seed;
	
	private int wScale;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> removeEntityList;
	private ArrayList<Entity> addEntityList;
	
	public World(int width, int height, int scale, Input input){
		this.chunkSize = 128;
		this.chunks = new Chunk[chunkSize];
		for(int x = 0; x < chunks.length; x++){
			this.chunks[x] = new Chunk((x-chunkSize/2) * 16, 16);
		}
		
		this.renderer = new TileRenderer();
		this.shader = new Shader("shader");
		this.camera = new Camera(width, height);
		this.worldScale = new Matrix4f().scale(scale);
		this.backdrop = Texture.loadTexture("src/main/resources/background/backdrop.png");
		this.wScale = scale;
		this.input = input;
		this.seed = new Random().nextLong();
		this.entities = new ArrayList<Entity>();
		this.removeEntityList = new ArrayList<Entity>();
		this.addEntityList = new ArrayList<Entity>();
	}
	
	public Matrix4f getWorldScale(){
		return this.worldScale;
	}
	
	public int getScale(){
		return wScale;
	}
	
	public int getChunkSize(){
		return chunkSize;
	}
	
	public Input getInput() {
		return input;
	}

	public void renderWorld(float pX, float pY){
		GL11.glPushMatrix();
		shader.bind();
		renderer.renderBackdrop(backdrop, shader, this.getWorldCamera());
		renderer.prepareRendering(shader);
		int cX = Math.round(pX / 16f);
		for(int x = cX - 3; x < cX + 3; x++){
			try{
				chunks[getChunkSize()/2 + x].renderChunk(renderer, shader, this, getWorldCamera(), (int)pX, (int)pY);
			}catch(Exception e){
				System.err.println("Unable to load chunk " + cX);
				e.printStackTrace();
			}
		}
		renderer.renderTiles(shader, world, getWorldCamera());
		renderer.finishRendering();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		for(Entity e : this.entities){
			e.update(this);
			if(Math.abs(e.getX() - pX) < 25 && Math.abs(e.getY() - pY) < 25)
			e.renderEntity(this);
		}
		GL11.glPopMatrix();
		updateEntityList();
	}
	
	public ArrayList<Entity> getEntityList(){
		return this.entities;
	}
	
	public void setCameraPos(float x, float y){
		camera.setPos(new Vector3f(x,y,0));
	}
	
	public void moveCamera(float x, float y){
		camera.addPos(new Vector3f(x,y,0));
	}
	
	public Camera getWorldCamera(){
		return this.camera;
	}
	
	public void setTile(int x, int y, Tile t){
		getChunkFor(x).getTilesAt(x, y).setTile(t);
	}
	
	public Tile getTileAt(int x, int y){
		try{
			return getChunkFor(x).getTilesAt(x, y).getTile();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Chunk getChunkFor(float x){
		return chunks[(int)(x/16) + getChunkSize()/2];
	}
	
	public int getTopTile(int x){
		for(int y = 254; y > 0; y--){
			Tile t = this.getChunkFor(x).getTilesAt(x, y).getTile();
			if(t != null && t.isSolid()) return y;
		}
		return 0;
	}
	
	public AABB getBoundsAt(int x, int y){
		try{
			return getChunkFor(x).getTilesAt(x, y).getBounds();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void genWorld(){
		generator = new WorldGen(seed);
		generator.genWorld(this);
	}
	
	public void addEntity(Entity e){
		this.addEntityList.add(e);
	}
	
	public void removeEntity(Entity e){
		this.removeEntityList.add(e);
	}
	
	public void updateEntityList(){
		for(Entity e : this.addEntityList){
			this.entities.add(e);
		}
		for(Entity e : this.removeEntityList){
			this.entities.remove(e);
		}
		this.addEntityList.clear();
		this.removeEntityList.clear();
	}
	
}
