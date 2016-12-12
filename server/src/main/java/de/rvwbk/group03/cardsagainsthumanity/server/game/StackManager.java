package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Objects;

public class StackManager {
	
	private final Competition game;
	private BlackCardStack blackCardStack = new BlackCardStack();
	private WhiteCardStack whiteCardStack = new WhiteCardStack();
	
	public StackManager(final Competition game) throws NullPointerException {
		this.game = Objects.requireNonNull(game, "game must not be null");
	}
	
	public BlackCardStack getBlackCardStack() {
		return this.blackCardStack;
	}
	
	public WhiteCardStack getWhiteCardStack() {
		return this.whiteCardStack;
	}
	
	public void prepareGameStart() throws NullPointerException {
		this.blackCardStack.addBlackCards(this.game.getConfiguration().getCardDeck().getBlackCards());
		this.whiteCardStack.addWhiteCards(this.game.getConfiguration().getCardDeck().getWhiteCards());
		this.blackCardStack.getRandomBlackCard().setCardHolder(this.game);
		this.game.getPlayerManager().getPlayers().forEach(player -> player.getHand().addWhiteCards(this.whiteCardStack.getRandomWhiteCards(Hand.MAX_CARDS_ON_HAND)));
	}
	
	public void nextRound() throws NullPointerException {
		this.blackCardStack.getRandomBlackCard().setCardHolder(this.game);
		this.game.getPlayerManager().getPlayers().forEach(player -> {
			int neededCards = Hand.MAX_CARDS_ON_HAND - player.getHand().getWhiteCards().size();
			if (neededCards > 0) {
				player.getHand().addWhiteCards(this.whiteCardStack.getRandomWhiteCards(neededCards));
			}
		});
	}
}
