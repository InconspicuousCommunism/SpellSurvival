package com.portrabbit.spellsurvival.gui;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Input;
import com.portrabbit.spellsurvival.render.Shader;

public abstract class GUI {
	
	private int nextId = 0;
	protected ArrayList<GUIElement> elements;
	protected Shader s;
	private boolean shouldClear = false;
	public static final String RESOURCE_LOCATION = "src/main/resources/gui/";
	
	public GUI() {
		elements = new ArrayList<GUIElement>();
		s = new Shader("shader");
	}
	
	public void renderGUI(Camera cam){
		for(GUIElement e : elements){
			e.renderElement(s, cam);
		}
	}
	
	public void addElement(GUIElement element){
		element.setId(this.getNextId());
		this.elements.add(element);
	}
	
	public int getNextId(){
		return nextId++;
	}
	
	public void onClicked(float x, float y, int type){
		for(GUIElement e : elements){
			if(x > e.getX() - e.getWidth()/2 && x < e.getX() + e.getWidth()/2 &&
					y > e.getY() - e.getHeight()/2 && y < e.getY() + e.getHeight()/2){
				if(e instanceof GUIButton){
					buttonClicked(e.getId());
				}
			}
		}
	}
	
	public void updateGUI(){
		if(SpellSurvival.instance.getWindow().getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
			Vector2f pos = SpellSurvival.instance.getWindow().getInput().getMousePosition();
			float x = pos.x - SpellSurvival.instance.getWindow().getWidth()/2;
			float y = -(pos.y - SpellSurvival.instance.getWindow().getHeight()/2);
			onClicked(x,y,Input.MOUSE_LEFT);
			System.out.println(x + " : " + y);
		}
		if(shouldClear) {
			elements.clear();
			this.shouldClear = false;
		}
	}
	
	public void buttonClicked(int id){}
	
	public abstract void init();
	
	public void close(){
		this.shouldClear = true;;
	}
	
}
