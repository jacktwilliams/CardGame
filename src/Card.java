
public class Card implements Comparable<Card>{
	private int number;
	
	public Card(int number){
		this.number = number;
	}

	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return this.number - o.getNumber();
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return this.number;
	}
	
	public String toString(){
		return String.valueOf(getNumber());
	}
}
