#version 120

attribute vec2 verticies;
attribute vec2 textures;

varying vec2 texCoords;

uniform mat4 projection;

void main() {
	texCoords = textures;
	gl_Position = projection * vec4(verticies, 0, 1);
}