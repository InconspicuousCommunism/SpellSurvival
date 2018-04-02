package com.portrabbit.spellsurvival.render.gui;

import java.util.ArrayList;

import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.gui.GUIElement;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Model;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;

public class GUIRenderer {
	
	private Model modelGUI;
	private ArrayList<Model> extraModels;
	
	public GUIRenderer(float width, float height) {
		extraModels = new ArrayList<Model>();
		if(width > height){
			width /= height;
			height /= height;
		}else{
			height /= width;
			width /= width;
		}
		float[] verts = new float[] {
				-width, height,   //Top Left
				-width, -height,  //Bottom Left
				width, -height,   // Bottom Right
				width, height     // Top Right
		};
		float[] texture = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
			};
		int[] indicies = new int[] {0,1,2,2,3,0};
		modelGUI = new Model(verts,texture,indicies);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return id of the model
	 */
	public int addExtraModel(float width, float height){
		if(width > height){
			width /= height;
			height /= height;
		}else{
			height /= width;
			width /= width;
		}
		float[] verts = new float[] {
				-width, height,   //Top Left
				-width, -height,  //Bottom Left
				width, -height,   // Bottom Right
				width, height     // Top Right
		};
		float[] texture = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
			};
		int[] indicies = new int[] {0,1,2,2,3,0};
		extraModels.add(new Model(verts,texture,indicies));
		return extraModels.size()-1;
	}
	
	public void render(GUIElement e, Shader s, Transform trans, Camera cam){
		s.bind();
		e.getTexture().bind();
		
		s.setUniform("sampler", 0);
		s.setUniform("projection", trans.getProjection(cam.getProjection()));
		
		modelGUI.render();
	}
	
	public void render(Texture t, Shader s, Transform trans, Camera cam){
		s.bind();
		t.bind();
		
		s.setUniform("sampler", 0);
		s.setUniform("projection", trans.getProjection(cam.getProjection()));
		
		modelGUI.render();
	}
	
	public void render(Texture t, Shader s, Transform trans, Camera cam, int modelId){
		s.bind();
		t.bind();
		
		s.setUniform("sampler", 0);
		s.setUniform("projection", trans.getProjection(cam.getProjection()));
		
		this.extraModels.get(modelId).render();
	}
	
}
