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
		int suitValue = gameDealer.getSuitValue(suit); //10 for trump suit, 5 for complimentary, 0 for opposite color
		
		for(int i = 9; i < 15; ++i) {
			if(suitValue == 10 && i == 11) {
				//trump is clubs and we are adding jack. Jack should have higher suitValue
				this.add(new Card(i, suit, suitValue + 2));
			}
			else if(suitValue == 5 && i == 11) {
				//trump is spades
				this.add(new Card(i, suit, suitValue+1));
			}
			else { 
				this.add(new Card(i, suit, suitValue));
			}
		}
		
		suit = "Diamonds";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			if(suitValue == 10 && i == 11) {
				this.add(new Card(i, suit, suitValue + 2));
			}
			else if(suitValue == 5 && i == 11) {
				this.add(new Card(i, suit, suitValue+1));
			}
			else { 
				this.add(new Card(i, suit, suitValue));
			}

		}
		
		suit = "Hearts";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			if(suitValue == 10 && i == 11) {
				this.add(new Card(i, suit, suitValue + 2));
			}
			else if(suitValue == 5 && i == 11) {
				this.add(new Card(i, suit, suitValue+1));
			}
			else { 
				this.add(new Card(i, suit, suitValue));
			}
		}
		
		suit = "Spades";
		suitValue = gameDealer.getSuitValue(suit);
		for(int i = 9; i < 15; ++i) {
			if(suitValue == 10 && i == 11) {
				this.add(new Card(i, suit, suitValue + 2));
			}
			else if(suitValue == 5 && i == 11) {
				this.add(new Card(i, suit, suitValue+1));
			}
			else { 
				this.add(new Card(i, suit, suitValue));
			}
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
