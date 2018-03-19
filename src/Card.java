
public class Card implements Comparable<Card>{
	private int number;
	private String suit;
	private int suitValue;
	
	public Card(int number, String suit){
		this.number = number;
		this.suit = suit;
	}
	
	public Card(int number, String suit, int suitValue){
		this.number = number;
		this.suit = suit;
		this.suitValue = suitValue;
	}

	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		if(this.number == o.number && this.suit.equals(o.suit)) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return this.number;
	}
	
	public int getSuitValue(){
		return this.suitValue;
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
		return cardName + " of " + suit;
	}

	public void setSuitValue(int cardSuitValue) {
		// TODO Auto-generated method stub
		this.suitValue = cardSuitValue;
	}
}
