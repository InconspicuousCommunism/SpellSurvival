package com.portrabbit.spellsurvival;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDepthRange;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import com.portrabbit.spellsurvival.entity.PlayerEntity;
import com.portrabbit.spellsurvival.gui.MainMenu;
import com.portrabbit.spellsurvival.gui.player.PlayerGUI;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Timer;
import com.portrabbit.spellsurvival.render.Window;
import com.portrabbit.spellsurvival.world.World;

public class SpellSurvival {
	
	public static SpellSurvival instance;
	
	public static final int GAME_SCALE = 32;
	public static final double FRAME_LIMIT = 1/120.0;
	
	private GameState state;
	
	private MainMenu mainMenu;
	private PlayerGUI playerGUI;
	private PlayerEntity player;
	
	private Window window;
	
	public SpellSurvival() {
		state = GameState.MAIN_MENU;
	}
	
	public void setGameState(GameState state){
		GameState oldState = this.state;
		this.state = state;
		stateSwapped(oldState);
	}
	
	public Window getWindow(){
		return window;
	}
	
	public static void main(String[] args){
		instance = new SpellSurvival();
		instance.run();
	}
	
	public void run(){
		System.out.println(Version.getVersion());
		
		init();
		loop();
		
		window.clearAndDestroy();
	}
	
	private void init(){
		
		//Init GLFW 
		if(!GLFW.glfwInit()) throw new IllegalStateException("Unable to load GLFW");
		
		//Configure
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		//Start Window
		window = new Window("Spell Survival");
		window.setDimensions(1600, 1600);
		window.initWindow();
		
		//V-sync
		GLFW.glfwSwapInterval(1);
	}
	
	private void loop(){
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDepthFunc(GL_LEQUAL);
		//Set Color
		glClearColor(0f, 0f, 0f, 0f);
		
		
		double time = Timer.getTime();
		double timePassed = 0;
		int fps = 0;
		double frameTime = 0;
		
		//World & Player Setup
		World.world = new World(window.getWidth(), window.getHeight(),GAME_SCALE, window.getInput());
		World.world.genWorld();
		player = new PlayerEntity(0,0, World.world.getWorldCamera());
		player.move(0, 12);
		playerGUI = new PlayerGUI();
		
		//Main Menu Setup
		Camera menuCam = new Camera(window.getWidth(), window.getHeight());
		menuCam.setPos(new Vector3f(0,0,0));
		mainMenu = new MainMenu();
		mainMenu.init();
		
		//Run until close
		while(!window.shouldClose()){
			
			double t = Timer.getTime();
			double secs = t - time;
			time = t;
			timePassed += secs;
			frameTime += secs;
			boolean doRender = false;
			while(timePassed >= FRAME_LIMIT){
				if(window.hasResized()){
					World.world.getWorldCamera().setProjection(window.getWidth(),window.getHeight());
					menuCam.setProjection(window.getWidth(),window.getHeight());
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				timePassed -= FRAME_LIMIT;
				if(frameTime >= 1.0){
					frameTime = 0;
					System.out.println("FPS: " + fps);
					fps = 0;
				}
				doRender = true;
			}
			if(doRender){
				glDepthRange(0.0f, 1.0f);
				glClearDepth(1.0f);
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				
				switch(state){
				case IN_WORLD:
					World.world.renderWorld(player.getX(), player.getY());
					player.update(World.world);
					player.renderEntity(World.world);
					playerGUI.renderGUI(menuCam);
					break;
				case MAIN_MENU:
					mainMenu.renderGUI(menuCam);
					mainMenu.updateGUI();
					break;
				}
				
				//Swap color
				window.swapBuffers();
				//Update
				window.update();
				
				fps++;
				doRender = false;
			}
		}
	}
	
	private void stateSwapped(GameState oldState){
		switch(oldState){
		case IN_WORLD:
			playerGUI.close();
			break;
		case MAIN_MENU:
			mainMenu.close();
			break;
		}
		switch(state){
		case IN_WORLD:
			playerGUI.init();
			player.setPosition(player.getX(), World.world.getTopTile((int)player.getX()) + 2);
			break;
		case MAIN_MENU:
			mainMenu.init();
			break;
		}
	}
	
	public enum GameState {
		MAIN_MENU, IN_WORLD
	}
	
}
