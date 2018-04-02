package com.portrabbit.spellsurvival.gui;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.SpellSurvival.GameState;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.world.World;

public class MainMenu extends GUI{
	
	private GUIButton play;
	private GUIButton quit;
	
	@Override
	public void init() {
		play = new GUIButton(Texture.loadTexture(GUI.RESOURCE_LOCATION + "play_button.png"), 0, 100, 300, 100);
		quit = new GUIButton(Texture.loadTexture(GUI.RESOURCE_LOCATION + "quit_button.png"), 0, -100, 300, 100);
		
		addElement(play);
		addElement(quit);
	}
	
	@Override
	public void buttonClicked(int id) {
		super.buttonClicked(id);
		if(id == play.getId()){
			SpellSurvival.instance.setGameState(GameState.IN_WORLD);
		}else if(id == quit.getId()){
			SpellSurvival.instance.getWindow().closeWindow();
		}
	}
	
}
