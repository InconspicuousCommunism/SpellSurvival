package com.portrabbit.spellsurvival.gui.player;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.entity.PlayerEntity;
import com.portrabbit.spellsurvival.gui.GUIElement;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.inventory.Slot;
import com.portrabbit.spellsurvival.item.TileItem;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Input;
import com.portrabbit.spellsurvival.render.Shader;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.item.InventoryItemRenderer;
import com.portrabbit.spellsurvival.world.World;
import com.portrabbit.spellsurvival.world.tile.Tile;

public class PlayerBars extends GUIElement {
	
	
	private Texture itemBarTexture;
	private Texture spellBarTexture;
	private Texture selectTexture;
	private Texture outlineTexture;
	private Texture[] breakingTexture;
	private int secondModel = -1;
	private int squareModel = -1;
	private Inventory itemBar;
	private InventoryItemRenderer itemRenderer;
	
	//Item Hot Bar & Spells Bar
	public PlayerBars(String loc) {
		super(null, 0, -400, 631, 78);
		itemBarTexture = Texture.loadTexture(loc + "item_bar.png");
		spellBarTexture = Texture.loadTexture(loc + "spell_bar.png");	
		selectTexture = Texture.loadTexture(loc + "item_bar_select.png");
		outlineTexture = Texture.loadTexture(Tile.RESOURCE_LOCATION + "tile_outline.png");
		breakingTexture = new Texture[4];
		breakingTexture[0] = Texture.loadTexture(Tile.RESOURCE_LOCATION + "broken_1.png");
		breakingTexture[1] = Texture.loadTexture(Tile.RESOURCE_LOCATION + "broken_2.png");
		breakingTexture[2] = Texture.loadTexture(Tile.RESOURCE_LOCATION + "broken_3.png");
		breakingTexture[3] = Texture.loadTexture(Tile.RESOURCE_LOCATION + "broken_4.png");
		secondModel = this.renderer.addExtraModel(78, 354);
		squareModel = this.renderer.addExtraModel(64f, 64f);
		itemRenderer = new InventoryItemRenderer();
	}
	
	@Override
	public void renderElement(Shader s, Camera cam) {
		if(this.itemBar == null)this.itemBar = SpellSurvival.instance.getPlayer().getItemBar();
		x = 0;
		y = -700;
		trans.setPos(new Vector3f(x,y,0)).setAfterScale(DEFAULT_AFTER_SCALE).setScale(DEFAULT_SCALE);
		renderer.render(this, s, trans, cam);
		x = -453.5f;
		y = -700;
		for(Slot slot : this.itemBar.slots){
			if(slot.getStack() != null){
				trans.setPos(new Vector3f(x,y,0)).setAfterScale(new Vector3f(3 * SpellSurvival.MENU_SCALE, 3 * SpellSurvival.MENU_SCALE, 1f));
				itemRenderer.render(slot.getStack().getItem(), s, trans, cam);
			}
			x+=113.5f;
		}
		x = 675;
		y = 000;
		trans.setPos(new Vector3f(x,y,0)).setAfterScale(DEFAULT_AFTER_SCALE);
		renderer.render(spellBarTexture, s, trans, cam, secondModel);
		x = -453.5f + 113.5f * SpellSurvival.instance.getPlayer().getSelectedSlot();
		y = -700f;
		trans.setPos(new Vector3f(x,y,0)).setAfterScale(new Vector3f(1.8f * SpellSurvival.MENU_SCALE, 1.8f * SpellSurvival.MENU_SCALE, 1f));
		renderer.render(selectTexture, s, trans, cam, squareModel);
		PlayerEntity player = SpellSurvival.instance.getPlayer();
		Slot slot = this.itemBar.slots[player.getSelectedSlot()];
		if(s != null && slot.getStack() != null && slot.getStack().getItem() instanceof TileItem){
			Vector2f mPos = player.getHoveredTilePos();
			int pX = (int)mPos.x;
			int pY = (int)mPos.y;
			if(World.world.getTileAt(pX, pY) != null)
				World.world.getTileRenderer().renderOverTile(outlineTexture, pX, pY, s, World.world, World.world.getWorldCamera());
		}else if(s != null){
			int breakStage = player.getTileBreakStage();
			if(breakStage >= 0 && breakStage < 4){
				World.world.getTileRenderer().renderOverTile(breakingTexture[breakStage], player.getBreakX(), player.getBreakY(), s, World.world, World.world.getWorldCamera());
			}
		}
	}
	
	@Override
	public Texture getTexture() {
		return itemBarTexture;
	}
	
}
