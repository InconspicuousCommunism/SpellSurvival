package com.portrabbit.spellsurvival.gui;

import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.gui.GUIRenderer;

public abstract class GUIElement {
	
	protected Texture texture;
	protected GUIRenderer renderer;
	protected float x, y;
	protected int width, height;
	protected Transform trans;
	private int id = -1;
	
	public GUIElement(Texture texture, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.texture = texture;
		renderer = new GUIRenderer(width, height);
		this.trans = new Transform().setScale(new Vector3f(1,1f,1)).setAfterScale(new Vector3f(2 * SpellSurvival.GAME_SCALE,2 * SpellSurvival.GAME_SCALE,1)).setPos(new Vector3f(x,y,0));
	}
	
	public void renderElement(Shader s, Camera cam){
		renderer.render(this, s, trans, cam);
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public GUIElement setAfterScale(Vector3f scale){
		this.trans.setAfterScale(scale);
		return this;
	}
	
}
