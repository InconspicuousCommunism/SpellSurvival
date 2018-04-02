package com.portrabbit.spellsurvival.render.entity;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import com.portrabbit.spellsurvival.entity.Entity;
import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Model;
import com.portrabbit.spellsurvival.render.Shader;

public class EntityRenderer {
	
	private Model entityModel;
	
	public EntityRenderer() {
		float[] verts = new float[] {
				-.5f, .5f,
				-.5f, -.5f,
				.5f, -.5f,
				.5f, .5f
		};
		float[] texture = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
			};
		int[] indicies = new int[] {0,1,2,2,3,0};
		entityModel = new Model(verts,texture,indicies);
	}
	
	public void renderEntity(Entity e, Shader shader, Transform trans, Camera cam){
		e.getTexture().bind();
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", trans.getProjection(cam.getProjection()));
		entityModel.render();
	}
	
}
