package com.portrabbit.spellsurvival.entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.collision.AABB;
import com.portrabbit.spellsurvival.gui.GUI;
import com.portrabbit.spellsurvival.gui.player.PlayerInventoryGUI;
import com.portrabbit.spellsurvival.inventory.Inventory;
import com.portrabbit.spellsurvival.inventory.Slot;
import com.portrabbit.spellsurvival.item.Item;
import com.portrabbit.spellsurvival.item.ItemEntity;
import com.portrabbit.spellsurvival.item.ItemStack;
import com.portrabbit.spellsurvival.render.Camera;
import com.portrabbit.spellsurvival.render.Input;
import com.portrabbit.spellsurvival.world.World;
import com.portrabbit.spellsurvival.world.tile.Tile;

public class PlayerEntity extends Entity{
	
	private static int TIME_BETWEEN_STAGES = 20;
	
	private Camera cam;
	private Inventory playerInv;
	private Inventory itemBar;
	private GUI openedGui;
	private int selectedSlot = 0;
	private int breakingStage = 0;
	private int stageCount;
	private float breakX, breakY;
	
	public PlayerEntity(float posX, float posY, Camera cam) {
		super(posX, posY, "character.png");
		this.cam = cam;
		boundingBox = new AABB(new Vector2f(transform.getPos().x, transform.getPos().y), new Vector2f(1f,1f));
		inAir = true;
		playerInv = new Inventory(48);
		itemBar = new Inventory(9);
		playerInv.setSlotStack(new ItemStack(Item.COAL), 0);
		playerInv.setSlotStack(new ItemStack(Item.GOLD_ORE), 1);
		playerInv.setSlotStack(new ItemStack(Item.IRON_ORE), 2);
		playerInv.setSlotStack(new ItemStack(Item.LEATHER), 3);
		playerInv.setSlotStack(new ItemStack(Item.PLYWOOD), 4);
		playerInv.setSlotStack(new ItemStack(Item.STICK), 5);
		playerInv.setSlotStack(new ItemStack(Item.STONE), 6);
		playerInv.setSlotStack(new ItemStack(Item.WOOD), 7);
		playerInv.setSlotStack(new ItemStack(Tile.FLOWER_GRASS, 100), 8);
		itemBar.setSlotStack(new ItemStack(Tile.FLOWER_GRASS, 100), 0);
		itemBar.setSlotStack(new ItemStack(Tile.DIRT, 100), 1);
		itemBar.setSlotStack(new ItemStack(Tile.GRASS, 1), 2);
		itemBar.setSlotStack(new ItemStack(Tile.STONE, 100), 3);
		itemBar.setSlotStack(new ItemStack(Tile.WATER, 100), 4);
		itemBar.setSlotStack(new ItemStack(Tile.WOOD, 100), 5);
		itemBar.setSlotStack(new ItemStack(Tile.WOOD, 100), 6);
		itemBar.setSlotStack(new ItemStack(Tile.WOOD, 100), 7);
		itemBar.setSlotStack(new ItemStack(Tile.WOOD, 100), 8);
	}

	@Override
	public void update(World world) {
		Input input = world.getInput();
		if(input.isKeyDown(GLFW_KEY_A))mX = .25f;
		if(input.isKeyDown(GLFW_KEY_D))mX = -.25f;
		if(input.isKeyDown(GLFW_KEY_SPACE) && !inAir)mY = .15f;
		
		for(int i = GLFW_KEY_1; i <= GLFW_KEY_9; i++){
			if(input.isKeyPressed(i)){
				this.selectedSlot = i - GLFW_KEY_1;
				break;
			}
		}
		
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
							this.playerInv.addToInventory(item.getItemStack());
							World.world.removeEntity(e);
						}
					}
				}
			}
		}
		
		cam.setPos(this.getTransformation().getPos().mul(-this.getTransformation().getScale().x, new Vector3f()));
		
		if(SpellSurvival.instance.getWindow().getInput().isKeyPressed(GLFW_KEY_I)){
			if(!isGuiOpened())
				openPlayerInventory();
			else closeCurrentGui();
		}
		
		if(!isGuiOpened()){
			Slot s = this.itemBar.slots[this.selectedSlot];
			if(input.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
				Vector2f tPos = getMousePosition();
				Vector2f tilePos = getHoveredTilePos();
				if(s.getStack() != null){
					s.getStack().getItem().onClickInWorld(tPos.x, tPos.y, 0, s.getStack());
				}else{
					if(World.world.getTileAt((int)tilePos.x, (int)tilePos.y) != null && World.world.getTileAt((int)tilePos.x, (int)tilePos.y).canBeBroken()){
						if(this.breakX != (int)tilePos.x || this.breakY != (int)tilePos.y){
							this.breakX = (int)tilePos.x;
							this.breakY = (int)tilePos.y;
							this.breakingStage = 0;
							this.stageCount = 0;
						}
						this.stageCount++;
						if(this.stageCount > TIME_BETWEEN_STAGES){
							this.stageCount = 0;
							this.breakingStage++;
							if(this.breakingStage == 4){
								this.breakingStage = -1;
								this.stageCount = 0;
								World.world.breakTile((int)breakX, (int)breakY);
							}
						}
					}else{
						this.breakingStage = -1;
						this.stageCount = 0;
					}
				}
			}
			itemBar.updateInventory();
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
	
	public void openPlayerInventory(){
		this.openedGui = new PlayerInventoryGUI(this.playerInv);
	}
	
	public void closeCurrentGui(){
		this.openedGui.close();
		this.openedGui = null;
	}
	
	public Inventory getItemBar(){
		return this.itemBar;
	}
	
	public int getSelectedSlot(){
		return this.selectedSlot;
	}
	
	public Vector2f getMousePosition(){
		Vector2f mPos = SpellSurvival.instance.getWindow().getInput().getMousePosition();
		return mPos.add(-SpellSurvival.instance.getWindow().getWidth()/2, -SpellSurvival.instance.getWindow().getHeight()/2).mul(1f, -1f);
	}
	
	public Vector2f getHoveredTilePos(){
		Vector2f mPos = getMousePosition();
		mPos.mul(1f / SpellSurvival.GAME_SCALE);
		mPos.add(this.getX(), this.getY());
		return new Vector2f(Math.round(mPos.x), Math.round(mPos.y));
	}
	
	public Tile getHoveredTile(){
		Vector2f tPos = getHoveredTilePos();
		return World.world.getTileAt((int)tPos.x, (int)tPos.y);
	}
	
	public int getTileBreakStage(){
		return this.breakingStage;
	}
	
	public float getBreakX(){
		return this.breakX;
	}
	
	public float getBreakY(){
		return this.breakY;
	}
	
}
