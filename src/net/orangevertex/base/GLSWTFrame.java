package net.orangevertex.base;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import com.jogamp.opengl.util.Animator;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

public class GLSWTFrame extends Composite {

    private GLRenderer glR;
    private GLCanvas glcanvas;
    private Frame glframe;
    private GLCapabilities glcapabilities;

    public GLSWTFrame(Composite parent, int style) {
        super(parent, style);
        glframe = SWT_AWT.new_Frame(this);
        GLProfile glprofile = GLProfile.getDefault();
        glcapabilities = new GLCapabilities(glprofile);
        glcanvas = new GLCanvas(glcapabilities);
        glframe.add(glcanvas);
        glR = new GLRenderer();
        glcanvas.addGLEventListener(glR);
        glframe.addWindowListener(glWindowListener);
        glcanvas.addKeyListener(glKeyListener);
        Animator animator = new Animator(glcanvas);
        animator.start();
    }

    private WindowAdapter glWindowListener = new WindowAdapter() {
        public void windowClosing(WindowEvent windowevent) {
            glframe.dispose();
        }
    };
    private KeyListener glKeyListener = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println("Key pressed: " + e.getKeyCode());
//            glR.pan(.01f, .01f);
            glR.zoom(glR.zoom -1 );
            switch (e.getKeyCode()) {
                case 37:
                    break;
                case 38:
                    break;
                case 39:
                    break;
                case 32:
                    break;
                case 27:
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    };

}
