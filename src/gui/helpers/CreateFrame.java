package gui.helpers;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CreateFrame extends JFrame {

	private final String name = "MyHttpServer";
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Dimension frameSize = new Dimension(600, 390);

	public CreateFrame() {
		this.drawFrame();
	}

	private void drawFrame() {
		//center the window
		setBounds(screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2,
				frameSize.width, frameSize.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setName(name);
	}

	public static Dimension getScreenSize() {
		return screenSize;
	}

	public static Dimension getFrameSize() {
		return frameSize;
	}

}
