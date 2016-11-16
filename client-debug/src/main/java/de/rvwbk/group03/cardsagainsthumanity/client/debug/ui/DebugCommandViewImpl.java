package de.rvwbk.group03.cardsagainsthumanity.client.debug.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DebugCommandViewImpl extends JFrame {
	public DebugCommandViewImpl() {
		setTitle("Send command");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 500);
		
		getContentPane().setLayout(new GridLayout(5, 0, 0, 0));
		
		JButton btnLogincommand = new JButton("LoginCommand");
		getContentPane().add(btnLogincommand);
		
		JButton btnGetgamelist = new JButton("GetGameListCommand");
		getContentPane().add(btnGetgamelist);
		
		JButton btnCreategamecommand = new JButton("CreateGameCommand");
		getContentPane().add(btnCreategamecommand);
		
		JButton btnUpdategameconfigurationcommand = new JButton("UpdateGameConfigurationCommand");
		getContentPane().add(btnUpdategameconfigurationcommand);
		
		JButton btnJoingamecommand = new JButton("JoinGameCommand");
		getContentPane().add(btnJoingamecommand);
	}
	
}
