/*
 * Jack Williams
 * Simulates one Shuffled Deck of cards to play 'Clubs' with.
 */
public class ClubDeck extends Deck<Card> {
	private Dealer gameDealer;
	public ClubDeck(Dealer d) {
		
		gameDealer = d;
		
		String trump = gameDealer.getTrump();
		
		String suit = "Clubs";
		int suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			this.add(new Card(i, suit, suitValue));
		}
		
		suit = "Diamonds";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			this.add(new Card(i, suit, suitValue));
		}
		
		suit = "Hearts";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			this.add(new Card(i, suit, suitValue));
		}
		
		suit = "Spades";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			this.add(new Card(i, suit, suitValue));
		}
		
		this.add(new Card(15, "Joker", 15)); //15 is highest suit-value
		
		this.shuffle();
		
		//Full deck is created and shuffled
	}
	
	public void setDealer(Dealer d){
		this.gameDealer = d;
	}
	
	/*
	public String toString(){
		Node<Card> current = head;
		Card crntCard;
		String result = "";
		
		for(int i = 0; i < size; i++){
			crntCard = current.object;
			
			result += crntCard.toString() + "\n";
			current = current.next;
		}
		return result;
	}
	*/
}
