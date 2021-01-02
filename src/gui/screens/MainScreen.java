package gui.screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import gui.helpers.CreateFrame;
import gui.helpers.JDataInfo;
import gui.helpers.JFilePicker;

import server.MyServer;
import server.ServerState;
import server.configuration.Configuration;
import server.configuration.PersistentConfiguration;
import server.errors.InvalidConfigurationException;
import server.errors.InvalidParameterException;

public class MainScreen {

	private JFrame frame;
	private final String TITLE = "My Java Http Server";
	private ServerState state = ServerState.STOPPED;
	private final Border compoundBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	private final Font textFont = new Font("Verdana", 0, 12);
	private final Font borderFont = new Font("Verdana", Font.PLAIN, 9);
	private JDataInfo serverStatus;
	private JDataInfo serverAddress;
	private JDataInfo serverPort;
	private JCheckBox goMaintenance;
	private JTextField textFieldPort;
	private JFilePicker textFieldRootDirectory;
	private JFilePicker textFieldMaintenanceDirectory;
	private InetAddress myIP;

	private final String pathToStorageDesktop = System.getProperty("user.home") + File.separator + "Desktop"
			+ File.separator + "MyJavaHttpServerStorage.json";
	private PersistentConfiguration persistentConfig;
	@SuppressWarnings("unused")
	private Configuration config;
//	private MyServer server;
//	private ServerSocket serverSocket;

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

	public JFrame getMainFrame() throws InvalidConfigurationException, IOException, InvalidParameterException {
		this.persistentConfig = new PersistentConfiguration(pathToStorageDesktop);

		JPanel serverInfo = new JPanel();
		JPanel serverControl = new JPanel();
		JPanel serverConfiguration = new JPanel();
		JPanel centralPanel = new JPanel();
		ImageIcon start = createImageIcon("../assets/start.png");
		ImageIcon stop = createImageIcon("../assets/stop.png");
		myIP = InetAddress.getLocalHost();

		this.serverStatus = new JDataInfo("Server status: ", "not running", this.textFont, Color.RED);
		this.serverAddress = new JDataInfo("Server address: ", "not running", this.textFont, Color.blue);
		this.serverPort = new JDataInfo("Server listening port: ", "not running", this.textFont, Color.black);

		textFieldPort = new JTextField(5);
		textFieldPort.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = textFieldPort.getText();
				if (value.length() > 5 && ke.getKeyCode() != 8)
					showMessage("You have exceeded the maximum length.");
				else
				// ke.getKeyCode() == 8 check if delete button was press
				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyCode() == 8)
					textFieldPort.setEditable(true);
				else {
					System.out.println(value);
					showMessage("Port must be an integer...");
					if (value.length() == 0)
						textFieldPort.setText("");
					else
						textFieldPort.setText(value);
				}
			}
		});

		textFieldRootDirectory = new JFilePicker("Web root directory", "...", "Select root directory", textFont,
				JFileChooser.DIRECTORIES_ONLY, this.persistentConfig, "root");
		textFieldRootDirectory.setMode(JFilePicker.MODE_OPEN);

		textFieldMaintenanceDirectory = new JFilePicker("Maintenance page ", "...", "Select maintenance page", textFont,
				JFileChooser.FILES_ONLY, this.persistentConfig, "maintenance");
		textFieldMaintenanceDirectory.setMode(JFilePicker.MODE_OPEN);

		// SERVER INFO PANEL
		serverInfo.setLayout(new BoxLayout(serverInfo, BoxLayout.Y_AXIS));
		this.setDefaultBorderToPanel(serverInfo, "Server info");
		serverInfo.add(Box.createRigidArea(new Dimension(0, 50)));
		serverInfo.add(this.serverStatus);
		serverInfo.add(this.serverAddress);
		serverInfo.add(this.serverPort);

		// SERVER CONTROL PANEL
		serverControl.setLayout(new BoxLayout(serverControl, BoxLayout.PAGE_AXIS));
		this.setDefaultBorderToPanel(serverControl, "Server control");

		JButton buttonStartServer = new JButton("Start server", start);
		buttonStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == ServerState.STOPPED) {
					if (checkConfiguration()) {
						try {
							persistentConfig.setKey("port", textFieldPort.getText());
							config = new Configuration(textFieldRootDirectory.getSelectedFilePath(),
									textFieldMaintenanceDirectory.getSelectedFilePath(),
									Integer.parseInt(textFieldPort.getText()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidParameterException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						state = ServerState.RUNNING;
						goMaintenance.setEnabled(true);
						textFieldRootDirectory.disable();
						textFieldPort.setEditable(false);
						setFrameTitle("Running");
						serverStatus.setText("running...");
						serverAddress.setText(myIP.getHostAddress());
						serverPort.setText(textFieldPort.getText());
						buttonStartServer.setIcon(stop);
						buttonStartServer.setText("Stop server");
					} else
						showMessage("Please check configurations...");
				} else if (state == ServerState.RUNNING || state == ServerState.MAINTENANCE) {
					state = ServerState.STOPPED;
					textFieldRootDirectory.enable();
					textFieldMaintenanceDirectory.enable();
					textFieldPort.setEditable(true);
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
		serverControl.add(Box.createRigidArea(new Dimension(0, 50)));
		buttonStartServer.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonStartServer.setAlignmentY(Component.CENTER_ALIGNMENT);
		serverControl.add(buttonStartServer);

		goMaintenance = new JCheckBox("Switch to maintenance mode");
		goMaintenance.setEnabled(false);
		goMaintenance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (goMaintenance.isSelected()) {
					textFieldRootDirectory.enable();
					textFieldMaintenanceDirectory.disable();
					MyServer.setState(ServerState.MAINTENANCE);
					setFrameTitle("Maintenance");
					state = ServerState.MAINTENANCE;
					serverStatus.setText("maintenance");
				} else {
					textFieldRootDirectory.disable();
					textFieldMaintenanceDirectory.enable();
					MyServer.setState(ServerState.RUNNING);
					setFrameTitle("Running");
					state = ServerState.RUNNING;
					serverStatus.setText("running");
				}
			}
		});
		goMaintenance.setAlignmentX(Component.CENTER_ALIGNMENT);
		goMaintenance.setAlignmentY(Component.CENTER_ALIGNMENT);
		serverControl.add(goMaintenance);

		// SERVER CONFIGURATION PANEL
		this.setDefaultBorderToPanel(serverConfiguration, "Server configuration");
		GridBagConstraints gbccofiguration = new GridBagConstraints();
		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridx = 0;
		gbccofiguration.gridy = 0;
		gbccofiguration.insets = new Insets(0, 5, 0, 0);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(this.createStaticLabel("Server listening on port ", this.textFont), gbccofiguration);

		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridwidth = 3;
		gbccofiguration.gridx = 1;
		gbccofiguration.gridy = 0;
		gbccofiguration.insets = new Insets(0, 0, 0, 15);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(textFieldPort, gbccofiguration);

		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridx = 1;
		gbccofiguration.gridy = 0;
		gbccofiguration.insets = new Insets(0, 5, 0, 0);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(textFieldRootDirectory);

		gbccofiguration.fill = GridBagConstraints.HORIZONTAL;
		gbccofiguration.gridx = 0;
		gbccofiguration.gridy = 1;
		gbccofiguration.insets = new Insets(0, 5, 0, 0);
		gbccofiguration.weightx = 1;
		serverConfiguration.add(textFieldMaintenanceDirectory);

		// Central PANEL
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
		this.setDefaultConfig();
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

	private boolean checkConfiguration() {
		if (textFieldPort.getText().equals(null) || textFieldPort.getText().equals(""))
			return false;

		if (textFieldRootDirectory.getSelectedFilePath().equals(null)
				|| textFieldRootDirectory.getSelectedFilePath().equals(""))
			return false;

		if (textFieldMaintenanceDirectory.getSelectedFilePath().equals(null)
				|| textFieldMaintenanceDirectory.getSelectedFilePath().equals(""))
			return false;
		return true;
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	private void setDefaultConfig() throws InvalidConfigurationException, IOException, InvalidParameterException {
		Integer port;
		try {
			port = Integer.parseInt(this.persistentConfig.getValue("port"));
		} catch (Exception e) {
			port = null;
		}
		String maintenance = this.persistentConfig.getValue("maintenance");
		String root = this.persistentConfig.getValue("root");

		if (port != null)
			this.textFieldPort.setText(port.toString());
		if (maintenance != null)
			this.textFieldMaintenanceDirectory.setFilePath(maintenance);
		if (root != null)
			this.textFieldRootDirectory.setFilePath(root);
	}
}
