package com.portrabbit.spellsurvival.gui.player;

import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.gui.GUI;
import com.portrabbit.spellsurvival.render.Texture;

public class PlayerGUI extends GUI{
	
	@Override
	public void init() {
		addElement(new Meters(GUI.RESOURCE_LOCATION,-675).setAfterScale(new Vector3f(2f * SpellSurvival.MENU_SCALE,2f * SpellSurvival.MENU_SCALE,1f)));
		addElement(new PlayerBars(GUI.RESOURCE_LOCATION).setAfterScale(new Vector3f(2f * SpellSurvival.MENU_SCALE,2f * SpellSurvival.MENU_SCALE,1f)));
		addElement(new Minimap(Texture.loadTexture(GUI.RESOURCE_LOCATION + "minimap.png"),-575,575,200,200).setAfterScale(new Vector3f(6 * SpellSurvival.MENU_SCALE,6 * SpellSurvival.MENU_SCALE,1f)));
	}
	
}
