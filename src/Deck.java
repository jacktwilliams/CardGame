import java.util.Random;

/*
 * Author: Jack Williams
 * Deck is a child of Pile, which is a linked list. Add and remove are specified in Pile, and operate strictly at the head of the list.
 * This class holds private methods, indexAdd and indexRemove, which operate on a given index. 
 * The inner class, elementGroup, has a constructor which picks a random element and a random two consecutive elements.
 * An element group object also holds AnyType objects, which seemed preferable to using local variables inside of shuffle.
 * The shuffle method swaps the element with the group of elements.
 */

public class Deck<AnyType extends Comparable<AnyType>> extends Pile<AnyType> {
	private class elementGroup {
		private Node<AnyType> soloElement;
		private Node<AnyType> groupElement1;
		private Node<AnyType> groupElement2;
		private Node<AnyType> preSolo;
		private Node<AnyType> preGroup;
		
		int indexSolo, indexG1, indexG2;
		
		boolean adjacent = false, itemAtHead = false;

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
			
			makePointers();
		}
		
		private void makePointers() {
			Node<AnyType> current = head;
						
			for(int i = 0; i < size; i++) {
				if(i == indexSolo -1) {
					preSolo = current;
				}
				if (i == indexSolo) {
					soloElement = current;
				}
				
				if (i == indexG1 - 1) {
					preGroup = current;
				}
				if(i == indexG1) {
					groupElement1 = current;
				}
				if(i == indexG2) {
					groupElement2 = current;
				}

				if( i != size-1) {
					current = current.next;
				}
			}
			
			if(indexSolo + 1 == indexG1 || indexG2 + 1 == indexSolo) {
				adjacent = true;
			}
			
			if((indexSolo == 0) || (indexG1 == 0)) {
				itemAtHead = true;
			}

		}
	}
	
	final int SHUFFLEX = 1000;
	public void shuffle(){
		elementGroup tS; //toShuffle
		for(int i = 0; i < SHUFFLEX; i++){
			tS = new elementGroup();

			if(tS.adjacent && tS.itemAtHead) {
				if(tS.indexSolo < tS.indexG1) {
					head = tS.groupElement1;
					tS.soloElement.next = tS.groupElement2.next;
					tS.groupElement2.next = tS.soloElement;
				}
				else {
					tS.groupElement2.next = tS.soloElement.next;
					tS.soloElement.next = tS.groupElement1;
					head = tS.soloElement;
				}
			}
			else if (tS.adjacent) {
				if (tS.indexSolo < tS.indexG1) {
					tS.preSolo.next = tS.groupElement1;
					tS.preGroup.next = tS.groupElement2.next;
					tS.groupElement2.next = tS.preGroup;
				}
				else {
					tS.preGroup.next = tS.soloElement;
					tS.groupElement2.next = tS.soloElement.next;
					tS.soloElement.next = tS.groupElement1;
				}
			} 
			else if(tS.itemAtHead){
				if (tS.indexSolo < tS.indexG1) {
					Node<AnyType> g2Next = tS.groupElement2.next;
					
					tS.preGroup.next = tS.soloElement;
					head = tS.groupElement1;
					tS.groupElement2.next = tS.soloElement.next;
					tS.soloElement.next = g2Next;
				}
				else {
					Node<AnyType> soloNext = tS.soloElement.next;
					
					tS.soloElement.next = tS.groupElement2.next;
					head = tS.soloElement;
					tS.groupElement2.next = soloNext;
					tS.preSolo.next = tS.groupElement1;
				}
				
			}
			else {

				tS.preSolo.next = tS.groupElement1;
				Node<AnyType> soloNext = tS.soloElement.next;
				tS.soloElement.next = tS.groupElement2.next;

				tS.groupElement2.next = soloNext;
				tS.preGroup.next = tS.soloElement;
			}
		} //end for loop		
	}
}
