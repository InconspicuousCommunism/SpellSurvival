package com.portrabbit.spellsurvival.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.entity.Entity;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.entity.EntityRenderer;
import com.portrabbit.spellsurvival.world.World;

public class ItemEntity extends Entity{

	private static final int PICKUP_DELAY = 300;
	private static final Vector3f DEFAULT_AFTER_SCALE = new Vector3f(1f,1f,1f);
	
	private ItemStack item;
	private int pickupDelayCounter;
	
	public ItemEntity(float posX, float posY, ItemStack item) {
		super(posX, posY, null);
		this.item = item;
		this.transform.setAfterScale(DEFAULT_AFTER_SCALE);
		this.boundingBox = new AABB(new Vector2f(this.transform.getPos().x, this.transform.getPos().y), new Vector2f(.5f,.5f));
		renderer = new EntityRenderer(1, 1, .5f * .75f);
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
	@Override
	public Texture getTexture() {
		return item.getItem().getTexture();
	}
	
	@Override
	public void renderEntity(World world) {
		super.renderEntity(world);
	}
	
	@Override
	public void update(World world) {
		super.update(world);
		collideWithWorld(world);
		if(pickupDelayCounter > 0){
			pickupDelayCounter--;
		}
	}
	
	public boolean canBePickedUp(){
		return pickupDelayCounter <= 0;
	}
	
	public void onDrop(){
		pickupDelayCounter = PICKUP_DELAY;
	}
	
}
