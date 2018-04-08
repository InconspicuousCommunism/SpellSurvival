package com.portrabbit.spellsurvival.item;

import com.portrabbit.spellsurvival.SpellSurvival;
import com.portrabbit.spellsurvival.world.World;
import com.portrabbit.spellsurvival.world.tile.Tile;

public class TileItem extends Item{
	
	private Tile tile;
	
	public TileItem(Tile tile) {
		super(tile.getTexture());
		this.tile = tile;
	}
	
	@Override
	public void onClickInWorld(float mX, float mY, int type, ItemStack stack) {
		float pX = SpellSurvival.instance.getPlayer().getX(), pY = SpellSurvival.instance.getPlayer().getY();
		int x = Math.round((mX / SpellSurvival.GAME_SCALE + pX)), y = Math.round((mY / SpellSurvival.GAME_SCALE + pY));
		System.out.println(mX / SpellSurvival.GAME_SCALE);
		if(World.world.getTileAt(x, y) == null && (Math.abs(mX/SpellSurvival.GAME_SCALE) > 1f || Math.abs(mY/SpellSurvival.GAME_SCALE) > 1f)){
			World.world.setTile(x, y, this.tile);
			stack.decreaseStack(1);
		}
		super.onClickInWorld(mX, mY, type, stack);
	}

}
 