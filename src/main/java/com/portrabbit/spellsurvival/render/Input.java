package com.portrabbit.spellsurvival.render;
	
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {
	
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;
	public static final int MOUSE_MIDDLE = 2;
	
	private long window;
	
	private boolean keys[];
	private boolean mouse[];
	
	public Input(long window){
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		this.mouse = new boolean[GLFW_MOUSE_BUTTON_LAST];
		for(int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
		for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++)
			mouse[i] = false;
	}
	
	public boolean isKeyDown(int key){
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isMouseButtonDown(int key){
		return glfwGetMouseButton(window, key) == 1;
	}
	
	public Vector2f getMousePosition(){
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, posX, posY);
		return new Vector2f((float)posX.get(0), (float)posY.get(0));
	}
	
	public void update(){
		for(int i = 32; i < GLFW_KEY_LAST; i++){
			keys[i] = isKeyDown(i);
		}
		for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++){
			mouse[i] = isMouseButtonDown(i);
		}
	}
	
	public boolean isKeyPressed(int key){
		return (isKeyDown(key) && !keys[key]); 
	}
	
	public boolean isKeyReleased(int key){
		return (!isKeyDown(key) && keys[key]);
	}
	
	public boolean isMouseButtonPressed(int key){
		return (isMouseButtonDown(key) && !mouse[key]); 
	}
	
	public boolean isMouseButtonReleased(int key){
		return (!isMouseButtonDown(key) && mouse[key]);
	}
	
}
