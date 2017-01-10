package de.rvwbk.group03.cardsagainsthumanity.view;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.client.ClientPresenter;
import de.rvwbk.group03.cardsagainsthumanity.client.ClientView;
import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;
import de.rvwbk.group03.cardsagainsthumanity.components.ClientLobbyModel;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;

public class LobbyView extends JFrame implements ClientView {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LobbyView.class);
	private static final String VIEW_NAME = "Cards against Humanity";
	
	private final ClientPresenter presenter;
	private AbstractManager manager;
	
	private JButton connectButton = new JButton("Connect");
	private JButton refreshLobbyButton = new JButton("Refresh Lobby");
	private JButton joinGameButton = new JButton("Join game");
	
	private PrintStream console;
	private final JPanel buttonPanel = new JPanel();
	private final JPanel lobbyPanel = new JPanel();
	
	private JTable lobbyTable;
	
	private List<Game> games;
	
	
	public LobbyView(final AbstractManager manager) {
		this.manager = manager;
		this.presenter = new ClientPresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		
		setTitle(VIEW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		//		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		this.buttonPanel.add(this.connectButton);
		this.buttonPanel.add(this.refreshLobbyButton);
		this.buttonPanel.add(this.joinGameButton);
		
		//		this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.X_AXIS));
		this.buttonPanel.setVisible(true);
		
		this.joinGameButton.setEnabled(getSelectedGame() != null);
		
		this.connectButton.addActionListener(event -> connectButtonClicked(event));
		this.refreshLobbyButton.addActionListener(event -> refreshLobbyViewButtonClicked(event));
		this.joinGameButton.addActionListener(event -> joinGameButtonClicked(event));
		
		add(this.buttonPanel, BorderLayout.SOUTH);
		
		
		
		this.connectButton.setVisible(false);
		
		// Refresh Lobby view
		this.refreshLobbyButton.doClick();
		
		setVisible(true);
	}
	
	private void joinGameButtonClicked(final ActionEvent event) {
		// TODO: Passwort umsetzen
		this.manager.getLobbyManager().joinGame(getSelectedGame().getId(), Strings.EMPTY);
	}
	
	private void refreshLobbyViewButtonClicked(final ActionEvent actionEvent) {
		this.games = this.manager.getLobbyManager().getGames();
		
		this.lobbyPanel.removeAll();
		
		this.lobbyTable = new JTable();
		this.lobbyTable.setEnabled(true);
		this.lobbyTable.getTableHeader().setReorderingAllowed(false);
		this.lobbyTable.setRowSelectionAllowed(true);
		this.lobbyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.lobbyTable.getSelectionModel().addListSelectionListener(event -> gameSelectionChanged(event));
		this.lobbyTable.setModel(new ClientLobbyModel(this.games));
		
		this.lobbyPanel.add(new JScrollPane(this.lobbyTable));
		add(this.lobbyPanel, BorderLayout.CENTER);
		revalidate();
	}
	
	private void connectButtonClicked(final ActionEvent event) {
		if (event == null) {
			LOGGER.warn("Connect button clicked event is null.");
			return;
		}
		
		try {
			this.manager.getClientManager().connect();
			this.connectButton.setEnabled(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Could not connect to the Server", "Could not connect!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Returns the selected game.
	 *
	 * @return The selected game. May be {@code null} to indicate that there is no game selected.
	 */
	public Game getSelectedGame() {
		if (this.lobbyTable != null) {
			int row = this.lobbyTable.getSelectedRow();
			
			if(row != -1) {
				Game game = this.games.get(row);
				
				if(game != null) {
					return game;
				}
			}
		}
		return null;
	}
	
	private void gameSelectionChanged(final ListSelectionEvent event) {
		this.joinGameButton.setEnabled(this.lobbyTable.getSelectedRow() != -1);
	}
	
	@Override
	public void addRecievedMessage(final String recievedMessage) {
		if (this.console != null) {
			this.console.println(recievedMessage);
		}
	}
}
