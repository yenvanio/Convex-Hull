package ConvexHull;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class QStack<Item> implements Iterable<Item> {

	private Node<Item> first; // top of stack
	private int n; // size of the stack

	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	public QStack() {
		first = null;
		n = 0;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;
	}

	public void push(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	public Item pop() {
		if (isEmpty())
			throw new NoSuchElementException("Error");
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}

	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Error");
		return first.item;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this) {
			s.append(item);
			s.append(' ');
		}
		return s.toString();
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}


}
