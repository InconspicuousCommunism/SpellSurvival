package com.portrabbit.spellsurvival.collision;

import org.joml.Vector2f;

public class AABB {
	
	private Vector2f center, halfExtent;
	public float x, X, y, Y;
	public boolean fixedY = false;
	
	public AABB(Vector2f center, Vector2f halfExtent){
		this.center = center;
		this.halfExtent = halfExtent;
		calculateSides();
	}
	
	private void calculateSides(){
		x = center.x - halfExtent.x;
		y = center.y - halfExtent.y;
		X = center.x + halfExtent.x;
		Y = center.y + halfExtent.y;
	}
	
	public Collision getCollision(AABB other){
		Vector2f dist = other.center.sub(center, new Vector2f());
		dist.x = (float)Math.abs(dist.x);
		dist.y = (float)Math.abs(dist.y);
		dist.sub(halfExtent.add(other.halfExtent, new Vector2f()));
		return new Collision(dist, (dist.x < 0 && dist.y < 0));
	}
	
	public void correctPosition(AABB other, Collision col){
		Vector2f correct = other.center.sub(center, new Vector2f());
		if((col.getDistance().x)/this.getHalfExtent().x > (col.getDistance().y)/this.getHalfExtent().y){
			fixedY = false;
			if(correct.x > 0){
				center.add(col.getDistance().x, 0);
			}else{
				center.add(-col.getDistance().x, 0);
			}
		}else{
			fixedY = true;
			if(correct.y > 0){
				center.add(0,col.getDistance().y);
			}else{
				center.add(0,-col.getDistance().y);
			}
		}
	}

	
	public Vector2f getCenter(){
		return center;
	}
	
	public Vector2f getHalfExtent(){
		return halfExtent;
	}
	
}
