package com.portrabbit.spellsurvival.world;

import java.util.HashMap;
import java.util.Random;

import com.portrabbit.spellsurvival.world.tile.Tile;

public class WorldGen {
	
	private Random random;
	private long seed;
	
	private HashMap<Integer, Integer> worldHeights;
	
	private static final int WATER_LEVEL = 50;
	
	public WorldGen(long seed) {
		this.random = new Random();
		this.seed = seed;
		worldHeights = new HashMap<Integer, Integer>();
	}
	
	public void genWorld(World w){
		for(int i = -(w.getChunkSize())*8; i < (w.getChunkSize())*8; i++){
			float f = genHeight(i);
			if(f < 0) f*=1f;
			int h = (int) (f*12) + WATER_LEVEL;
			World.world.setTile(i, h, Tile.GRASS);
			if(f < 0){
				for(int j = h+1; j < 6; j++){
					World.world.setTile(i, j, Tile.WATER);
				}
			}
			for(int j = h-1; j >= Math.max(h-3,0); j--){
				World.world.setTile(i, j, Tile.DIRT);
			}
			for(int j = h-3; j >= 0; j--){
				World.world.setTile(i, j, Tile.STONE);
			}
			worldHeights.put(i, h);
		}
		genTrees(w);
		
	}
	
	public float genHeight(int x){
		return getInterpolatedNoise(x/64f);
	}
	
	private float getInterpolatedNoise(float x){
		int intX = (int)x;
		float fracX = x - intX;
		
		float[] v = new float[8];
		for(int i = 0; i < v.length; i++){
			int b = i - v.length/2;
			v[i] = getSmoothNoise(intX + b);
		}
		float l = v.length/2;
		while(l >= 1){
			for(int i = 0; i < l; i++){
				float a = v[i*2];
				float b = v[i*2+1];
				v[i] = interpolate(a,b,fracX);
			}
			l/=2f;
		}
		
		return v[0];
	}
	
	private float interpolate(float a, float b, float blend){
		double t = blend * Math.PI;
		float f = (float)(1f - Math.sin(t)) * .5f;
		return a * (1f-f) + b*f;
	}
	
	private float getSmoothNoise(int x){
		float sides = (getNoise(x-1) + getNoise(x+1)) / 4f;
		float center = getNoise(x) / 2f;
		return  sides + center;
	}
	
	private float getNoise(int x){
		random.setSeed(x * (x < 0 ? 58347 : 64513) * seed);
		return random.nextFloat() * 2f - 1f;
	}
	
	private void genTrees(World w){
		for(int i = -(w.getChunkSize()/2)*16; i < (w.getChunkSize()/2)*16; i++){
			int h = 0;
			if((h=worldHeights.get(i)) > WATER_LEVEL){
				float a = getTreeChance(i);
				if(a > 7f/8f){
					a-= 7f/8f;
					a = a * 8f * 5 + 3;
					for(int b = h+1; b < h + a+1; b++){
						w.setTile(i, b, Tile.WOOD);
					}
				}
			}
		}
	}
	
	private float getTreeChance(int x){
		random.setSeed(x * ( x < 0 ? 15321052 : 1432521) * seed);
		return (random.nextFloat() * 2f - 1f);
	}
	
}
