package dlist;

/**
 * Lista de objetos de tipo String 
 * @author isegura
 *
 */
public interface IListVertex {

	public void addFirst(String v, float f);

	public void addLast(String v, float f);

	public void removeFirst();

	public void removeLast();
	
	
	public void insertAt(int index, String v, float f);
	
	public boolean isEmpty();

	public boolean contains(String v);

	public int getSize();

	public int getIndexOf(String v);

	public String getFirst();

	public String getLast();

	public DNodeVertex getAt(int index);

	public String toString();

	public void removeAll(String vertex);

	public void removeAt(int index);

}


