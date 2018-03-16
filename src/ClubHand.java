import java.util.Iterator;

/*
 * Jack Williams
 * Class to simulate one Hand in the game of 'Clubs'
 */
public class ClubHand extends Pile<Card> {
	private Dealer gameDealer;
	
	public ClubHand(){
		
	}
	
	public Card bestPlay(){
		Card toPlay = null;
		Card bestCard = null;
		Card current = null;
		
		// first find best card played so far
		
		Iterator<Card> itr = iterator();		
		
		Card[] cardsPlayed = gameDealer.getCardsPlayed();
		if(cardsPlayed[0] == null) {
			bestCard = new Card(-1, "null", -1);
		}
		else {
		bestCard = cardsPlayed[0];
		}

		for (int i = 0; i < cardsPlayed.length; ++i) {
			if (cardsPlayed[i] != null) {
				if (cardsPlayed[i].getSuitValue() > bestCard.getSuitValue()) {
					bestCard = cardsPlayed[i];
				} else if (cardsPlayed[i].getSuitValue() == bestCard.getSuitValue()
						&& cardsPlayed[i].getNumber() > bestCard.getNumber()) {
					bestCard = cardsPlayed[i];
				}
			}
		}
		
		String dealerSuit;
		if(cardsPlayed[0] == null) {
			dealerSuit = gameDealer.getTrump();
		}
		else {
			dealerSuit = cardsPlayed[0].getSuit();
		}
		current = head.object;
		String currentSuit;
		boolean hasDSuit = false;
		String trump = gameDealer.getTrump();
		
		//check to see if we have a dealer suit card
		for(int i = 0; i < size; i++) {
			currentSuit = current.getSuit();

			if(dealerSuit.equals(trump) && currentSuit.equals("Joker")) {
				hasDSuit = true;
			}
			else if(current.getSuitValue() == 11) { 
				//a complimentary jack is part of trump suit
				hasDSuit = true;
			}
			else if(currentSuit.equals(dealerSuit)) {
				hasDSuit = true;
			}
			current = itr.next();
		}
		
		current = head.object;
		itr = iterator();
		
		/*Note that jacks and complimentary jacks have had suitValue increased in ClubDeck.*/
		if(hasDSuit) {
			boolean foundADSuit = false;
			for(int i = 0; i < size; ++i) {
				currentSuit = current.getSuit();
				if(dealerSuit.equals(trump) && currentSuit.equals("Joker")) {
					toPlay = current;
					foundADSuit = true;
				}	
				if(currentSuit.equals(dealerSuit) || current.getSuitValue() == 11) {
					//toPlay starts as null, must find first DSuit card for comparisons
					if(!foundADSuit) {
						toPlay = current;
						foundADSuit = true;
					}
					//have already found a dealer suit. Is this card better than other dsuit card?
					else if (current.getSuitValue() > toPlay.getSuitValue() || 
							(current.getSuitValue() == toPlay.getSuitValue() 
							&& current.getNumber() > toPlay.getNumber()))
					{
						toPlay = current;
					}
				}
				current = itr.next();
			}
			//if toPlay can't win, find worst card of DSuit
			if(bestCard.getSuitValue() > toPlay.getSuitValue() 
					|| (bestCard.getSuitValue() == toPlay.getSuitValue() 
					&& bestCard.getNumber() > toPlay.getNumber())) {
				current = head.object;
				itr = iterator();
				for(int i = 0; i < size; ++i) {
					currentSuit = current.getSuit();
					if(currentSuit.equals(dealerSuit) || current.getSuitValue() == 11) {
						if(current.getSuitValue() < toPlay.getSuitValue() ||
								(current.getSuitValue() == toPlay.getSuitValue() 
								&& current.getNumber() < toPlay.getNumber())){
							toPlay = current;
						}
					}
					current = itr.next();
				}
			}
		}
		else {
<<<<<<< HEAD
			// we have no card of dealer suit. Play best card we have if it can win.
			for (int i = 0; i < size; ++i) {

				if (current.getSuitValue() > bestCard.getSuitValue()
						|| (current.getSuitValue() == bestCard.getSuitValue()
								&& current.getNumber() > bestCard.getNumber())) {
=======
			toPlay = current;
			// we have no card of dealer suit. Play best card we have if it can win.
			for (int i = 0; i < size; ++i) {

				if (current.getNumber() > toPlay.getNumber()) {
>>>>>>> SuitValueFix
					toPlay = current;
				}

				current = itr.next();
			}

			// can toPlay win?
			if (toPlay.getSuitValue() < bestCard.getSuitValue() || (toPlay.getSuitValue() == bestCard.getSuitValue()
					&& toPlay.getNumber() < bestCard.getNumber())) {
				// card can't win. Find worst card
				current = head.object;
				itr = iterator();
				for (int i = 0; i < size; ++i) {
					currentSuit = current.getSuit();
					
					if (current.getSuitValue() < toPlay.getSuitValue()
							|| (current.getSuitValue() == toPlay.getSuitValue()
									&& current.getNumber() < toPlay.getNumber())) {
						toPlay = current;

					}
					current = itr.next();
				}
			}
		}

		//find index of card to play and delete it from hand
		current = head.object;
		itr = iterator();
		int i;
		for(i = 0; i < size; ++i) {
			if(current.compareTo(toPlay) == 0) {
				break;
			}
			current = itr.next();
		}
		
		this.indexRemove(i);
		
		return toPlay;
	}
	
	
	
	public void setDealer(Dealer d){
		this.gameDealer = d;
	}
	

	
}
