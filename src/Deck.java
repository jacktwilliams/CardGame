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
			
			indexSolo = 5;
			indexG1 = 6;
			indexG2 = 7;
			makePointers();
		}
		
		private void makePointers() {
			Node<AnyType> current = head;
			
			for(int i = 0; i < size - 1; i++) {
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
				
				current = current.next;
			}
			
			if(indexSolo + 1 == indexG1 || indexG2 + 1 == indexSolo) {
				adjacent = true;
			}
			
			System.out.println("preSolo" + preSolo + "\n" +
								"soloElement" + soloElement + "\n" +
								"preGroup" + preGroup + "\n" +
								"groupElement1" + groupElement1 + "\n");
			 
			
		}
	}
	
	private void indexAdd(int index, AnyType x) {
		Node<AnyType> current = head;
		
		//iterate to node before position of the addition
		for (int i = 0; i < index - 1; i++) {
			current = current.next;
		}

		if (index == 0) {
			Node<AnyType> addition = new Node<AnyType>(x, head);
			head = addition;
		} else if (index == size) {
			Node<AnyType> addition = new Node<AnyType>(x, null);
			current.next = addition;
		} else {
			Node<AnyType> addition = new Node<AnyType>(x, current.next);
			current.next = addition;
		}
		++this.size;
	}
	
	private AnyType indexRemove(int index) {
		Node<AnyType> current = head;
		AnyType removed = null;

		for (int i = 0; i < index - 1; i++) {
			current = current.next;
		}
		
		if(index != 0) { //if clause necessary in order to avoid out-of-bounds when size is 1
		removed = current.next.object; // save object to be removed
		}

		if (index == 0) {
			removed = head.object;
			head = current.next; // overwrite 'removed' to be head -- not current.next.
		} else if (index == size) {
			current.next = null;
		} else {
			current.next = current.next.next;
		}
		--size;
		return removed;
	}
	
	final int SHUFFLEX = 1;
	public void shuffle(){
		elementGroup tS; //toShuffle
		for(int i = 0; i < SHUFFLEX; i++){
			tS = new elementGroup();

			if (tS.adjacent) {
				if (tS.indexSolo < tS.indexG1) {
					tS.preSolo.next = tS.groupElement1;
					tS.preGroup.next = tS.groupElement2.next;
					tS.groupElement2.next = tS.preGroup;
				}
				else {
					tS.preGroup = tS.soloElement;
					tS.groupElement2.next = tS.soloElement.next;
					tS.soloElement.next = tS.groupElement1;
				}
			} else {

				tS.preSolo.next = tS.groupElement1;
				Node<AnyType> soloNext = tS.soloElement.next;
				tS.soloElement.next = tS.groupElement2.next;

				tS.groupElement2.next = soloNext;
				tS.preGroup.next = tS.soloElement;
			}
		} //end for loop		
	}
}
