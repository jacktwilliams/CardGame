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
		
		
		
		return toPlay;
	}
	
	
	public void setDealer(Dealer d){
		this.gameDealer = d;
	}
	

	
}
