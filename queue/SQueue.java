package queue;


public class SQueue implements IQueue {
	
	private SNode first;
	private SNode last;
	int size;

	public boolean isEmpty() {
		return first == null;
	}
	
	public void enqueue(String elem) {
		SNode node = new SNode(elem);
		if (isEmpty()) {
			first = node;
		} else {
			last.next = node;
		}
		last = node;
		size++;
	}

	public String dequeue() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
		} 
		
		String firstElem = first.elem;
		first = first.next;
		if (first == null) {
			last = null;
		}
		size--;
		return firstElem;
		
	}

	public String front() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
			return null;
		}
		return first.elem;
	}

	@Override
	public String toString() {
		String result = null;
		for (SNode nodeIt = first; nodeIt != null; nodeIt = nodeIt.next) {
			if (result == null) {
				result = "[" + nodeIt.elem.toString() + "]";
			} else {
				result += "," + nodeIt.elem.toString();
			}
		}
		return result == null ? "empty" : result;
	}

	


	@Override
	public int getSize() {
		return size;
	}
}
