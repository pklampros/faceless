package net.orangevertex.gl.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

import net.orangevertex.gl.GLUtil;

public class GLAxes {
    private int mProgram;
    private int vPositionHandle;
    private int vColourHandle;
    private int mMVPMatrixHandle;
    private final int COORDS_PER_VERTEX = 3;
    private final int COLOURS_PER_VERTEX = 3;
    private float[] vertexData;
    private int vertexCount;
    private int vertexStride;
    private int[] aiVertexBufferIndices = new int[] { -1 };
    private final String[] vertexShaderCode = { //
    "uniform mat4 uMVPMatrix;", //
            "attribute vec4 vPosition;", //
            "attribute vec4 vColour;", //
            "varying vec4 aColor;", //
            "void main() {", //
            "  gl_Position = vPosition * uMVPMatrix;", //
            "  aColor = vColour;", //
            "}" };

    private final String[] fragmentShaderCode = { //
    "varying vec4 aColor;", //
            "void main() {", //
            "  gl_FragColor = aColor;", //
            "}" };

    public GLAxes(int dimensions, float size) {
        if (dimensions == 3) {
            vertexData = new float[] { 
        //interleaved coordinates and colours
                //@formatter:off
                1f,     0f,     0f,
                0f,     0f,     0f,
                1f,     0f,     0f,
                size,   0f,     0f,
                0f,     1f,     0f,
                0f,     0f,     0f,
                0f,     1f,     0f,
                0f,     size,   0f,
                0f,     0f,     1f,
                0f,     0f,     0f,
                0f,     0f,     1f,
                0f,     0f,     size
                //@formatter:on
            };
        } else {
            vertexData = new float[] { 
                    //interleaved coordinates and colours
                    //@formatter:off
                1f,     0f,     0f,
                0f,     0f,     0f,
                1f,     0f,     0f,
                size,   0f,     0f,
                0f,     1f,     0f,
                0f,     0f,     0f,
                0f,     1f,     0f,
                0f,     size,   0f,
            };
                    //@formatter:on
        }
    }

    public void build(GL2 gl) {

        int vertexShader = loadShader(gl, GL2.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(gl, GL2.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = gl.glCreateProgram();

        gl.glAttachShader(mProgram, vertexShader);
        gl.glAttachShader(mProgram, fragmentShader);

        gl.glLinkProgram(mProgram);
        // gl.glUseProgram(mProgram);

        vPositionHandle = gl.glGetAttribLocation(mProgram, "vPosition");
        vColourHandle = gl.glGetAttribLocation(mProgram, "vColour");
        mMVPMatrixHandle = gl.glGetUniformLocation(mProgram, "uMVPMatrix");

        vertexCount = vertexData.length
                / (COORDS_PER_VERTEX + COLOURS_PER_VERTEX);
        vertexStride = (COORDS_PER_VERTEX + COLOURS_PER_VERTEX)
                * Buffers.SIZEOF_FLOAT;
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexData.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexData);
        vertexBuffer.position(0);

        gl.glGenBuffers(1, aiVertexBufferIndices, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, aiVertexBufferIndices[0]);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, vertexData.length
                * Buffers.SIZEOF_FLOAT, vertexBuffer, GL2.GL_STATIC_DRAW);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        vertexBuffer = null;
        vertexData = null;
    }

    public void draw(GL2 gl, float[] mvp) {
        gl.glUseProgram(mProgram);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, aiVertexBufferIndices[0]);

        gl.glEnableVertexAttribArray(vColourHandle);
        gl.glVertexAttribPointer(vColourHandle, 3, GL2.GL_FLOAT, false,
                vertexStride, 0);
        GLUtil.checkGlError(gl, "glVertexAttribPointer(vColourHandle)");

        gl.glEnableVertexAttribArray(vPositionHandle);
        gl.glVertexAttribPointer(vPositionHandle, 3, GL2.GL_FLOAT, false,
                vertexStride, COORDS_PER_VERTEX * Buffers.SIZEOF_FLOAT);
        GLUtil.checkGlError(gl, "glVertexAttribPointer(vPositionHandle)");

        gl.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvp, 0);
        GLUtil.checkGlError(gl, "glUniformMatrix4fv");

        gl.glDrawArrays(GL2.GL_LINES, 0, vertexCount);
        gl.glDisableVertexAttribArray(vPositionHandle);
        gl.glDisableVertexAttribArray(vColourHandle);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
    }

    public int loadShader(GL2 gl, int type, String[] shaderCode) {
        int shader = gl.glCreateShader(type);
        gl.glShaderSource(shader, shaderCode.length, shaderCode, null);
        gl.glCompileShader(shader);
        return shader;
    }
}
