/*
 * Jack Williams
 * Holds ClubDeck and basic game info
 */
public class Dealer {
	private String gameTrump = "Clubs"; // this will get updated. LEGACY AND LAZINESS
	private ClubDeck theDeck;
	private ClubGame game;
	private Card[] cardsPlayed;
	private int numOfPlayers;
	private Pile<Player> players;
	
	public Dealer() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTrump(){
		return this.gameTrump;
	}
	
	public ClubDeck getDeck(){
		return this.theDeck;
	}
	
	public void setTrump(String trump){
		gameTrump = trump;
	}
	
	public void setDeck(ClubDeck d){
		theDeck = d;
	}
	
	public Card[] getCardsPlayed(){
		return game.getCardsPlayed();
	}
	
	public void setNumPlayers(int num){
		numOfPlayers = num;
		cardsPlayed = new Card[numOfPlayers * 5];
	}
	
	public int getSuitValue(String suit, int i){
		
		int suitValue = 0;
		
		if(suit.equals(gameTrump) && i != 11){
			suitValue = 9;
		}
		else if(suit.equals(gameTrump) && i == 11) {
			suitValue = 13;
		}
		else if (gameTrump.equals("Clubs") && suit.equals("Spades") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Spades") && suit.equals("Clubs") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Hearts") && suit.equals("Diamonds") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Diamonds") && suit.equals("Hearts") && i == 11){
			suitValue = 11;
		}
		else {
			suitValue = 0; 
		}
		
		return suitValue;
	}
	
	public int getSuitValue(String gameTrump, String suit, int i){
		
		int suitValue = 0;
		
		if(suit.equals(gameTrump) && i != 11){
			suitValue = 9;
		}
		else if(suit.equals(gameTrump) && i == 11) {
			suitValue = 13;
		}
		else if (gameTrump.equals("Clubs") && suit.equals("Spades") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Spades") && suit.equals("Clubs") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Hearts") && suit.equals("Diamonds") && i == 11){
			suitValue = 11;
		}
		else if (gameTrump.equals("Diamonds") && suit.equals("Hearts") && i == 11){
			suitValue = 11;
		}
		else {
			suitValue = 0; 
		}
		
		return suitValue;
	}
	
	public void setGame(ClubGame game) {
		this.game = game;
	}

	public void setPlayers(Pile<Player> players) {
		// TODO Auto-generated method stub
		this.players = players;
	}

	public void dealToPlayers() {
		// TODO Auto-generated method stub
		Player current = players.head.object;
		java.util.Iterator<Player> itr = players.iterator();
		
		for(int i = 0; i < numOfPlayers; ++i){
			ClubHand handForPlayer = new ClubHand();
			for(int x = 0; x < 5; ++x){
				handForPlayer.add(theDeck.remove());
			}
			handForPlayer.setDealer(this); //give each hand a reference to the dealer.
			current.setHand(handForPlayer);
			current = itr.next();
		}
	}
	//returns index of best card
	public int getBest () {
		// TODO Auto-generated method stub
		Card bestCard = new Card(-1, "null", -1);
		
		Card[] cardsPlayed = getCardsPlayed();
		int bestCIndex = -1;
		
		for(int i = 0; i < cardsPlayed.length; ++i){
			if(cardsPlayed[i] != null) {
				if(cardsPlayed[i].getSuitValue() > bestCard.getSuitValue()){
					bestCard = cardsPlayed[i];
					bestCIndex = i;
				}
				else if(cardsPlayed[i].getSuitValue() == bestCard.getSuitValue() &&
						cardsPlayed[i].getNumber() > bestCard.getNumber()) {
					bestCard = cardsPlayed[i];
					bestCIndex = i;
				}
			}
		}
		return bestCIndex;
	}

	public void dynamicSetSuitValue(Card current, Card cardPlayed) {
		// TODO Auto-generated method stub
		int cardSuitValue = 0;
		int i = current.getNumber();
		String cardSuit = current.getSuit();
		String playedSuit = cardPlayed.getSuit();
		
		if(playedSuit.equals("Joker") || cardPlayed.getSuitValue() == 11) {
			playedSuit = gameTrump;
		}
		
		if(cardSuit.equals("Joker")) {
			cardSuitValue = 15;
		}
		else if (cardSuit.equals(gameTrump) && i != 11) {
			cardSuitValue = 9;
		} else if (cardSuit.equals(gameTrump) && i == 11) {
			cardSuitValue = 13;
		} else if (gameTrump.equals("Clubs") && cardSuit.equals("Spades") && i == 11) {
			cardSuitValue = 11;
		} else if (gameTrump.equals("Spades") && cardSuit.equals("Clubs") && i == 11) {
			cardSuitValue = 11;
		} else if (gameTrump.equals("Hearts") && cardSuit.equals("Diamonds") && i == 11) {
			cardSuitValue = 11;
		} else if (gameTrump.equals("Diamonds") && cardSuit.equals("Hearts") && i == 11) {
			cardSuitValue = 11;
		} else {
			cardSuitValue = 0;
		}

		if (cardSuit.equals(cardPlayed)) {
			cardSuitValue += 1;
		}
		
		current.setSuitValue(cardSuitValue);

	}

}
