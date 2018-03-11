
public class Player implements Comparable<Player>{
	private String name;
	private ClubHand hand;
	
	public Player(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public int compareTo(Player arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setHand(ClubHand handForPlayer) {
		// TODO Auto-generated method stub
		hand = handForPlayer;
	}
	
	public ClubHand getHand(){
		return this.hand;
	}
	
	public String toString(){
		return name + "\n" + hand.toString();
	}
	
	//toString which appends to the name
	public String toString(String appendage){
		return name + appendage + "\n" + hand.toString();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
