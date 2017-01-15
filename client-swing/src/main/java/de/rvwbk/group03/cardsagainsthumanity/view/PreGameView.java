package de.rvwbk.group03.cardsagainsthumanity.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;
import de.rvwbk.group03.cardsagainsthumanity.components.PlayerTableModel;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.Player;

public class PreGameView extends JFrame {
	
	private AbstractManager manager;
	private JButton startGame = new JButton("Start game");
	private JButton updateGameConfiguration = new JButton("Edit game settings");
	private JPanel buttonPanel = new JPanel();
	private JPanel playerPanel = new JPanel();
	private JTable currentPlayersTable = new JTable();
	private List<Player> playerList = new ArrayList<>();
	private Game currentGame;
	
	public PreGameView(final AbstractManager manager) {
		this.manager = manager;
		init();
	}
	
	private void init() {
		setSize(600, 200);
		setTitle("Manage Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.updateGameConfiguration.addActionListener(event -> updateGameButtonClicked(event));
		this.startGame.addActionListener(event -> startGameButtonClicked(event));
		
		this.buttonPanel.add(this.updateGameConfiguration);
		this.buttonPanel.add(this.startGame);
		
		add(this.buttonPanel, BorderLayout.SOUTH);
		
		// TODO: Updaten, wenn ein weiterer Spieler beitritt, eine Konfiguration geändert wird
		// und allgemein den Table in eigene Methode auslagern
		setCurrentGame(this.manager.getLobbyManager().getCreatedGame());
		
		this.currentPlayersTable.setEnabled(true);
		this.currentPlayersTable.getTableHeader().setReorderingAllowed(false);
		this.currentPlayersTable.setModel(new PlayerTableModel(this.currentGame, false));
		
		
		this.playerPanel.add(new JScrollPane(this.currentPlayersTable));
		add(this.playerPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public Game getCurrentGame() {
		return this.currentGame;
	}
	
	private void setCurrentGame(final Game currentGame) {
		this.currentGame = currentGame;
	}
	
	private void updateGameButtonClicked(final ActionEvent event) {
		setCurrentGame(this.manager.getGameManager().updateGame(this.currentGame));
	}
	
	private void startGameButtonClicked(final ActionEvent event) {
		this.manager.getGameManager().startGame(this.currentGame);
	}
	
}
