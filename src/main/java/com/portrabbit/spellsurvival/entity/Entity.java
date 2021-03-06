package com.portrabbit.spellsurvival.entity;

import java.util.HashMap;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.collision.Collision;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.entity.EntityRenderer;
import com.portrabbit.spellsurvival.world.World;

public abstract class Entity {
	
	private static HashMap<String, Texture> entityTextures = new HashMap<String, Texture>();
	
	protected AABB boundingBox;
	protected Transform transform;
	protected Texture texture;
	protected EntityRenderer renderer;
	protected Shader shader;
	protected float scale;
	
	protected float mX;
	protected float mY;
	protected boolean inAir;
	
	private static final float FALL_ACCEL = .0098f;
	private static final float X_DECAY = .2f;
	
	public Entity(float posX, float posY, String texture) {
		if(texture != null){
			if(!entityTextures.containsKey(texture))entityTextures.put(texture, Texture.loadTexture("src/main/resources/entity/"+texture));
			this.texture = entityTextures.get(texture);
		}
		this.renderer = new EntityRenderer();
		this.shader = new Shader("shader");
		this.scale = SpellSurvival.GAME_SCALE;
		this.transform = new Transform().setScale(new Vector3f(scale,scale,1)).setAfterScale(new Vector3f(2f,2f,1));
		setPosition(posX, posY);
	}
	
	public void move(float posX, float posY){
		this.transform.setPos(this.transform.getPos().add(new Vector3f(posX, posY, 0)));
	}
	
	public void setPosition(float posX, float posY){
		this.transform.setPos(new Vector3f(posX, posY, 0));
	}
	
	public float getX(){
		return this.transform.getPos().x;
	}
	
	public float getY(){
		return this.transform.getPos().y;
	}
	
	public Texture getTexture(){
		return this.texture;
	}
	
	public AABB getBoundingBox(){
		return boundingBox;
	}
	
	public float getScale(){	
		return scale;
	}
	public Transform getTransformation(){
		return this.transform;
	}
	
	public void renderEntity(World world){
		renderer.renderEntity(this, shader, transform, world.getWorldCamera());
	}
	
	public void collideWithWorld(World world){
		boundingBox.getCenter().set(transform.getPos().x, transform.getPos().y);
		
		AABB[] boxes = new AABB[36];
		for(int x = -2; x < 3; x++){
			for(int y = -2; y < 3; y++){
				boxes[(x+2) + (y+2) * 5] = world.getBoundsAt((int)transform.getPos().x + x, (int)-transform.getPos().y + y);
			}
		}
		AABB box = null;
		for(int i = 0; i < boxes.length; i++){
			if(boxes[i] != null){
				if(box == null) box = boxes[i];
				Vector2f len1 = box.getCenter().sub(transform.getPos().x, transform.getPos().y, new Vector2f());
				Vector2f len2 = boxes[i].getCenter().sub(transform.getPos().x, transform.getPos().y, new Vector2f());
				
				if(len1.lengthSquared() > len2.lengthSquared()){
					box = boxes[i];
				}
			}
		}
		if(box != null){
			Collision data = this.boundingBox.getCollision(box);
			if(data.isIntersecting()){
				this.boundingBox.correctPosition(box, data);
				transform.setPos(new Vector3f(boundingBox.getCenter(), 0));
				this.inAir = false;
			}else{
				this.inAir = true;
			}
			for(int i = 0; i < boxes.length; i++){
				if(boxes[i] != null){
					if(box == null) box = boxes[i];
					Vector2f len1 = box.getCenter().sub(transform.getPos().x, transform.getPos().y, new Vector2f());
					Vector2f len2 = boxes[i].getCenter().sub(transform.getPos().x, transform.getPos().y, new Vector2f());
					
					if(len1.lengthSquared() > len2.lengthSquared()){
						box = boxes[i];
					}
				}
			}
			data = this.boundingBox.getCollision(box);
			if(data.isIntersecting()){
				this.boundingBox.correctPosition(box, data);
				transform.setPos(new Vector3f(boundingBox.getCenter(), 0));
			}
		}
	}
	
	public void update(World world){
		if(inAir || mY > 0){
			mY -= FALL_ACCEL;
		}else if(mY < 0)mY = -.1f;
		move(-mX, mY);
		if(mX > 0) mX -= Math.min(mX, X_DECAY);
		else if(mX < 0) mX += Math.min(-mX, X_DECAY);
	}
	
}
