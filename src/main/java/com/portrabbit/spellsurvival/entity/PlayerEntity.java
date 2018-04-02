package com.portrabbit.spellsurvival.entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.collision.Collision;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.world.World;

public class PlayerEntity extends Entity{

	private Camera cam;
	
	public PlayerEntity(float posX, float posY, Camera cam) {
		super(posX, posY, "character.png");
		this.cam = cam;
		boundingBox = new AABB(new Vector2f(transform.getPos().x, transform.getPos().y), new Vector2f(1f,1f));
		inAir = true;
	}

	@Override
	public void update(World world) {
		if(world.getInput().isKeyDown(GLFW_KEY_A))mX = .25f;
		if(world.getInput().isKeyDown(GLFW_KEY_D))mX = -.25f;
		if(world.getInput().isKeyDown(GLFW_KEY_SPACE) && !inAir)mY = .15f;
		super.update(world);
		
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
		
		cam.setPos(this.getTransformation().getPos().mul(-this.getTransformation().getScale().x, new Vector3f()));
		
	}
	
	@Override
	public void move(float posX, float posY) {
		super.move(posX, posY);
		
	}

}
