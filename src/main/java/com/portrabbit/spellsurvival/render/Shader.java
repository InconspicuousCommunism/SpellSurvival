package com.portrabbit.spellsurvival.render;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {
	
	private int program;
	private int vShader;
	private int fShader;
	
	public Shader(String file){
		program = glCreateProgram();
		vShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vShader, readFile(file+".vert"));
		glCompileShader(vShader);
		if(glGetShaderi(vShader, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(vShader));
			System.exit(1);
		}
		fShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fShader, readFile(file+".frag"));
		glCompileShader(fShader);
		if(glGetShaderi(fShader, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(fShader));
			System.exit(1);
		}
		glAttachShader(program, vShader);
		glAttachShader(program, fShader);
		
		glBindAttribLocation(program, 0, "verticies");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		glValidateProgram(program);
	}
	
	public void setUniform(String name, int value){
		int loc = glGetUniformLocation(program, name);
		if(loc != -1){
			glUniform1i(loc, value);
		}
	}
	
	public void setUniform(String name, Matrix4f value){
		int loc = glGetUniformLocation(program, name);
		FloatBuffer buff = BufferUtils.createFloatBuffer(16);
		value.get(buff);
		if(loc != -1){
			glUniformMatrix4fv(loc, false, buff);
		}
	}
	
	public void setUniform(String name, float value){
		int loc = glGetUniformLocation(program, name);
		if(loc != -1){
			glUniform1f(loc, value);
		}else{
			System.err.println("Unable to find Uniform: " + name);
		}
	}
	
	public void bind(){
		glUseProgram(program);
	}
	
	private String readFile(String file){
		StringBuilder str = new StringBuilder();
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(new File("src/main/resources/shaders/"+file)));
			String ln;
			while((ln = br.readLine()) != null){
				str.append(ln);
				str.append("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return str.toString();
	}
	
}
