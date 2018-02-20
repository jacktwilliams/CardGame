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
	
	//toString results in empty pile
	public String toString(){
		String result = "";
		result += String.valueOf(size) + "\n";
		int loops = this.size; //size will decrease so we need a constant
		for(int i = 0; i < loops; i++){
			result += this.remove() + " ";
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
