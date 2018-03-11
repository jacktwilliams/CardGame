/*
 * Author: Jack Williams
 * Testing app for testing deck and its shuffle method
 */
public class TestingApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Deck<Card> deck = new Deck<Card>(); Deck<Card> copy = new
		 * Deck<Card>(); //toString results in empty deck. copy is for testing.
		 * for (int i = 0; i < DECKSIZE; i++) { deck.add(new Card(i, "spades"));
		 * copy.add(new Card(i, "spades")); }
		 * 
		 * 
		 * deck.shuffle();
		 * 
		 * System.out.println(deck);
		 */
		ClubGame clubs = new ClubGame();
		clubs.play();
	}

}
