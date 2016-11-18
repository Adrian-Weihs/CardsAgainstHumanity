package de.rvwbk.group03.cardsagainsthumanity.server.engine.ui;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.server.engine.GameEnginePresenter;
import de.rvwbk.group03.cardsagainsthumanity.server.engine.GameEngineView;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

/**
 * The Graphical User Interface, which shows all games, but also games by its status. The user can select one game from
 * the table to manage it. Also the user can create a new game. In both cases a new Window will be showed.
 * 
 * @author Adrian Weihs
 */
public class GameEngineViewImpl extends JFrame implements GameEngineView {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameEngineViewImpl.class);
	
	private static final String VIEW_NAME = "Cards against Humanity - Overview";
	
	private final GameEnginePresenter presenter;
	
	private GameTableModel games = new GameTableModel();
	
	private JTabbedPane selectViewPane;
	private JScrollPane pnAllGames;
	private JTable tblAllGames;
	private JPanel cardsAgainstHumanityServerPane;
	private JButton btnCreateNewGame;
	private JButton btnManageGame;
	private JButton btnManageClients;
	
	
	/**
	 * The constructor of {@link GameEngineViewImpl}.
	 * 
	 * @param gameEngineModel the gameEngine.
	 */
	public GameEngineViewImpl() {
		this.presenter = new GameEnginePresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		initForm();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Creates all components to show the user a table of games and its informations.
	 */
	private void initForm() {
		setTitle(VIEW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(800, 350);
		this.cardsAgainstHumanityServerPane = new JPanel();
		this.cardsAgainstHumanityServerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.cardsAgainstHumanityServerPane);
		this.cardsAgainstHumanityServerPane.setLayout(null);
		setResizable(false);
		
		this.selectViewPane = new JTabbedPane(JTabbedPane.TOP);
		this.selectViewPane.setBounds(10, 20, 780, 240);
		this.cardsAgainstHumanityServerPane.add(this.selectViewPane);
		
		//All games
		this.tblAllGames = new JTable();
		this.tblAllGames.setEnabled(true);
		this.tblAllGames.getTableHeader().setReorderingAllowed(false);
		this.tblAllGames.setRowSelectionAllowed(true);
		this.tblAllGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tblAllGames.setBounds(0, 0, 541, 223);
		this.tblAllGames.setModel(this.games);
		
		
		this.pnAllGames = new JScrollPane(this.tblAllGames);
		this.pnAllGames.setLocation(0, 0);
		this.pnAllGames.setSize(541, 223);
		this.selectViewPane.addTab("All Games", null, this.pnAllGames, null);
		
		
		this.btnCreateNewGame = new JButton("Create New Game");
		this.btnCreateNewGame.addActionListener(event -> createGameButtonClicked(event));
		this.btnCreateNewGame.setBounds(281, 269, 163, 23);
		this.cardsAgainstHumanityServerPane.add(this.btnCreateNewGame);
		
		this.btnManageGame = new JButton("Manage Game");
		this.btnManageGame.setBounds(454, 269, 169, 23);
		this.btnManageGame.setEnabled(false);
		this.cardsAgainstHumanityServerPane.add(this.btnManageGame);
		
		this.btnManageClients = new JButton("Manage Clients");
		this.btnManageClients.addActionListener(event -> manageClientsButtonClicked(event));
		this.btnManageClients.setBounds(633, 269, 157, 23);
		this.cardsAgainstHumanityServerPane.add(this.btnManageClients);
	}
	
	private void manageClientsButtonClicked(final ActionEvent event) {
		this.presenter.handleManageClientsButtonClicked();
	}
	
	private void createGameButtonClicked(final ActionEvent event) {
		this.presenter.handleCreateGameButtonClicked();
	}
	
	/**
	 * Enables (or disables) the button.
	 * 
	 * @param b true to enable the button, otherwise false
	 */
	public void setBtnManageGameEnable(final boolean b) {
		this.btnManageGame.setEnabled(b);
	}
	
	
	/**
	 * Returns the selected game.
	 * 
	 * @return The selected game. May be {@code null} to indicate that there is no game selected.
	 */
	public Game getSelectedGame() {
		int row = this.tblAllGames.getSelectedRow();
		
		if(row != -1) {
			Game game = this.games.getGame(row);
			
			if(game != null) {
				return game;
			}
		}
		
		return null;
	}
	
	@Override
	public void release() {
		this.presenter.release();
	}
	
	@Override
	public void addGame(final Game game) throws NullPointerException {
		this.games.addGame(Objects.requireNonNull(game, "game must not be null"));
	}
	
	@Override
	public void updateGame(final Game game) throws NullPointerException {
		this.games.updateGame(Objects.requireNonNull(game, "game must not be null"));
	}
	
	@Override
	public void removeGame(final Game game) throws NullPointerException {
		this.games.removeGame(Objects.requireNonNull(game, "game must not be null"));
	}
}
