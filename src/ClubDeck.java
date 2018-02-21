
public class ClubDeck extends Deck<Card> {
	
	public ClubDeck() {
		String suit = "clubs";
		for(int i = 9; i < 15; i++) {
			this.add(new Card(i, suit));
		}
		
		suit = "diamonds";
		for(int i = 9; i < 15; i++) {
			this.add(new Card(i, suit));
		}
		
		suit = "hearts";
		for(int i = 9; i < 15; i++) {
			this.add(new Card(i, suit));
		}
		
		suit = "spades";
		for(int i = 9; i < 15; i++) {
			this.add(new Card(i, suit));
		}
		
		this.shuffle();
	}
}
