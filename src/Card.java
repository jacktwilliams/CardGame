
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
		return String.valueOf(getNumber() + suit);
	}
}
