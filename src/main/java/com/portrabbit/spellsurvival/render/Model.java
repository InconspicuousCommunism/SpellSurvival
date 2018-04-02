package com.portrabbit.spellsurvival.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	
	private int drawCount;
	private int vId;
	private int tId;
	private int iId;
	
	public Model(float[] verts, float[] texVerts, int[] indicies){
		drawCount = indicies.length;
		
		vId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glBufferData(GL_ARRAY_BUFFER, getBuffer(verts), GL_STATIC_DRAW);
		
		tId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glBufferData(GL_ARRAY_BUFFER, getBuffer(texVerts), GL_STATIC_DRAW);
		
		iId = glGenBuffers();
		IntBuffer buff = BufferUtils.createIntBuffer(indicies.length);
		buff.put(indicies);
		buff.flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buff, GL_STATIC_DRAW);
		
		//UNBIND
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void render(){
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		//UNBIND
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public void prepare(){
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
	}
	
	public void doRender(){
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
	}
	
	public void finish(){
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public static FloatBuffer getBuffer(float[] verts){
		FloatBuffer buff = BufferUtils.createFloatBuffer(verts.length);
		buff.put(verts);
		buff.flip();
		return buff;
	}
	
}
