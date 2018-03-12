import javax.swing.text.html.HTMLDocument.Iterator;

/*
 * Jack Williams
 * Holds ClubDeck and basic game info
 */
public class Dealer {
	private String gameTrump;
	private ClubDeck theDeck;
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
		return cardsPlayed;
	}
	
	public void setNumPlayers(int num){
		numOfPlayers = num;
		cardsPlayed = new Card[numOfPlayers * 5];
	}
	
	public int getSuitValue(String suit){
		
		int suitValue = 0;
		
		if(suit.equals(gameTrump)){
			suitValue = 10;
		}
		else if (gameTrump.equals("Clubs") && suit.equals("Spades")){
			suitValue = 5;
		}
		else if (gameTrump.equals("Spades") && suit.equals("Clubs")){
			suitValue = 5;
		}
		else if (gameTrump.equals("Hearts") && suit.equals("Diamonds")){
			suitValue = 5;
		}
		else if (gameTrump.equals("Diamonds") && suit.equals("Hearts")){
			suitValue = 5;
		}
		else {
			suitValue = 0; 
		}
		
		return suitValue;
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
			current.setHand(handForPlayer);
			current = itr.next();
		}
	}

}
