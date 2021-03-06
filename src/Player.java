
public class Player implements Comparable<Player>{
	private String name;
	private ClubHand hand;
	private int winCount = 0;
	private int gameScore = 0;
	private boolean playing;
	
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
	
	public void addWin() {
		++this.winCount;
	}
	
	public int getWinCount() {
		return this.winCount;
	}
	
	public int getGameScore() {
		return this.gameScore;
	}
	
	public void updateGameScore(int mod) {
		this.gameScore += mod;
	}

	public void resetWinCount() {
		// TODO Auto-generated method stub
		this.winCount = 0;
	}

	public void setPlaying(boolean entry) {
		// TODO Auto-generated method stub
		this.playing = entry;
	}
	
	public boolean getPlaying() {
		return this.playing;
	}

}
