package net.orangevertex.gl;

import javax.media.opengl.GL2;

public class GLUtil {
    public static int loadShader(GL2 gl, int type, String[] shaderCode) {
        int shader = gl.glCreateShader(type);
        gl.glShaderSource(shader, shaderCode.length, shaderCode, null);
        gl.glCompileShader(shader);
        return shader;
    }
    public static void checkGlError(GL2 gl, String glOperation) {
        int error;
        while ((error = gl.glGetError()) != GL2.GL_NO_ERROR) {
            System.out.println(glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
