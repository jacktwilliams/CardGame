import java.io.File;
import java.io.FileNotFoundException;
import java.text.Bidi;
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
		//File f = new File("input.dat");
		Scanner input = new Scanner(System.in);
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
		int startOffset;
		Player currentP;
		String bidWinnerName;
		int highestBid;
		java.util.Iterator<Player> itr;
		
		boolean gameOver = false;
		String winner = "";
		
		while (!gameOver) {
			startOffset = randr.nextInt(numOfPlayers);
			bidWinnerName = "";
			highestBid = 0;
			itr = players.iterator();
			currentP = players.head.object;
			deck = new ClubDeck(dealer);
			dealer.setDeck(deck);
			System.out.println("\n******** Shuffled Deck ********\n" + deck);

			dealer.dealToPlayers();

			// start of five-trick for loop
			for (int i = 0; i < 5; ++i) {
				// this for loop prints players and their cards with an asterisk next to
				// Starting player.
				if (i != 0) {
					System.out.println("\n******** Cards Held ********");
				}
				for (int x = 0; x < numOfPlayers; ++x) {
					if (x == startOffset) {
						System.out.println(currentP.toString("*"));
					} else {
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

				/* Bidding block */
				if (i == 0) {
					cardsPlayed[0] = dealer.getDeck().remove(); // store flipped up card temporarily
					System.out.println("******** Flipped ********\n" + cardsPlayed[0]);

					String pickedTrump = cardsPlayed[0].getSuit();

					if (cardsPlayed[0].getSuit().equals("Clubs")) {
						dealer.setTrump("Clubs");
					} else {
						System.out.println("\n********Bidding********");
						int currentBid;
						for (int x = 0; x < numOfPlayers; ++x) {
							currentBid = currentP.getHand().getBid(highestBid);
							System.out.println(currentP.getName() + ": " + currentBid);
							if (currentBid > highestBid) {
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
						System.out.println(bidWinnerName + " picked " + pickedTrump);
					}
					
					System.out.println("\n*******Player Entries*******");
					//bidding over. Allow players to opt out.
					boolean entry;
					for(int y = 0; y < numOfPlayers; ++y) {
						entry = currentP.getHand().getEntry();
						currentP.setPlaying(entry);
						
						if(entry) {
							System.out.println(currentP.getName() + ": Playing");
						}
						else {
							System.out.println(currentP.getName() + ": Passing");
						}
						
						if (itr.hasNext()) {
							currentP = itr.next();
						} else {
							currentP = players.head.object;
							itr = players.iterator();
						}
					}
				}
				
				

				cardsPlayed[0] = null; // no need for flipped card any longer

				System.out.println("\n********Cards Played********");

				/* Playing block */
				boolean hasPlayed = false;
				boolean firstPlay = true;
				int firstPlayed = numOfPlayers;
				for (int x = 0; x < numOfPlayers; ++x) {
					// refresh hand suit-values. Dealer suit must be worth more than non-dealer
					// suits
					if (x > firstPlayed) {
						currentP.getHand().refreshSuitValues(cardsPlayed[firstPlayed]);
					}
					
					if(currentP.getPlaying()) {
						cardsPlayed[x] = currentP.getHand().bestPlay();
						hasPlayed = true;
					}
					else {
						cardsPlayed[x] = null;
					}

					// if first card has been played, then refresh suit values for hand just played
					if (hasPlayed && firstPlay) {
						dealer.dynamicSetSuitValue(cardsPlayed[x], cardsPlayed[x]);
						firstPlay = false;
						firstPlayed = x;
					}

					if (itr.hasNext()) {
						currentP = itr.next();
					} else {
						currentP = players.head.object;
						itr = players.iterator();
					}
				} // cards have been played
				int champPlayer = dealer.getBest();
				/*
				currentP = players.head.object;
				itr = players.iterator();
				*/
				int newOffset = 0;

				for (int x = 0; x < numOfPlayers; ++x) {
					if (currentP.getPlaying()) {
						if (x == champPlayer) {
							System.out.println("Player " + String.valueOf((x + startOffset) % numOfPlayers) + "*: "
									+ cardsPlayed[x]);
							currentP.addWin();
							newOffset = (champPlayer + startOffset) % numOfPlayers; // start at winner player next game

							
						} else {
							System.out.println("Player " + String.valueOf((x + startOffset) % numOfPlayers) + ": "
									+ cardsPlayed[x]);
						}
					}
					else {
						System.out.println("Player " + String.valueOf((x + startOffset) % numOfPlayers) + " (Passed): " + cardsPlayed[x]);
					}
					
					if (itr.hasNext()) {
						currentP = itr.next();
					} else {
						currentP = players.head.object;
						itr = players.iterator();
					}
				} // trick moves displayed
				startOffset = newOffset;

				cardsPlayed = new Card[numOfPlayers];

				currentP = players.head.object;
				itr = players.iterator();

			} // all tricks done
			currentP = players.head.object;
			itr = players.iterator();

			System.out.println("\n******** Game Results *******\n");
			int highestScore = 0;
			int currentScore = 0;
			winner = "";
			for (int i = 0; i < numOfPlayers; ++i) {
				if (currentP.getPlaying()) {
					if (currentP.getName().equals(bidWinnerName)) {
						if (currentP.getWinCount() < highestBid) {
							currentP.updateGameScore(-5);
						} else {
							currentP.updateGameScore(currentP.getWinCount());
						}
					} else {
						if (currentP.getWinCount() == 0) {
							currentP.updateGameScore(-5);
						} else {
							currentP.updateGameScore(currentP.getWinCount());
						}
					}
				}
				currentScore = currentP.getGameScore();
				if (currentP.getPlaying()) {
					System.out.println(currentP.getName() + ": " + currentP.getWinCount() + " tricks won."
							+ " Game Score: " + currentScore);
				} else {
					System.out.println(currentP.getName() + " (Passed): " + currentP.getWinCount() + " tricks won."
							+ " Game Score: " + currentScore);
				}
				if(currentScore > highestScore) {
					highestScore = currentScore;
					winner = currentP.getName();
				}
				

				currentP.resetWinCount();
				currentP = itr.next();
			} //end of five-trick wrap-up
			
			if(highestScore >= 15) {
				itr = players.iterator();
				currentP = players.head.object;
				gameOver = true;
				for(int i = 0; i < numOfPlayers; ++i) {
					currentScore = currentP.getGameScore();
					if(currentScore == highestScore && !(currentP.getName().equals(winner))) {
						gameOver = false;
					}
					currentP = itr.next();
				}
			}
		} //end game while loop
		
		System.out.println("\n********Winner********\n" + winner);
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
