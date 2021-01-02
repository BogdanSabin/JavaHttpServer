package gui.helpers;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.configuration.PersistentConfiguration;
import server.errors.InvalidParameterException;

@SuppressWarnings("serial")
public class JFilePicker extends JPanel {
	@SuppressWarnings("unused")
	private String textFieldLabel;
	@SuppressWarnings("unused")
	private String buttonLabel;

	private JLabel label;
	private JTextField textField;
	private JButton button;

	private JFileChooser fileChooser;

	private int mode;
	public static final int MODE_OPEN = 1;
	public static final int MODE_SAVE = 2;

	private PersistentConfiguration config;
	private String key;

	public JFilePicker(String textFieldLabel, String buttonLabel, String title, Font font, int mode,
			PersistentConfiguration config, String key) {
		this.textFieldLabel = textFieldLabel;
		this.buttonLabel = buttonLabel;
		this.config = config;
		this.key = key;
		File workingDirectory = new File(System.getProperty("user.dir"));
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(workingDirectory);
		fileChooser.setFileSelectionMode(mode);
		fileChooser.setDialogTitle(title);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// creates the GUI
		label = new JLabel(textFieldLabel);
		label.setFont(font);

		textField = new JTextField(30);
		button = new JButton(buttonLabel);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				buttonActionPerformed(evt);
			}
		});

		add(label);
		add(textField);
		add(button);

	}

	private void buttonActionPerformed(ActionEvent evt) {
		if (mode == MODE_OPEN)
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				savePath();

			else if (mode == MODE_SAVE)
				if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
					savePath();

	}

	private void savePath() {
		try {
			this.config.setKey(this.key, fileChooser.getSelectedFile().getAbsolutePath());
			textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		} catch (InvalidParameterException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while saving into storage: " + e);
		}
	}

	public void setFilePath(String filePath) {
		this.textField.setText(filePath);
	}

	public void disable() {
		this.textField.setEditable(false);
		this.button.setEnabled(false);
	}

	public void enable() {
		this.textField.setEditable(true);
		this.button.setEnabled(true);
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getSelectedFilePath() {
		return textField.getText();
	}

	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}

}