package gui;

import java.awt.EventQueue;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import gui.screens.MainScreen;

public class MyServerGui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyServerGui window = new MyServerGui();
					window.setVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyServerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MainScreen window = new MainScreen();
		try {
			this.frame = window.getMainFrame();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setVisible() {
		this.frame.setVisible(true);
	}

}
