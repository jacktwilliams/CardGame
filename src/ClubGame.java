import java.util.Random;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

public class ClubGame {
	private Card cardsPlayed[];
	private int numOfPlayers;
	private ClubDeck deck;
	private Dealer dealer;
	Pile<Player> players;
	
	private static final String trump = "Clubs";

	
	
	public ClubGame() {
		// TODO Auto-generated constructor stub
		dealer = new Dealer();
		dealer.setTrump(trump);
		
		deck = new ClubDeck(dealer);
		
		dealer.setDeck(deck);
		
		System.out.println("3 or 4 players?");
		Scanner input = new Scanner(System.in);
		numOfPlayers = input.nextInt();
		
		if(numOfPlayers > 4 || numOfPlayers < 3){
			System.out.println("Invalid Input");
			input.close();
			return;
		}
		
		dealer.setNumPlayers(numOfPlayers);
		
		//create players
		players = new Pile<Player>();
		
		for(int i = numOfPlayers - 1; i >= 0; --i){
			players.add(new Player("Player " + String.valueOf(i)));
		}
				
		dealer.setPlayers(players);
	
		input.close();
	}
	
	public void play(){
		System.out.println("\n******** Shuffled Deck ********\n" + deck);
		
		Random randr = new Random();
		int startOffset = randr.nextInt(numOfPlayers); //startOffset is the same as first player to go
		
		java.util.Iterator<Player> itr = players.iterator();
		Player currentP = players.head.object;
		
		dealer.dealToPlayers();
		
		System.out.println("\n******** Cards Delt ********\n");
		
		//this for loop prints players and their cards with an asterisk next to Starting player.
		for(int i = 0; i < numOfPlayers; ++i){
			if(i == startOffset){
				System.out.println(currentP.toString("*"));
			}
			else{
				System.out.println(currentP);
			}
			currentP = itr.next();
		}
		
		//start of five-trick for loop
		for(int i = 0; i < 5; ++i) {
			//first navigate to the player who should start
			for(int x = 0; x < startOffset; ++x) {
				currentP = itr.next();
			}
			
			cardsPlayed[i] = currentP.getHand().bestPlay();
			
			if(itr.hasNext()) {
				currentP = itr.next();
			}
			else {
				currentP = players.head.object;
				itr = players.iterator();
			}
		}
		
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public String toString(){
		return deck.toString();
	}

}
