package com.portrabbit.spellsurvival.render.entity;

import java.util.ArrayList;

import com.portrabbit.spellsurvival.entity.Entity;
import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Model;
import com.portrabbit.spellsurvival.render.Shader;

public class EntityRenderer {
	
	private static final float[] verts = new float[] {
			-.5f, .5f,
			-.5f, -.5f,
			.5f, -.5f,
			.5f, .5f
	};
	private static final float[] texture = new float[] {
			0,1,
			0,0,
			1,0,
			1,1
		};
	private static final int[] indicies = new int[] {0,1,2,2,3,0};
	private static final Model baseModel;
	
	static{
		baseModel = new Model(verts,texture,indicies);
	}
	
	private boolean isCustom = false;
	private Model customModel;
	
	public EntityRenderer() {}
	public EntityRenderer(float width, float height, float size){
		isCustom = true;
		if(width > height){
			width /= height;
			height /= height;
		}else{
			height /= width;
			width /= width;
		}
		float[] verts = new float[] {
				-width * size, height * size,   //Top Left
				-width * size, -height * size,  //Bottom Left
				width * size, -height * size,   // Bottom Right
				width * size, height * size     // Top Right
		};
		customModel = new Model(verts,texture,indicies);
	}
	
	public void renderEntity(Entity e, Shader shader, Transform trans, Camera cam){
		e.getTexture().bind();
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", trans.getProjection(cam.getProjection()));
		if(!isCustom)
			baseModel.render();
		else customModel.render();
	}
	
}
