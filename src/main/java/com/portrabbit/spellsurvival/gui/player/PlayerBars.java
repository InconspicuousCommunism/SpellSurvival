package com.portrabbit.spellsurvival.gui.player;

import org.joml.Vector3f;

import com.portrabbit.spellsurvival.gui.GUIElement;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;

public class PlayerBars extends GUIElement {
	
	
	private Texture itemBarTexture;
	private Texture spellBarTexture;
	private int secondModel = -1;
	
	//Item Hot Bar & Spells Bar
	public PlayerBars(String loc) {
		super(null, 0, -400, 631, 78);
		itemBarTexture = Texture.loadTexture(loc + "item_bar.png");
		spellBarTexture = Texture.loadTexture(loc + "spell_bar.png");	
		secondModel = this.renderer.addExtraModel(78, 354);
	}
	
	@Override
	public void renderElement(Shader s, Camera cam) {
		renderer.render(this, s, trans, cam);
		x = 675;
		y = 000;
		trans.setPos(new Vector3f(x,y,0));
		renderer.render(spellBarTexture, s, trans, cam, secondModel);
		x = 0;
		y = -700;
		trans.setPos(new Vector3f(x,y,0));
	}
	
	@Override
	public Texture getTexture() {
		return itemBarTexture;
	}
	
}
