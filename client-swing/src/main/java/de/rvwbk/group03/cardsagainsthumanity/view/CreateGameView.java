package de.rvwbk.group03.cardsagainsthumanity.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;
import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.util.UiManager;

public class CreateGameView extends JFrame {
	
	private static AtomicInteger COUNT = new AtomicInteger(1);
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateGameView.class);
	
	private JButton createButton = new JButton("Create");
	private JButton cancelButton = new JButton("Cancel");
	private JTextField nameTextField;
	private JTextField joinPasswordTextField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JSlider maxPlayersSlider = new JSlider();
	
	private AbstractManager manager;
	private boolean createGame;
	private Game game;
	
	
	public CreateGameView(final AbstractManager manager, final boolean createGame, final Game game) {
		this.manager = manager;
		this.createGame = createGame;
		this.game = game;
		
		init();
	}
	
	private void init() {
		setTitle("Create Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(460, 450);
		getContentPane().setLayout(null);
		
		if (!this.createGame) {
			this.createButton.setText("Update game");
		}
		this.createButton.setBounds(341, 377, 89, 23);
		this.createButton.addActionListener(this::createButtonClicked);
		getContentPane().add(this.createButton);
		
		this.cancelButton.setBounds(10, 377, 89, 23);
		this.cancelButton.addActionListener(this::cancelButtonClicked);
		getContentPane().add(this.cancelButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 420, 359);
		getContentPane().add(panel);
		
		JLabel lblConfiguration = new JLabel("Configuration");
		lblConfiguration.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(lblConfiguration, "2, 2");
		
		JLabel lblGeneral = new JLabel("General");
		lblGeneral.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblGeneral, "2, 4");
		
		JLabel lblName = new JLabel("Name:");
		panel.add(lblName, "2, 6");
		
		this.nameTextField = new JTextField();
		this.nameTextField.setText("Client game " + COUNT.get());
		panel.add(this.nameTextField, "6, 6, fill, default");
		this.nameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Join password:");
		panel.add(lblPassword, "2, 8");
		
		this.joinPasswordTextField = new JTextField();
		panel.add(this.joinPasswordTextField, "6, 8, fill, default");
		this.joinPasswordTextField.setColumns(10);
		
		// TODO: Make multiple Decks
		
		JLabel lblMaxPlayers = new JLabel("Max Players:");
		panel.add(lblMaxPlayers, "2, 14");
		
		
		panel.add(this.maxPlayersSlider, "6, 14");
		this.maxPlayersSlider.setMinorTickSpacing(1);
		this.maxPlayersSlider.setMajorTickSpacing(1);
		this.maxPlayersSlider.setPaintLabels(true);
		this.maxPlayersSlider.setValue(6);
		this.maxPlayersSlider.setMinimum(Configuration.MIN_PLAYERS);
		this.maxPlayersSlider.setMaximum(Configuration.MAX_PLAYERS);
		
		JLabel lblWinningCondition = new JLabel("Winning Condition");
		lblWinningCondition.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblWinningCondition, "2, 16");
		
		JLabel lblRounds = new JLabel("Rounds:");
		panel.add(lblRounds, "2, 18");
		
		this.textField_2 = new JTextField();
		this.textField_2.setText("10");
		panel.add(this.textField_2, "6, 18, fill, default");
		this.textField_2.setColumns(10);
		
		JLabel lblPoints = new JLabel("Points:");
		panel.add(lblPoints, "2, 20");
		
		this.textField_3 = new JTextField();
		panel.add(this.textField_3, "6, 20, fill, default");
		this.textField_3.setColumns(10);
	}
	
	private void cancelButtonClicked(final ActionEvent event) {
		dispose();
		if (!this.createGame) {
			new PreGameView(this.manager);
		}
	}
	
	private void createButtonClicked(final ActionEvent event) {
		String joinPassword = this.joinPasswordTextField.getText();
		
		Configuration config = new Configuration(this.nameTextField.getText());
		try {
			config.setJoinPassword(joinPassword);
			config.setMaxNumberOfPlayer(this.maxPlayersSlider.getValue());
			// TODO: Multiple decks
			config.setCardDeckName("default");
			if (this.joinPasswordTextField.getText() != null) {
				config.setPasswordProtected(true);
				config.setJoinPassword(joinPassword);
			}
			COUNT.incrementAndGet();
			if (this.createGame) {
				this.manager.getLobbyManager().createGame(config);
			} else {
				this.manager.getGameManager().updateGame(config, getGame());
			}
			UiManager.closeAllViews();
			new PreGameView(this.manager);
			
		} catch (IllegalArgumentException e) {
			LOGGER.error("Could not create a Game.", e);
		}
	}
	
	public Game getGame() {
		return this.game;
	}
	
}
