package queue;

public interface IQueue {

	public boolean isEmpty();

	public void enqueue(String elem);

	public String dequeue();

	public String front();
	
	public int getSize();
	

}

