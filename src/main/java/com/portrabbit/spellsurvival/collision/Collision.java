package com.portrabbit.spellsurvival.collision;

import org.joml.Vector2f;

public class Collision {
	
	private Vector2f dist;
	private boolean isIntersect;
	
	public Collision(Vector2f dist, boolean isIntersect) {
		this.dist = dist;
		this.isIntersect = isIntersect;
	}
	
	public boolean isIntersecting(){
		return this.isIntersect;
	}
	
	public Vector2f getDistance(){
		return dist;
	}
	
}
