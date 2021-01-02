package gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gui.screens.MainScreen;
import server.errors.InvalidConfigurationException;
import server.errors.InvalidParameterException;

public class MyServerGui {

	private JFrame frame;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
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
	 * @wbp.parser.entryPoint
	 */
	public MyServerGui() {
		initialize();
		setIcon();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		MainScreen window = new MainScreen();
		try {
			this.frame = window.getMainFrame();
		} catch (NumberFormatException | InvalidConfigurationException | IOException | InvalidParameterException e) {
			// TODO Auto-generated catch block
			System.out.println("Error when apps started: " + e);
		}
	}
	
	private void setIcon() {
		ImageIcon icon = new ImageIcon("./assets/favicon.ico");
		this.frame.setIconImage(icon.getImage());
	}
	
	public void setVisible() {
		this.frame.setVisible(true);
	}

}
