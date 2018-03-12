import java.util.Iterator;

/*
 * Jack Williams
 * Class to simulate one Hand in the game of 'Clubs'
 */
public class ClubHand extends Pile<Card> {
	private Dealer gameDealer;
	
	public ClubHand(){
		
	}
	
	private Card bestPlay(){
		Card toPlay = null;
		Card bestCard = null;
		Card current = null;
		
		// first find best card played so far
		
		Iterator<Card> itr = iterator();		
		
		Card[] cardsPlayed = gameDealer.getCardsPlayed();
		bestCard = cardsPlayed[0];

		
		for(int i = 0; i < cardsPlayed.length; ++i){
			if(cardsPlayed[i].getSuitValue() > bestCard.getSuitValue()){
				bestCard = cardsPlayed[i];
			}
			else if(cardsPlayed[i].getSuitValue() == bestCard.getSuitValue() &&
					cardsPlayed[i].getNumber() > bestCard.getNumber()) {
				bestCard = cardsPlayed[i];
			}
		}
		
		String dealerSuit = cardsPlayed[0].getSuit();
		current = head.object;
		String currentSuit = current.getSuit();
		boolean hasDSuit = false;
		String trump = gameDealer.getTrump();
		
		//check to see if we have a dealer suit card
		for(int i = 0; i < size; i++) {
			if(dealerSuit.equals(trump) && currentSuit.equals("Joker")) {
				hasDSuit = true;
			}
			if(currentSuit.equals(dealerSuit)) {
				hasDSuit = true;
			}
			current = itr.next();
			currentSuit = current.getSuit();
		}
		
		current = head.object;
		itr = iterator();
		currentSuit = current.getSuit();
		
		
		if(hasDSuit) {
			boolean foundADSuit = false;
			for(int i = 0; i < size; ++i) {
				if(dealerSuit.equals(trump) && currentSuit.equals("Joker")) {
					toPlay = current;
				}
				
				if(currentSuit.equals(dealerSuit)) {
					//toPlay starts as null, must find first DSuit card for comparisons
					if(!foundADSuit) {
						toPlay = current;
						foundADSuit = true;
					}
					else if (current.getSuitValue() > toPlay.getSuitValue() || 
							(current.getSuitValue() == toPlay.getSuitValue() 
							&& current.getNumber() > toPlay.getNumber()))
					{
						toPlay = current;
					}
				}
				current = itr.next();
				currentSuit = current.getSuit();
			}
			//if toPlay can't win, find worse card of DSuit
			if(bestCard.getSuitValue() > toPlay.getSuitValue() 
					|| (bestCard.getSuitValue() == toPlay.getSuitValue() 
					&& bestCard.getNumber() > toPlay.getNumber())) {
				current = head.object;
				itr = iterator();
				currentSuit = current.getSuit();
				for(int i = 0; i < size; ++i) {
					if(currentSuit.equals(dealerSuit)) {
						if(current.getSuitValue() < toPlay.getSuitValue() ||
								(current.getSuitValue() == toPlay.getSuitValue() 
								&& current.getNumber() < toPlay.getNumber())){
							toPlay = current;
						}
					}
					current = itr.next();
					currentSuit = current.getSuit();
				}
			}
		}
		else {
			//we have no card of dealer suit. Play best card we have if it can win.
			for(int i = 0; i < size; ++i) {
				if(current.getSuitValue() > bestCard.getSuitValue() ||
						(current.getSuitValue() == bestCard.getSuitValue() 
						&& current.getNumber() > bestCard.getNumber())) {
					toPlay = current;
				}
				current = itr.next();
				currentSuit = current.getSuit();
			}
			
			if (toPlay.getSuitValue() < bestCard.getSuitValue() || (toPlay.getSuitValue() == bestCard.getSuitValue()
					&& toPlay.getNumber() < bestCard.getNumber())) {
				//card can't win. Find worst card
				current = head.object;
				itr = iterator();
				currentSuit = current.getSuit();
				for (int i = 0; i < size; ++i) {

					if (current.getSuitValue() < toPlay.getSuitValue()
							|| (current.getSuitValue() == toPlay.getSuitValue()
									&& current.getNumber() < toPlay.getNumber())) {
						toPlay = current;

					}
					current = itr.next();
					currentSuit = current.getSuit();
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
