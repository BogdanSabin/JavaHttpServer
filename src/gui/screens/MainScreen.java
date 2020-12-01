package gui.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import gui.helpers.CreateFrame;
import gui.helpers.JFilePicker;
import gui.helpers.JTextFieldLimit;
import server.ServerState;

public class MainScreen {

	private JFrame frame;
	private final String TITLE = "My Java Http Server";
	private ServerState state = ServerState.STOPPED;
	private final Border compoundBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private final Font textFont = new Font("Verdana", 0, 12);
	private final Font borderFont = new Font("Verdana", Font.PLAIN, 9);
	private JLabel serverStatus;
	private JLabel serverAddress;
	private JLabel serverPort;
	private JCheckBox goMaintenance;
	private JFormattedTextField textFieldPort;
	private JFilePicker textFieldRootDirectory;
	private JFilePicker textFieldMaintenanceDirectory;

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new CreateFrame();
	}

	public JFrame getMainFrame() {
		JPanel serverInfo = new JPanel();
		JPanel serverControl = new JPanel();
		JPanel serverConfiguration = new JPanel();
		JPanel centralPanel = new JPanel();
		ImageIcon start = createImageIcon("../assets/start.png");
		ImageIcon stop = createImageIcon("../assets/stop.png");

		this.serverStatus = this.createStaticLabel("not running", this.textFont);
		this.serverAddress = this.createStaticLabel("not running", this.textFont);
		this.serverPort = this.createStaticLabel("not running", this.textFont);

		NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setGroupingUsed(false);
		textFieldPort = new JFormattedTextField(integerFieldFormatter);
		textFieldPort.setColumns(5);
		textFieldPort.setDocument(new JTextFieldLimit(5));
		
		textFieldRootDirectory = new JFilePicker("Web root directory", "...");
		textFieldRootDirectory.setMode(JFilePicker.MODE_SAVE);
		
		textFieldMaintenanceDirectory = new JFilePicker("Maintenance directory", "...");
		textFieldMaintenanceDirectory.setMode(JFilePicker.MODE_SAVE);

		//SERVER INFO PANEL
		serverInfo.setLayout(new GridBagLayout());
		this.setDefaultBorderToPanel(serverInfo, "Server info");
		GridBagConstraints gbcinfo = new GridBagConstraints();

		gbcinfo.fill = GridBagConstraints.HORIZONTAL;
		gbcinfo.gridx = 0;
		gbcinfo.gridy = 0;
		gbcinfo.insets = new Insets(0, 5, 0, 0);
		gbcinfo.weightx = 1;
		serverInfo.add(this.createStaticLabel("Server status: ", this.textFont), gbcinfo);

		gbcinfo.fill = GridBagConstraints.HORIZONTAL;
		gbcinfo.gridwidth = 3;
		gbcinfo.gridx = 1;
		gbcinfo.gridy = 0;
		gbcinfo.insets = new Insets(0, 0, 0, 15);
		gbcinfo.weightx = 1;
		serverInfo.add(serverStatus, gbcinfo);

		gbcinfo.fill = GridBagConstraints.HORIZONTAL;
		gbcinfo.gridwidth = 1;
		gbcinfo.gridx = 0;
		gbcinfo.gridy = 1;
		gbcinfo.insets = new Insets(0, 5, 0, 0);
		gbcinfo.weightx = 1;
		serverInfo.add(this.createStaticLabel("Server address: ", this.textFont), gbcinfo);

		gbcinfo.fill = GridBagConstraints.HORIZONTAL;
		gbcinfo.gridwidth = 3;
		gbcinfo.gridx = 1;
		gbcinfo.gridy = 1;
		gbcinfo.insets = new Insets(5, 0, 0, 15);
		gbcinfo.weightx = 1;
		serverInfo.add(serverAddress, gbcinfo);

		gbcinfo.gridwidth = 1;
		gbcinfo.gridx = 0;
		gbcinfo.gridy = 2;
		gbcinfo.insets = new Insets(0, 5, 0, 0);
		gbcinfo.weightx = 1;
		serverInfo.add(this.createStaticLabel("Server listening port: ", this.textFont), gbcinfo);

		gbcinfo.fill = GridBagConstraints.HORIZONTAL;
		gbcinfo.gridwidth = 3;
		gbcinfo.gridx = 1;
		gbcinfo.gridy = 2;
		gbcinfo.weightx = 1;
		gbcinfo.insets = new Insets(5, 0, 0, 15);
		serverInfo.add(serverPort, gbcinfo);

		//SERVER CONTROL PANEL
		serverControl.setLayout(new GridBagLayout());
		this.setDefaultBorderToPanel(serverControl, "Server control");
		GridBagConstraints gbccontrol = new GridBagConstraints();

		gbccontrol.gridx = 0;
		gbccontrol.gridy = 0;
		JButton buttonStartServer = new JButton("Start server", start);
		buttonStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == ServerState.STOPPED) {
					state = ServerState.RUNNING;
					setFrameTitle("Running");
					goMaintenance.setEnabled(true);
					serverStatus.setText("running...");
					serverAddress.setText("localhost");
					serverPort.setText("8081");
					buttonStartServer.setIcon(stop);
					buttonStartServer.setText("Stop server");
				} else if (state == ServerState.RUNNING || state == ServerState.MAINTENANCE) {
					state = ServerState.STOPPED;
					setFrameTitle("Stopped");
					goMaintenance.setEnabled(false);
					goMaintenance.setSelected(false);
					serverStatus.setText("not running");
					serverAddress.setText("not running");
					serverPort.setText("not running");
					buttonStartServer.setIcon(start);
					buttonStartServer.setText("Start server");
				}
			}
		});
		serverControl.add(buttonStartServer, gbccontrol);

		gbccontrol.gridx = 0;
		gbccontrol.gridy = 1;
		goMaintenance = new JCheckBox("Switch to maintenance mode");
		goMaintenance.setEnabled(false);
		goMaintenance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (goMaintenance.isSelected()) {
					setFrameTitle("Maintenance");
					state = ServerState.MAINTENANCE;
					serverStatus.setText("maintenance");
				} else {
					setFrameTitle("Running");
					state = ServerState.RUNNING;
					serverStatus.setText("running");
				}
			}
		});
		serverControl.add(goMaintenance, gbccontrol);

		//SERVER CONFIGURATION PANEL
		serverControl.setLayout(new GridBagLayout());
		this.setDefaultBorderToPanel(serverConfiguration, "Server configuration");
		GridBagConstraints gbccofiguration = new GridBagConstraints();
		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridx = 0;
		gbccofiguration.gridy = 0;
		gbccofiguration.insets = new Insets(0, 5, 0, 0);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(this.createStaticLabel("Server listening on port: ", this.textFont), gbccofiguration);

		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridwidth = 3;
		gbccofiguration.gridx = 1;
		gbccofiguration.gridy = 0;
		gbccofiguration.insets = new Insets(0, 0, 0, 15);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(textFieldPort, gbccofiguration);

		
		//Central PANEL
		centralPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2;
		gbc.weighty = 2;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		centralPanel.add(serverInfo, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		centralPanel.add(serverControl, gbc);

		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.SOUTH;
		centralPanel.add(serverConfiguration, gbc);

		this.frame.setName("My Http Server");
		this.frame.add(centralPanel);
		this.setFrameTitle("Stopped");
		return this.frame;
	}

	private JLabel createStaticLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		return label;
	}

	private void setDefaultBorderToPanel(JPanel panel, String text) {
		panel.setBorder(BorderFactory.createTitledBorder(this.compoundBorder, text, TitledBorder.DEFAULT_POSITION,
				TitledBorder.DEFAULT_JUSTIFICATION, this.borderFont, Color.BLACK));
	}

	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MainScreen.class.getResource(path);
		return new ImageIcon(imgURL);
	}

	private void setFrameTitle(String state) {
		this.frame.setTitle(this.TITLE + "-[" + state + "]");
	}
}
