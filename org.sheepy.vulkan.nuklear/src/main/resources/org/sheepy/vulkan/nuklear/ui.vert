#version 450
#extension GL_ARB_separate_shader_objects : enable

precision mediump float;

layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 TexCoord;
layout (location = 2) in vec4 Color;

layout (push_constant) uniform PushConstants {
	mat4 ProjMtx;
} pushConstants;

layout (location = 0) out vec2 fragUv;
layout (location = 1) out vec4 fragColor;

void main() {
	fragUv = TexCoord;
	fragColor = Color;
	gl_Position = pushConstants.ProjMtx * vec4(Position.xy, 0, 1);
	gl_Position.y = -gl_Position.y;
}
