package com.portrabbit.spellsurvival.gui;

import java.util.HashMap;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.entity.PlayerEntity;
import com.portrabbit.spellsurvival.entity.Transform;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.inventory.Slot;
import com.portrabbit.spellsurvival.item.ItemEntity;
import com.portrabbit.spellsurvival.item.ItemStack;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Input;
import com.portrabbit.spellsurvival.render.Texture;
import com.portrabbit.spellsurvival.render.gui.GUIRenderer;
import com.portrabbit.spellsurvival.render.item.InventoryItemRenderer;
import com.portrabbit.spellsurvival.world.World;

public class InventoryGUI extends GUI{
	
	protected Inventory inv;
	protected InventoryItemRenderer itemRenderer;
	private GUIRenderer render;
	private static final Texture hoverTexture = Texture.loadTexture(GUI.RESOURCE_LOCATION + "hover_image.png");
	private ItemStack heldItem;
	
	private HashMap<Integer, Vector3f> slotPos = new HashMap<Integer, Vector3f>();
	
	public InventoryGUI(Inventory inv) {
		this.inv = inv;
		itemRenderer = new InventoryItemRenderer();
	}
	
	@Override
	public void init() {}
	
	public void drawGuiLayer(Camera cam){}
	
	@Override
	public void renderGUI(Camera cam) {
		Input input = SpellSurvival.instance.getWindow().getInput();
		drawGuiLayer(cam);
		itemRenderer.bindShader(s);
		float mX, mY;
		Vector2f mPos = input.getMousePosition();
		mX = mPos.x - SpellSurvival.instance.getWindow().getWidth()/2;
		mY = -(mPos.y - SpellSurvival.instance.getWindow().getHeight()/2);
		boolean clicked = false;
		if(input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
			clicked = true;
		}
		boolean drop = false;
		if(input.isKeyPressed(GLFW.GLFW_KEY_Q)){
			drop = true;
		}
		Transform trans = new Transform().setScale(new Vector3f(1f,1f,1f)).setAfterScale(new Vector3f(SpellSurvival.GAME_SCALE * 2.5f, SpellSurvival.GAME_SCALE * 2.5f, 1f));
		for(Slot s : inv.slots){
			if(s != null){
				Vector3f pos = slotPos.getOrDefault(s.getId(), new Vector3f(0f,0f,0f));
				trans.setPos(pos);
				
				if(mX > pos.x - 40 && mX < pos.x + 40){
					if(mY > pos.y - 40 && mY < pos.y + 40){
						itemRenderer.render(hoverTexture, this.s, trans, cam);
						if(clicked){
							ItemStack temp = this.heldItem;
							this.heldItem = s.getStack();
							s.setStack(temp);
						}
						if(drop){
							dropItem(s.getStack());
							s.setStack(null);
						}
					}
				}
				if(s.getStack() != null){
					itemRenderer.render(s.getStack().getItem(), this.s, trans, cam);
				}
			}
		}
		if(heldItem != null){
			trans.setPos(new Vector3f(mX, mY, 0f));
			itemRenderer.render(heldItem.getItem(), this.s, trans, cam);
		}
	}
	
	public void dropItem(ItemStack item){
		PlayerEntity player = SpellSurvival.instance.getPlayer();
		ItemEntity e = new ItemEntity(player.getX(), player.getY(), item);
		e.onDrop();
		World.world.addEntity(e);
	}
	
	public void renderGUI(Texture texture, float xPos, float yPos, float width, float height, float scale, Camera cam){
		if(render == null)
			render = new GUIRenderer(width, height);
		render.render(texture, s, new Transform().setScale(new Vector3f(1f,1f,1f)).setPos(new Vector3f(xPos, yPos, 0)).setAfterScale(new Vector3f(SpellSurvival.GAME_SCALE * scale, SpellSurvival.GAME_SCALE * scale, 1f)), cam);
	}
	
	public void registerSlot(int id, float xPos, float yPos){
		slotPos.put(id, new Vector3f(xPos, yPos, 0));
	}
	
}
