package com.portrabbit.spellsurvival.render;

public class Timer {
	
	public static double getTime(){
		return (double)System.nanoTime() / (double)1000000000l;
	}
	
}
