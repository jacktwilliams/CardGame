import java.util.Iterator;

/*
 * Jack Williams
 * Class to simulate one Hand in the game of 'Clubs'
 */
public class ClubHand extends Pile<Card> {
	private Dealer gameDealer;
	private String trumpChoice = null;
	
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
		
		String dealerSuit = null;
		for(int i = 0; i < cardsPlayed.length; ++i) {
			if(cardsPlayed[i] != null) {
				dealerSuit = cardsPlayed[i].getSuit();
				
				if(cardsPlayed[i].getSuitValue() == 11 || cardsPlayed[i].getSuitValue() == 15){
					dealerSuit = gameDealer.getTrump();
				}
				
				i = cardsPlayed.length;
			}
		}
		
		if(dealerSuit == null) {
			dealerSuit = gameDealer.getTrump();
		}
		
		
			
		current = head.object;
		String currentSuit;
		boolean hasDSuit = false;
		String trump = gameDealer.getTrump();
		
		//check to see if we have a dealer suit card
		for(int i = 0; i < size; i++) {
			currentSuit = current.getSuit();

			if(currentSuit.equals("Joker")) {
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
		
		/*Jacks complimentary to trump suit have suitValue 11. Can be 12 if the jack is of the dealer suit*/
		if(hasDSuit) {
			boolean foundADSuit = false;
			for(int i = 0; i < size; ++i) {
				currentSuit = current.getSuit();
				if(currentSuit.equals("Joker")) {
					toPlay = current;
					foundADSuit = true;
				}	
				if(currentSuit.equals(dealerSuit) || current.getSuitValue() == 11 || current.getSuitValue() == 12) {
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
					if(currentSuit.equals(dealerSuit) || current.getSuitValue() == 11 || current.getSuitValue() == 12) {
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
			toPlay = current;
			// we have no card of dealer suit. Play best card we have if it can win.
			for (int i = 0; i < size; ++i) {

				if (current.getNumber() > toPlay.getNumber()) {
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

	public void refreshSuitValues(Card cardPlayed) {
		// TODO Auto-generated method stub
		Card current = head.object;
		Iterator<Card> itr = iterator();
		
		for(int i = 0; i < size; i++) {
			gameDealer.dynamicSetSuitValue(current, cardPlayed);
			
			current = itr.next();
		}
	}
	
	public String getTrumpChoice() {
		return this.trumpChoice;
	}

	public int getBid(int currentBid) {
		Card current = head.object;
		this.trumpChoice = head.object.getSuit(); //in case this never gets changed. If we have any jacks it will.
		if(trumpChoice == "Joker"){
			trumpChoice = "Clubs";
		}
		Iterator<Card> itr = iterator();
	
		int bidAmt = 0;		
		Pile<String> jacks = new Pile<String>();
		String jackOpt = null;
		String jackComp = null;
		boolean foundJack = false;
		boolean compJacks = false;
		
		for(int i = 0; i < size; ++i) {
			if(current.getSuit().equals("Joker")) {
				++bidAmt;
			}
			
			if (current.getNumber() == 11) {
				jackOpt = current.getSuit();
				jacks.add(jackOpt);
				
				if(!foundJack) {
					++bidAmt;
					foundJack = true; //only add one bid if jack is found.. for starters
				}
			}
			current = itr.next();
		}
		if(jacks.size() == 4) {
			++bidAmt; //guaranteed to have complimentary jacks
		}
		else if(jacks.size() > 1) {
			jackOpt = jacks.head.object;
			Iterator<String> jitr = jacks.iterator();
			jackComp = jacks.head.object;
			Iterator<String> jitr2 = jacks.iterator();
			
			for(int i = 0; i < jacks.size() - 1; ++i) {
				for(int x = i + 1; x < jacks.size(); ++x) {
					for(int y = 0; y < x; ++y) {
						jackComp = jitr2.next();
					} // this loop navigates so that jackComp starts as object located one after jackOpt
					if(11 == gameDealer.getSuitValue(jackComp, jackOpt, 11)) {
						++bidAmt; //found complimentary jacks!
						compJacks = true;
						i = jacks.size(); //break out of loop! Only add one bid for complimentary jacks
						x = jacks.size();
					}
					
					if(i != jacks.size()) {
						//algorithm not stopped, reset itr for next loop
						jackComp = jacks.head.object;
						jitr2 = jacks.iterator();
					}
				}
				if (i != jacks.size()) {
					jackOpt = jitr.next();
				}
			}
		}
		
		/* We have added a bid for any joker, any jack, and one more if we have complimentary jacks.
		 * If we have aces that fit either of our jacks, pick suit accordingly and add bid. 
		 */
		
		current = this.head.object;
		itr = iterator();
		
		if (compJacks) {
			this.trumpChoice = jackOpt; // start by picking one of the
										// complimentary jack suits
			for (int i = 0; i < this.size; ++i) {
				if (current.getNumber() == 14) {
					// jackOpt/Comp still hold suits of two complimentary jacks
					if (current.getSuit().equals(jackOpt)) {
						++bidAmt;
						this.trumpChoice = jackOpt;
						i = this.size;
					}
					else if (current.getSuit().equals(jackComp)) {
						++bidAmt;
						this.trumpChoice = jackComp;
						i = this.size;
					}
				}
				current = itr.next();
			}
		}
		
		/*if we have four bids (one joker, two complimentary jacks, 
		 * and one ace that matches one of those jacks) then add a fifth bid if we have a king that matches
		 * our current trump choice.
		 */
		
		if(compJacks) {
			current = this.head.object;
			itr = iterator();
			
			for(int i = 0; i < this.size; ++i) {
				if(current.getNumber() == 13) {
					if(current.getSuit().equals(this.trumpChoice)) {
						++bidAmt;
						i = this.size;
					}
				}
			}
		}
		return bidAmt;
	}
	
	public boolean getEntry() {
		String trump = gameDealer.getTrump();
		int trickAmt = 0;
		
		Iterator<Card> itr = iterator();
		Card current = this.head.object;
		
		int suitValue;
		
		for(int i = 0; i < this.size; ++i) {
			suitValue = gameDealer.getSuitValue(trump, current.getSuit(), current.getNumber());
			
			if(current.getSuit().equals("Joker")) {
				return true;
			}
			else if(suitValue > 10) {
				//complimentary jack's or trump jacks 
				++trickAmt;
			}
			else if(suitValue == 9 && current.getNumber() >= 13) {
				//ace or king of trump suit
				++trickAmt;
			}
			current = itr.next();
		}
		
		if(trickAmt - 1 > 0) {
			//cautious playing... conservative
			return true;
		}
		else {
			return false;
		}
	}
	

	
}

