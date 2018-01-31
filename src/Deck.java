import java.util.Random;

public class Deck<AnyType extends Comparable<AnyType>> extends Pile<AnyType> {
	private class elementGroup {
		private AnyType soloElement;
		private AnyType groupElement1;
		private AnyType groupElement2;
		int indexSolo, indexG1, indexG2;

		private elementGroup() {
			Random randr = new Random();
			indexG1 = randr.nextInt(size - 1);

			if (indexG1 == size - 1) { //special case where groupE1 is at end of deck
				indexG2 = size - 2;
			} else {
				indexG2 = indexG1 + 1;
			}
			
			indexSolo = indexG1;
			while(indexSolo == indexG1 || indexSolo == indexG2){
				indexSolo = randr.nextInt(size - 1);
			}
		}
	}
	
	final int SHUFFLEX = 1000;
	public void shuffle(){
		elementGroup tS; //toShuffle
		for(int i = 0; i < SHUFFLEX; i++){
			tS = new elementGroup();
			
			tS.soloElement = this.remove(tS.indexSolo);
			
			if(tS.indexSolo < tS.indexG1){
				tS.groupElement1 = this.remove(tS.indexG1 - 1);//after removing solo element, this element index is one less than before
				tS.groupElement2 = this.remove(tS.indexG2 - 2); // this element's index is now two less than when picked
				
				this.add(tS.indexSolo, tS.groupElement1);
				this.add(tS.indexSolo + 1, tS.groupElement2); //increase index by one to add second element after first
				this.add(tS.indexG1 + 1, tS.soloElement); //increase index by one because extra element was added towards head
			}
			else{
				tS.groupElement1 = this.remove(tS.indexG1); 
				tS.groupElement2 = this.remove(tS.indexG2 - 1); //after removing indexG1, this element's index is one less than before
				
				this.add(tS.indexSolo - 2, tS.groupElement1); //two elements missing towards head
				this.add(tS.indexSolo - 1, tS.groupElement2); //increase index by one to add second element after first
				this.add(tS.indexG1, tS.soloElement); 
			}
		} //end for loop		
	}
}
