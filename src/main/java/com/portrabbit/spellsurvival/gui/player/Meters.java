package com.portrabbit.spellsurvival.gui.player;

import org.joml.Vector3f;

import com.portrabbit.spellsurvival.gui.GUIElement;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;

public class Meters extends GUIElement{
	
	private Texture healthTexture, hungerTexture, manaCorruptTexture, manaTexture;
	private float healthY = -700, hungerY = -550, manaCorruptY = -400, manaY = -550;
	private float firstX, secondX;
	private int manaModel;
	
	public Meters(String loc, float x) {
		super(null, x, -300, 128, 128);
		healthTexture = Texture.loadTexture(loc + "health_meter.png");
		hungerTexture = Texture.loadTexture(loc + "hunger_meter.png");
		manaCorruptTexture = Texture.loadTexture(loc + "mana_corruption_meter.png");
		manaTexture = Texture.loadTexture(loc + "mana_meter.png");
		manaModel = renderer.addExtraModel(128, 384);
		firstX = x;
		secondX = -x;
	}	
	
	@Override
	public void renderElement(Shader s, Camera cam) {
		trans.setPos(new Vector3f(firstX, healthY, 0));
		renderer.render(this, s, trans, cam);
		
		trans.setPos(new Vector3f(firstX, hungerY, 0));
		renderer.render(hungerTexture, s, trans, cam);
		
		trans.setPos(new Vector3f(firstX, manaCorruptY, 0));
		renderer.render(manaCorruptTexture, s, trans, cam);
		
		trans.setPos(new Vector3f(secondX, manaY, 0));
		renderer.render(manaTexture, s, trans, cam, manaModel);
	}
	
	@Override
	public Texture getTexture() {
		return healthTexture;
	}
	
}
