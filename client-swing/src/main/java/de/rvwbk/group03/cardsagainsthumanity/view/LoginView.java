package de.rvwbk.group03.cardsagainsthumanity.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.rvwbk.group03.cardsagainsthumanity.base.exception.WrongUserNameOrPasswordException;
import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;

public class LoginView extends JFrame {
	
	private JTextField user = new JTextField("Username");
	private JPasswordField password = new JPasswordField("Password");
	private JButton login = new JButton("Login");
	private JPanel loginPanel = new JPanel();
	private JPanel errorPanel = new JPanel();
	private JLabel message = new JLabel();
	
	private AbstractManager manager;
	
	
	public LoginView(final AbstractManager manager) {
		this.manager = manager;
	}
	
	
	public void init(final boolean errorOccured, final String errorMessage) {
		
		this.user.setSize(100, 25);
		this.password.setSize(100, 25);
		this.login.setSize(100, 25);
		
		this.login.addActionListener(event -> loginButtonClicked(event, this.user.getText(), this.password.getPassword().toString()));
		
		this.loginPanel.add(this.user);
		this.loginPanel.add(this.password);
		this.loginPanel.add(this.login);
		
		this.loginPanel.setSize(300, 30);
		
		setSize(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Please connect");
		
		if (errorOccured) {
			this.message.setText(errorMessage);
			this.message.setVisible(true);
			this.errorPanel.add(this.message);
			this.errorPanel.setSize(300, 25);
			add(this.errorPanel);
			revalidate();
		}
		
		add(this.loginPanel);
		
		setLayout(new GridLayout(2, 2, 0, 0));
		setVisible(true);
	}
	
	private void loginButtonClicked(final ActionEvent event, final String user, final String password) {
		try {
			this.manager.getClientManager().doLogin(user, password);
			dispose();
			new LobbyView(this.manager);
		} catch (WrongUserNameOrPasswordException e) {
			dispose();
			new LoginView(this.manager).init(true, "Kombination aus User und Password nicht korrekt");
		}
	}
}
