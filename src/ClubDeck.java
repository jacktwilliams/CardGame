import javax.swing.text.html.HTMLDocument.Iterator;

/*
 * Jack Williams
 * Simulates one Shuffled Deck of cards to play 'Clubs' with.
 */
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
		
		this.add(new Card(15, "Joker"));
		
		this.shuffle();
		
		//Full deck is created and shuffled
	}
	
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
}
