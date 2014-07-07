package net.orangevertex.base;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import net.orangevertex.gl.GLUtil;
import net.orangevertex.gl.objects.GLAxes;

public class GLRenderer implements GLEventListener {
    private GLAxes glx;
    private float[] mvp, pMatrix;
    private boolean perspective = false;
    private int width, height;
    float right, down, zoom = 2f;
    private float ratio = 1f, iratio = 1f;

    public GLRenderer() {
        glx = new GLAxes(2, .5f);
        mvp = new float[] {
//@formatter:off
              1, 0, 0, 0,
              0, 1, 0, 0,
              0, 0, 1, 0,
              0, 0, 0, 1
//@formatter:on
        };
        pMatrix = new float[16];
        for (int i = 0; i < 16; i++)
            pMatrix[i] = 0;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glx.build(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL2.GL_BLEND);
        GLUtil.checkGlError(gl, "pre-draw error");
        glx.draw(gl, mvp);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
        ratio = width / (float) height;
        iratio = height / (float) width;
        float near = 0.001f, far = 100f;
        float left = 0f, right = 1f;
        float top = 0f, bottom = 1f;
        bottom = 1f / ratio;
        pMatrix[0] = 2.0f / (right - left);
        pMatrix[5] = 2.0f / (top - bottom);
        pMatrix[10] = 2.0f / (far - near);
        pMatrix[12] = -(right + left) / (right - left);
        pMatrix[13] = -(top + bottom) / (top - bottom);
        pMatrix[14] = -(far + near) / (far - near);
        pMatrix[15] = 1.0f;
        System.out.println('a');
        calcMVP();
    }

    void calcMVP() {
        mvp = MatrixMath.lookAt(new float[] { right, -down, 1 }, new float[] {
                right, -down, 0f }, new float[] { 0.0f, -1.0f, 0.0f });
        mvp[14] -= zoom;
        System.out.println(mvp[14]);
        mvp = MatrixMath.apply(mvp, pMatrix);
        // mvp = MatrixMath.rotateZ(mvp, planRotation);
    }

    void pan(float x, float y) {
        right += x;
        down -= y;
        calcMVP();
    }

    public void zoom(float z) {
        zoom = z;
        calcMVP();
    }

    public void addZoom(float d) {
        zoom += d;
        calcMVP();
    }
}
