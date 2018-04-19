import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ClubGame {
	private Card cardsPlayed[];
	private int numOfPlayers;
	private ClubDeck deck;
	private Dealer dealer;
	Pile<Player> players;
	
	public ClubGame() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		dealer = new Dealer();
		dealer.setGame(this);
		
		deck = new ClubDeck(dealer);
		
		dealer.setDeck(deck);
		
		System.out.println("3 or 4 players?");
		File f = new File("input.dat");
		Scanner input = new Scanner(f);
		numOfPlayers = input.nextInt();
		
		if(numOfPlayers > 4 || numOfPlayers < 3){
			System.out.println("Invalid Input");
			input.close();
			return;
		}
		
		dealer.setNumPlayers(numOfPlayers);
		cardsPlayed = new Card[numOfPlayers];

		//create players
		players = new Pile<Player>();
		
		for(int i = numOfPlayers - 1; i >= 0; --i){
			players.add(new Player("Player " + String.valueOf(i)));
		}
				
		dealer.setPlayers(players);
	
		input.close();
	}
	
	public void play(){
		Random randr = new Random();
		int startOffset = randr.nextInt(numOfPlayers); //startOffset is the same as first player to go
		Player currentP;
		System.out.println("\n******** Shuffled Deck ********\n" + deck);
		

		java.util.Iterator<Player> itr = players.iterator();
		currentP = players.head.object;
		
		dealer.dealToPlayers();
		
		System.out.println("\n******** Cards Delt ********\n");
		
		
		// start of five-trick for loop
		for (int i = 0; i < 5; ++i) {
			//this for loop prints players and their cards with an asterisk next to Starting player.
			if(i != 0) {
				System.out.println("\n******** Cards Held ********\n");
			}
			for(int x = 0; x < numOfPlayers; ++x){
				if(x == startOffset){
					System.out.println(currentP.toString("*"));
				}
				else{
					System.out.println(currentP);
				}
				currentP = itr.next();
			}
			
			currentP = players.head.object;
			itr = players.iterator();
			// first navigate to the player who should start
			for (int x = 0; x < startOffset; ++x) {
				currentP = itr.next();
			}
			
			/*Bidding block */
			if(i == 0) {
				cardsPlayed[0] = dealer.getDeck().remove(); // store flipped up card temporarily
				System.out.println("******** Flipped ********\n" + cardsPlayed[0]);
				
				String pickedTrump = cardsPlayed[0].getSuit();
				String bidWinnerName = "";
				
				
				if(cardsPlayed[0].getSuit().equals("Clubs")) {
					dealer.setTrump("Clubs");
				}
				else {
					//start bidding
					int highestBid = 0; //TODO have players consider current bid or pick last player if all bids are 0
					//also while we are here, I have seen a player bid hearts, then play their jack of diamonds before their jack of hearts.
					int currentBid;
					for(int x = 0; x < numOfPlayers; ++x) {
						currentBid = currentP.getHand().getBid(highestBid);
						System.out.println(currentP.getName() + ": " + currentBid);
						if(currentBid > highestBid) {
							bidWinnerName = currentP.getName();
							pickedTrump = currentP.getHand().getTrumpChoice();
							highestBid = currentBid;
						}
						
						if (itr.hasNext()) {
							currentP = itr.next();
						} else {
							currentP = players.head.object;
							itr = players.iterator();
						}
					}
					dealer.setTrump(pickedTrump);
				}
				
				System.out.println(bidWinnerName + " picked " + pickedTrump);
			}
			
			/*Playing block */
			for (int x = 0; x < numOfPlayers; ++x) {
				
				//refresh hand suit-values. Dealer suit must be worth more than non-dealer suits
				if(x != 0) {
					currentP.getHand().refreshSuitValues(cardsPlayed[0]);
				}
				
				cardsPlayed[x] = currentP.getHand().bestPlay();
				
				
				//if x == 0, then refreshSuit values for hand just played
				if(x == 0) {
					dealer.dynamicSetSuitValue(cardsPlayed[0], cardsPlayed[0]);
				}
				
				
				
				if (itr.hasNext()) {
					currentP = itr.next();
				} else {
					currentP = players.head.object;
					itr = players.iterator();
				}
			} //cards have been played
			
			int champPlayer = dealer.getBest();
			currentP = players.head.object;
			itr = players.iterator();
			int newOffset = 0;
			
			
			for(int x = 0; x < numOfPlayers; ++x) {
				if(x == champPlayer) {
					System.out.println("Player " + String.valueOf((x + startOffset) % numOfPlayers) + 
							"*: " + cardsPlayed[x]);
				}
				else {
					System.out.println("Player " + String.valueOf((x + startOffset) % numOfPlayers) + 
							": " + cardsPlayed[x]);
				}
				
				if((champPlayer + startOffset) % numOfPlayers == x) {
					currentP.addWin();
					newOffset = (champPlayer + startOffset) % numOfPlayers; //start at winner player next game
				}
				currentP = itr.next();
			} //trick moves displayed
			startOffset = newOffset;

			
			cardsPlayed = new Card[numOfPlayers];
			
			currentP = players.head.object;
			itr = players.iterator();
			
		}//all tricks done
		currentP = players.head.object;
		itr = players.iterator();
		
		System.out.println("\n******** Game Results *******\n");
		for(int i = 0; i < numOfPlayers; ++i) {
			System.out.println(currentP.getName() + ": " + currentP.getWinCount() + " tricks won.");
			currentP = itr.next();
		}
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public String toString(){
		return deck.toString();
	}
	
	public Card[] getCardsPlayed() {
		return this.cardsPlayed;
	}

}
