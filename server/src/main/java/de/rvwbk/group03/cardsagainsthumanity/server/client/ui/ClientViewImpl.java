package de.rvwbk.group03.cardsagainsthumanity.server.client.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Objects;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.client.ClientPresenter;
import de.rvwbk.group03.cardsagainsthumanity.server.client.ClientView;


/**
 * This is the swing implementation of {@link ClientView}.
 * 
 * @author Adrian Weihs
 */
public class ClientViewImpl extends JFrame implements ClientView {
	private static final long serialVersionUID = -7919227452177472427L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientViewImpl.class);
	
	
	private final ClientPresenter presenter;
	
	private Container layout;
	
	
	private final DefaultListModel<Client> clients = new DefaultListModel<>();
	private final JList<Client> listClients = new JList<>(this.clients);
	private JLabel labelClients;
	private JButton disconnectButton;
	
	
	/**
	 * Creates a new {@link ClientViewImpl}.
	 */
	public ClientViewImpl() {
		this.presenter = new ClientPresenter(this);
		
		init();
		this.presenter.init();
	}
	
	
	/**
	 * Initialize the view.
	 */
	private void init() {
		this.setSize(250, 350);
		setResizable(false);
		
		this.layout = getContentPane();
		this.layout.setLayout(null);
		
		this.listClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listClients.addListSelectionListener(event -> listSelectionChanged(event));
		JScrollPane spbClients = new JScrollPane(this.listClients);
		spbClients.setBounds(10, 40, 220, 202);
		this.layout.add(spbClients);
		
		this.labelClients = new JLabel("Clients: ");
		this.labelClients.setBounds(10, 10, 220, 20);
		this.layout.add(this.labelClients);
		
		this.disconnectButton = new JButton("Disconnect");
		this.disconnectButton.addActionListener(event -> disconnectButtonClicked(event));
		this.disconnectButton.setEnabled(false);
		this.disconnectButton.setBounds(10, 253, 220, 29);
		this.layout.add(this.disconnectButton);
		setLocationRelativeTo(null);
	}
	
	private void listSelectionChanged(final ListSelectionEvent event) {
		if (event == null) {
			LOGGER.warn("List selection event is null.");
			return;
		}
		
		this.disconnectButton.setEnabled(this.listClients.getSelectedValue() != null);
	}
	
	
	private void disconnectButtonClicked(final ActionEvent event) {
		if (event == null) {
			LOGGER.warn("Disconnect button action event is null.");
			return;
		}
		
		if (this.listClients.getSelectedValue() != null) {
			this.presenter.disconnectButtonClicked(this.listClients.getSelectedValue());
		}
	}
	
	/**
	 * Returns the selected Client from list.
	 * 
	 * @return - selected {@code Client}
	 */
	public Client getSelectedClient() {
		return this.listClients.getSelectedValue();
	}
	
	@Override
	public void setClients(final Collection<Client> clients) throws NullPointerException {
		Objects.requireNonNull(clients, "clients must not be null");
		
		clients.forEach(client -> this.clients.addElement(client));
	}
	
	@Override
	public void addClient(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		
		this.clients.addElement(client);
	}
	
	@Override
	public void updateClient(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		this.clients.removeElement(client);
		this.clients.addElement(client);
	}
	
	@Override
	public void removeClient(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		
		this.clients.removeElement(client);
	}
	
	@Override
	public void release() {
		this.presenter.release();
	}
}
