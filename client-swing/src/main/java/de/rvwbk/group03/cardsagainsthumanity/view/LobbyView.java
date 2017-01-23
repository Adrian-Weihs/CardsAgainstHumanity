package de.rvwbk.group03.cardsagainsthumanity.view;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
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
	
	private JButton refreshLobbyButton = new JButton("Refresh Lobby");
	private JButton joinGameButton = new JButton("Join game");
	private JButton createGameButton = new JButton("Create Game");
	
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
		setSize(700, 550);
		//		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		this.buttonPanel.add(this.createGameButton);
		this.buttonPanel.add(this.joinGameButton);
		this.buttonPanel.add(this.refreshLobbyButton);
		
		//		this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.X_AXIS));
		this.buttonPanel.setVisible(true);
		
		this.joinGameButton.setEnabled(getSelectedGame() != null);
		
		this.refreshLobbyButton.addActionListener(event -> refreshLobbyViewButtonClicked(event));
		this.joinGameButton.addActionListener(event -> joinGameButtonClicked(event));
		this.createGameButton.addActionListener(event -> createGameButtonClicked(event));
		
		add(this.buttonPanel, BorderLayout.SOUTH);
		
		// Refresh Lobby view on startup
		this.refreshLobbyButton.doClick();
		
		setVisible(true);
	}
	
	private void joinGameButtonClicked(final ActionEvent event) {
		// TODO: Passwort umsetzen
		this.manager.getLobbyManager().joinGame(getSelectedGame().getId(), Strings.EMPTY);
		dispose();
		new PreGameView(this.manager);
	}
	
	private void createGameButtonClicked(final ActionEvent event) {
		new CreateGameView(this.manager, true).setVisible(true);
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
