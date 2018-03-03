
public class Card implements Comparable<Card>{
	private int number;
	private String suit;
	
	public Card(int number, String suit){
		this.number = number;
		this.suit = suit;
	}

	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return this.number - o.getNumber();
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return this.number;
	}
	
	public String getSuit() {
		return this.suit;
	}
	
	public String toString(){
		String cardName = "";
		
		switch(number) {
		case 9:
			cardName = String.valueOf(number);
			break;
		
		case 10:
			cardName = String.valueOf(number);
			break;
		
		case 11:
			cardName = "Jack";
			break;
		
		
		case 12:
			cardName = "Queen";
			break;
			
		case 13:
			cardName = "King";
			break;
			
		case 14:
			cardName = "Ace";
			break;
		
		case 15:
			cardName = "Joker";
			break;
		
		default:
			cardName = "ERROR";
			
		}
		return cardName + ": " + suit;
	}
}
