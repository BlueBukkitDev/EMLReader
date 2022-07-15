package dev.blue.eml;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Dimension dim;
	private Canvas canvas;
	
	public Window() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		canvas = new Canvas();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setPreferredSize(dim);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
}
