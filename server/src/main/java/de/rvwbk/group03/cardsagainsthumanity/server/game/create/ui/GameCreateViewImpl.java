package de.rvwbk.group03.cardsagainsthumanity.server.game.create.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.server.game.create.GameCreatePresenter;
import de.rvwbk.group03.cardsagainsthumanity.server.game.create.GameCreateView;

public class GameCreateViewImpl extends JFrame implements GameCreateView {
	
	private final GameCreatePresenter presenter;
	
	private JButton createButton = new JButton("Create");
	private JTextField nameTextField;
	private JTextField joinPasswordTextField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JComboBox<String> cardDeckNamecomboBox = new JComboBox<>();
	
	private JSlider maxPlayersSlider = new JSlider();
	
	public GameCreateViewImpl() {
		this.presenter = new GameCreatePresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		setTitle("Create Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(460, 450);
		this.createButton.setBounds(341, 377, 89, 23);
		
		this.createButton.addActionListener(event -> createButtonClicked(event));
		getContentPane().setLayout(null);
		getContentPane().add(this.createButton);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(10, 377, 89, 23);
		getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 420, 359);
		getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblConfiguration = new JLabel("Configuration");
		lblConfiguration.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(lblConfiguration, "2, 2");
		
		JLabel lblGeneral = new JLabel("General");
		lblGeneral.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblGeneral, "2, 4");
		
		JLabel lblName = new JLabel("Name:");
		panel.add(lblName, "2, 6");
		
		this.nameTextField = new JTextField();
		panel.add(this.nameTextField, "6, 6, fill, default");
		this.nameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Join password:");
		panel.add(lblPassword, "2, 8");
		
		this.joinPasswordTextField = new JTextField();
		panel.add(this.joinPasswordTextField, "6, 8, fill, default");
		this.joinPasswordTextField.setColumns(10);
		
		JLabel lblCardDeck = new JLabel("Card Deck:");
		panel.add(lblCardDeck, "2, 12");
		
		this.cardDeckNamecomboBox.addItem("default");
		panel.add(this.cardDeckNamecomboBox, "6, 12, fill, default");
		
		JLabel lblMaxPlayers = new JLabel("Max Players:");
		panel.add(lblMaxPlayers, "2, 14");
		
		
		panel.add(this.maxPlayersSlider, "6, 14");
		this.maxPlayersSlider.setMinorTickSpacing(1);
		this.maxPlayersSlider.setMajorTickSpacing(1);
		this.maxPlayersSlider.setPaintLabels(true);
		this.maxPlayersSlider.setValue(6);
		this.maxPlayersSlider.setMinimum(3);
		this.maxPlayersSlider.setMaximum(8);
		
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
	
	private void createButtonClicked(final ActionEvent event) {
		Configuration config = new Configuration();
		config.setName(this.nameTextField.getText());
		config.setJoinPassword(this.joinPasswordTextField.getText());
		config.setMaxNumberOfPlayer(this.maxPlayersSlider.getValue());
		config.setCardDeckName(this.cardDeckNamecomboBox.getSelectedItem().toString());
		this.presenter.handleCreateButtonClicked(config);
	}
	
	@Override
	public void release() {
		this.presenter.release();
	}
}
