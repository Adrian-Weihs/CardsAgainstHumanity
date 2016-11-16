package de.rvwbk.group03.cardsagainsthumanity.client.debug.ui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.client.debug.DebugManager;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.GetGameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.LoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.UpdateGameConfigurationCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.CommandHelper;

public class DebugJsonViewImpl extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebugJsonViewImpl.class);
	
	private JTextArea jsonMessage = new JTextArea();
	private JButton sendMessageButton = new JButton("Send Message");
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuTemplate = new JMenu("Template");
	private JMenuItem menuItemLogincommand = new JMenuItem("LoginCommand");
	private JMenuItem menuItemGetGameListCommand = new JMenuItem("GetGameListCommand");
	private JMenuItem menuItemCreateGameCommand = new JMenuItem("CreateGameCommand");
	private JMenuItem menuItemUpdateGameConfigurationCommand = new JMenuItem("UpdateGameConfigurationCommand");
	
	
	public DebugJsonViewImpl() {
		setTitle("Send JSON message");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 500);
		getContentPane().setLayout(null);
		
		this.jsonMessage.setBounds(10, 11, 414, 384);
		this.jsonMessage.setLineWrap(true);
		LoginCommand loginCommand = new LoginCommand();
		loginCommand.setName("Adrian");
		loginCommand.setPassword("123");
		this.jsonMessage.setText("{}");
		getContentPane().add(this.jsonMessage);
		
		this.sendMessageButton.setBounds(10, 406, 414, 23);
		this.sendMessageButton.addActionListener(event -> sendMessageButtonClicked(event));
		getContentPane().add(this.sendMessageButton);
		
		
		setJMenuBar(this.menuBar);
		
		this.menuBar.add(this.menuTemplate);
		this.menuTemplate.add(this.menuItemLogincommand);
		this.menuItemLogincommand.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new LoginCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemGetGameListCommand);
		this.menuItemGetGameListCommand.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new GetGameListCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemCreateGameCommand);
		this.menuItemCreateGameCommand.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new CreateGameCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemUpdateGameConfigurationCommand);
		this.menuItemUpdateGameConfigurationCommand.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new UpdateGameConfigurationCommand(), Command.class)));
	}
	
	private void sendMessageButtonClicked(final ActionEvent event) {
		if (event == null) {
			LOGGER.warn("Send message button clicked event is null.");
			return;
		}
		DebugManager.getServerCommunication().getWriteCommunication().writeMessage(this.jsonMessage.getText());
	}
}
