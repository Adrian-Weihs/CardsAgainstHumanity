package de.rvwbk.group03.cardsagainsthumanity.client.debug.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.client.debug.DebugPresenter;
import de.rvwbk.group03.cardsagainsthumanity.client.debug.DebugView;
import de.rvwbk.group03.cardsagainsthumanity.client.debug.component.JComponentOutputStream;
import de.rvwbk.group03.cardsagainsthumanity.client.debug.component.JComponentOutputStream.JComponentHandler;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.GetGameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.LoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.UpdateGameConfigurationCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.CommandHelper;

public class DebugViewImpl extends JFrame implements DebugView {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebugViewImpl.class);
	private static final String VIEW_NAME = "Cards against Humanity - Debug Client";
	
	private final DebugPresenter presenter;
	
	private JTextArea recivedMessageTextArea = new JTextArea();
	private JButton connectButton = new JButton("Connect");
	private JButton sendJsonButton = new JButton("Send Message");
	
	private JTextArea jsonMessage = new JTextArea();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuTemplate = new JMenu("Template");
	private JMenuItem menuItemLogincommand = new JMenuItem("LoginCommand");
	private JMenuItem menuItemGetGameListCommand = new JMenuItem("GetGameListCommand");
	private JMenuItem menuItemCreateGameCommand = new JMenuItem("CreateGameCommand");
	private JMenuItem menuItemUpdateGameConfigurationCommand = new JMenuItem("UpdateGameConfigurationCommand");
	
	private PrintStream console;
	private final JPanel panel = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	
	public DebugViewImpl() {
		this.presenter = new DebugPresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		
		setTitle(VIEW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		getContentPane().add(this.panel_1);
		this.panel_1.setLayout(new BoxLayout(this.panel_1, BoxLayout.Y_AXIS));
		this.panel_1.add(this.panel);
		this.panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		
		JLabel labelConsole = new JLabel();
		JComponentOutputStream consoleOutputStream = new JComponentOutputStream(labelConsole, new JComponentHandler() {
			private StringBuilder sb = new StringBuilder();
			
			
			@Override
			public void setText(final JComponent swingComponent, final String text) {
				this.sb.delete(0, this.sb.length());
				append(swingComponent, text);
			}
			
			@Override
			public void replaceRange(final JComponent swingComponent, final String text, final int start, final int end) {
				this.sb.replace(start, end, text);
				redrawTextOf(swingComponent);
			}
			
			@Override
			public void append(final JComponent swingComponent, final String text) {
				this.sb.append(text);
				redrawTextOf(swingComponent);
			}
			
			private void redrawTextOf(final JComponent swingComponent) {
				((JLabel) swingComponent).setText("<html><pre>" + this.sb.toString() + "</pre></html>");
			}
		});
		this.console = new PrintStream(consoleOutputStream);
		
		// Optional: add a scrollpane around the console for having scrolling bars
		JScrollPane sp = new JScrollPane(labelConsole, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.panel.add(sp);
		
		this.panel_1.add(this.panel_3);
		this.panel_3.setLayout(new BoxLayout(this.panel_3, BoxLayout.X_AXIS));
		
		JButton btnDisconnect = new JButton("Disconnect");
		this.panel_3.add(btnDisconnect);
		btnDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {}
		});
		btnDisconnect.setEnabled(false);
		this.panel_3.add(this.connectButton);
		this.connectButton.addActionListener(event -> connectButtonClicked(event));
		
		getContentPane().add(this.panel_4);
		this.panel_4.setLayout(new BoxLayout(this.panel_4, BoxLayout.Y_AXIS));
		this.panel_4.add(this.panel_2);
		this.panel_2.setLayout(new GridLayout(1, 1, 0, 0));
		this.panel_2.add(this.jsonMessage);
		this.jsonMessage.setLineWrap(true);
		
		this.panel_4.add(this.panel_5);
		this.panel_5.setLayout(new BoxLayout(this.panel_5, BoxLayout.X_AXIS));
		this.panel_5.add(this.sendJsonButton);
		this.sendJsonButton.setEnabled(false);
		this.sendJsonButton.addActionListener(event -> sendJsonButtonClicked(event));
		
		setJMenuBar(this.menuBar);
		
		this.menuBar.add(this.menuTemplate);
		this.menuTemplate.add(this.menuItemLogincommand);
		this.menuItemLogincommand
		.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new LoginCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemGetGameListCommand);
		this.menuItemGetGameListCommand
		.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new GetGameListCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemCreateGameCommand);
		this.menuItemCreateGameCommand
		.addActionListener(event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new CreateGameCommand(), Command.class)));
		this.menuTemplate.add(this.menuItemUpdateGameConfigurationCommand);
		this.menuItemUpdateGameConfigurationCommand.addActionListener(
				event -> this.jsonMessage.setText(CommandHelper.createGsonCommandBuilder().serializeNulls().create().toJson(new UpdateGameConfigurationCommand(), Command.class)));
		
	}
	
	private void connectButtonClicked(final ActionEvent event) {
		if (event == null) {
			LOGGER.warn("Connect button clicked event is null.");
			return;
		}
		
		try {
			this.presenter.connectButtonClicked();
			this.sendJsonButton.setEnabled(true);
			this.connectButton.setEnabled(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Could not connect to the Server", "Could not connect!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void sendJsonButtonClicked(final ActionEvent event) {
		if (event == null) {
			LOGGER.warn("Send command button clicked event is null.");
			return;
		}
		
		this.presenter.sendJsonButtonClicked(this.jsonMessage.getText());
	}
	
	@Override
	public void addRecievedMessage(final String recievedMessage) {
		if (this.console != null) {
			this.console.println(recievedMessage);
		}
	}
}
