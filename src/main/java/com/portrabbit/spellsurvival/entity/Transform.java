package com.portrabbit.spellsurvival.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
	
	private Vector3f pos;
	private Vector3f scale;
	private Vector3f afterScale;
	
	public Transform() {
		pos = new Vector3f();
		scale = new Vector3f();
		afterScale = new Vector3f();
	}
	
	public Matrix4f getProjection(Matrix4f other){
		other.scale(scale);
		other.translate(pos);
		other.scale(afterScale);
		return other;
	}

	public Vector3f getPos() {
		return pos;
	}

	public Transform setPos(Vector3f pos) {
		this.pos = pos;
		return this;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Transform setScale(Vector3f scale) {
		this.scale = scale;
		return this;
	}

	public Vector3f getAfterScale() {
		return afterScale;
	}

	public Transform setAfterScale(Vector3f afterScale) {
		this.afterScale = afterScale;
		return this;
	}
	
}
