package dev.blue.eml;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import dev.blue.eml.interpreter.ViewLoader;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private App app;
	
	private Dimension dim;
	private Canvas canvas;
	
	public Window(App app) {
		this.app = app;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.canvas = new Canvas();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.canvas.setPreferredSize(dim);
		this.setPreferredSize(dim);
		this.add(canvas);
		this.pack();
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setVisible(true);
		try {
			new ViewLoader(new File("C:\\Users\\be\\Desktop\\test.eml")).loadView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
        	app.stop();
            System.exit(0);
        }
    }
}
