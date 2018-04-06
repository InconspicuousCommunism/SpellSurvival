package com.portrabbit.spellsurvival.entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.gui.GUI;
import com.portrabbit.spellsurvival.gui.player.PlayerInventoryGUI;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.item.Item;
import com.portrabbit.spellsurvival.item.ItemEntity;
import com.portrabbit.spellsurvival.item.ItemStack;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.world.World;

public class PlayerEntity extends Entity{

	private Camera cam;
	private Inventory playerInv;
	private GUI openedGui;
	
	public PlayerEntity(float posX, float posY, Camera cam) {
		super(posX, posY, "character.png");
		this.cam = cam;
		boundingBox = new AABB(new Vector2f(transform.getPos().x, transform.getPos().y), new Vector2f(1f,1f));
		inAir = true;
		playerInv = new Inventory(48);
		playerInv.setSlotStack(new ItemStack(Item.COAL), 0);
		playerInv.setSlotStack(new ItemStack(Item.GOLD_ORE), 1);
		playerInv.setSlotStack(new ItemStack(Item.IRON_ORE), 2);
		playerInv.setSlotStack(new ItemStack(Item.LEATHER), 3);
		playerInv.setSlotStack(new ItemStack(Item.PLYWOOD), 4);
		playerInv.setSlotStack(new ItemStack(Item.STICK), 5);
		playerInv.setSlotStack(new ItemStack(Item.STONE), 6);
		playerInv.setSlotStack(new ItemStack(Item.WOOD), 7);
	}

	@Override
	public void update(World world) {
		if(world.getInput().isKeyDown(GLFW_KEY_A))mX = .25f;
		if(world.getInput().isKeyDown(GLFW_KEY_D))mX = -.25f;
		if(world.getInput().isKeyDown(GLFW_KEY_SPACE) && !inAir)mY = .15f;
		super.update(world);
		
		collideWithWorld(world);
		
		if(this.playerInv.hasEmptySlot()){
			AABB box = this.getBoundingBox();
			for(Entity e : World.world.getEntityList()){
				if(e instanceof ItemEntity){
					ItemEntity item = (ItemEntity) e;
					AABB itemBox = e.getBoundingBox();
					if(item.canBePickedUp()){
						if(box.getCollision(itemBox).isIntersecting()){
							this.playerInv.addToNextSlot(item.getItemStack());
							World.world.removeEntity(e);
						}
					}
				}
			}
		}
		
		cam.setPos(this.getTransformation().getPos().mul(-this.getTransformation().getScale().x, new Vector3f()));
		
		if(SpellSurvival.instance.getWindow().getInput().isKeyPressed(GLFW_KEY_I)){
			if(!isGuiOpened())
				openInventory();
			else closeCurrentGui();
		}
	}
	
	@Override
	public void move(float posX, float posY) {
		super.move(posX, posY);
	}
	
	public boolean isGuiOpened(){
		return openedGui != null;
	}
	
	public void renderOpenedGui(Camera cam){
		openedGui.renderGUI(cam);
	}
	
	public void openInventory(){
		this.openedGui = new PlayerInventoryGUI(this.playerInv);
	}
	
	public void closeCurrentGui(){
		this.openedGui.close();
		this.openedGui = null;
	}

}
