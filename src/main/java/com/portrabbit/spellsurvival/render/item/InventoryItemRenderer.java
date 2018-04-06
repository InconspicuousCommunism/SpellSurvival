package com.portrabbit.spellsurvival.render.item;

import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.item.Item;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Model;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;

public class InventoryItemRenderer {
	
	private Model itemModel;
	
	public InventoryItemRenderer() {
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
		itemModel = new Model(verts,texture,indicies);
	}
	
	public void bindShader(Shader s){
		s.bind();
		s.setUniform("sampler", 0);
	}
	
	public void render(Item item, Shader s, Transform trans, Camera cam){
		item.getTexture().bind();
		
		s.setUniform("projection", trans.getProjection(cam.getProjection()));
		
		itemModel.render();
	}
	
	public void render(Texture t, Shader s, Transform trans, Camera cam){
		t.bind();
		
		s.setUniform("projection", trans.getProjection(cam.getProjection()));
		
		itemModel.render();
	}
	
}
