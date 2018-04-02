package com.portrabbit.spellsurvival.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	private Vector3f pos;
	private Matrix4f projection;
	
	public Camera(int width, int height){
		pos = new Vector3f(0,0,0);
		projection = new Matrix4f().setOrtho(-width/2, width/2, -height/2, height/2, -100, 100);
	}
	
	public void setPos(Vector3f pos){
		this.pos = pos;
	}
	
	public void addPos(Vector3f pos){
		this.pos.add(pos);
	}
	
	public Vector3f getPos(){
		return pos;
	}
	
	public void setProjection(int width, int height){
		this.projection = new Matrix4f().setOrtho(-width/2, width/2, -height/2, height/2, -100, 100);;
	}
	
	public Matrix4f getProjection(){
		Matrix4f position = new Matrix4f().setTranslation(pos);
		Matrix4f end = new Matrix4f();
		end = projection.mul(position, end);
		return end;
	}
	
}
