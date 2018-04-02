package com.portrabbit.spellsurvival.render;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class Window {
	
	private long window;
	
	private String name;
	private int width, height;
	private boolean fullScreen;
	private Input input;
	
	private boolean hasResized;
	private GLFWWindowSizeCallback sizeCallback;
	
	public Window(String name) {
		this(1000,1000, name);
	}
	
	public Window(int width, int height, String name){
		setDimensions(width, height);
		setName(name);
		setFullscreen(false);
		hasResized = false;
	}

	public void initWindow(){
		//Setup error callback
		GLFWErrorCallback.createPrint(System.err).set();
		
		window = glfwCreateWindow(width, height, name, fullScreen ? glfwGetPrimaryMonitor() : 0, 0);
		if(window == 0)throw new IllegalStateException("Failed To Create Window!");
		
		input = new Input(window);
		
		if(!fullScreen){
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vid.width() - width)/2, (vid.height()-height)/2);
		}
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) closeWindow();
		});
		
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		
		setLocalCallbacks();
	}
	
	private void setLocalCallbacks(){
		sizeCallback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long aWindow, int aWidth, int aHeight) {
				width = aWidth;
				height = aHeight;
				hasResized = true;
			}
		};
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
	}
	
	public void closeWindow(){
		GLFW.glfwSetWindowShouldClose(window, true);
	}
	
	public void update(){
		hasResized = false;
		input.update();
		glfwPollEvents();
	}
	
	public boolean hasResized(){
		return hasResized;
	}
	
	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public void clearAndDestroy(){
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		// Terminate & Clear Error Callbacks
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setDimensions(int width, int height){
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setFullscreen(boolean full){
		this.fullScreen = full;
	}
	
	public boolean isFullscreen(){
		return this.fullScreen;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Input getInput(){
		return input;
	}
	
}
