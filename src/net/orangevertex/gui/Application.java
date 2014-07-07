package net.orangevertex.gui;

import net.orangevertex.base.GLSWTFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application {

    public static void main(String[] args) {
        new Application();
    }

    Application() {
        Display display = new Display();

        Shell shell = new Shell(display);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        shell.setLayout(gridLayout);
        shell.setSize(1280, 800);
        GLSWTFrame glframe = new GLSWTFrame(shell, SWT.EMBEDDED);
        glframe.setLayout(new GridLayout());
        GridData glgriddata = new GridData();
        glgriddata.horizontalAlignment = GridData.FILL;
        glgriddata.verticalAlignment = GridData.FILL;
        glgriddata.grabExcessHorizontalSpace = true;
        glgriddata.grabExcessVerticalSpace = true;
        glframe.setLayoutData(glgriddata);
        

        shell.open();
        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
        System.exit(0);
    }
}
