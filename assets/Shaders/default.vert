#version 100

attribute vec4 a_position;
attribute vec2 a_texCoord;

varying vec2 v_texCoord;

uniform mat4 u_projTrans;

void main() {
    v_texCoord = a_texCoord;
    gl_Position = u_projTrans * a_position;
}
