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
	
	final int SHUFFLEX = 1000;
	public void shuffle(){
		elementGroup tS; //toShuffle
		for(int i = 0; i < SHUFFLEX; i++){
			tS = new elementGroup();
			
			tS.soloElement = this.indexRemove(tS.indexSolo);
			
			if(tS.indexSolo < tS.indexG1){
				tS.groupElement1 = this.indexRemove(tS.indexG1 - 1);//after removing solo element, this element index is one less than before
				tS.groupElement2 = this.indexRemove(tS.indexG2 - 2); // this element's index is now two less than when picked
				
				this.indexAdd(tS.indexSolo, tS.groupElement1);
				this.indexAdd(tS.indexSolo + 1, tS.groupElement2); //increase index by one to indexAdd second element after first
				this.indexAdd(tS.indexG1 + 1, tS.soloElement); //increase index by one because extra element was indexAdded towards head
			}
			else{
				tS.groupElement1 = this.indexRemove(tS.indexG1); 
				tS.groupElement2 = this.indexRemove(tS.indexG2 - 1); //after removing indexG1, this element's index is one less than before
				
				this.indexAdd(tS.indexSolo - 2, tS.groupElement1); //two elements missing towards head
				this.indexAdd(tS.indexSolo - 1, tS.groupElement2); //increase index by one to indexAdd second element after first
				this.indexAdd(tS.indexG1, tS.soloElement); 
			}
		} //end for loop		
	}
}
