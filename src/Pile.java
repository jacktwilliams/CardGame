import java.util.Iterator;

/*
 * Author: Jack Williams
 * Due Date: 02/05/2018
 * Pile is a generic class that defines a linked list. I plan to use it to simulate a deck of cards.
 */

public class Pile<AnyType extends Comparable<AnyType>> {
	protected Node<AnyType> head;
	protected int size = 0;

	protected static class Node<AnyType> {
		protected AnyType object;
		protected Node<AnyType> next;

		protected Node(AnyType obj, Node<AnyType> next) {
			object = obj;
			this.next = next;
		}
		
		public String toString() {
			return object.toString();
		}
	}

	public Pile() {
		head = new Node<AnyType>(null, null);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		head = null;
		size = 0;
	}

	public boolean contains(AnyType x) {
		boolean contains = false;
		Iterator<AnyType> itr = iterator();
		AnyType current = head.object;
		for (int i = 0; i < size; i++) {
			if (current.compareTo(x) == 0) {
				contains = true;
				i = size; // stop searching
			}
			current = itr.next();
		}
		return contains;
	}

	public void add(AnyType x) {
		head = new Node<AnyType>(x, head);
		++size;
	}

	public AnyType remove() {
		AnyType removed = null;

		removed = head.object;
		head = head.next;
		--size;
		return removed;
	}
	
	protected void indexAdd(int index, AnyType x) {
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
	
	@SuppressWarnings("unused")
	protected AnyType indexRemove(int index) {
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
	
	//toString results in empty pile
	/*
	public String toString(){
		String result = "";
		result += String.valueOf(size) + "\n";
		int loops = this.size; //size will decrease so we need a constant
		for(int i = 0; i < loops; i++){
			result += this.remove() + " ";
		}
		return result;
	}
	*/
	
	public String toString(){
		String result = "";
		Iterator<AnyType> itr = iterator();
		AnyType current = head.object;
		
		for(int i = 0; i < size; ++i){
			result += current.toString() + "\n";
			current = itr.next();
		}
		return result;
	}

	public java.util.Iterator<AnyType> iterator() {
		return new PileIterator(head);
	}

	private class PileIterator implements java.util.Iterator<AnyType> {

		private Node<AnyType> current;

		public PileIterator(Node<AnyType> head) {
			current = head;
		}

		@Override
		public boolean hasNext() {
			return current.next != null;
		}

		@Override
		public AnyType next() {
			if (this.hasNext()) {
				current = current.next;
				AnyType item = current.object;
				return item;
			}
			return null;
		}

	}

}
