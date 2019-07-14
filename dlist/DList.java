package dlist;

/** Esta es una implementación de la DList con algunos métodos añadidos que se citan
 * adelante, creados para poder realizar la práctica. Se han puesto justo debajo del
 * constructor para facilitar su busqueda.
 * 
 * public DList copy(): Devuelve una copia de la lista.
 * public void sortDesc(): Ordena la lista en orden descendente.
 * public void sortAsc(): Ordena la lista en orden ascendente.
 * public DNode getNodeAt(int index): Devuelve el nodo asociado al index dado.
 * 
 **/

public class DList implements IList {

	DNode header;
	DNode trailer;
	int size = 0;

	public DList() {
		
		header = new DNode(null);
		trailer = new DNode(null);
		
		header.next = trailer;
		trailer.prev= header;
		
	}
	
	/** Devuelve una copia de la lista
	 * @return DList
	 **/
	public DList copy(){
		
		DList newList = new DList();
		for (DNode nodeIt = header.next; nodeIt != trailer; nodeIt = nodeIt.next) {
			newList.addLast(String.valueOf(nodeIt.elem));
		}
		return newList;
		
	}
	
	/** Adaptación del método de la burbuja que ordena descendentemente la lista.
	 **/
	public void sortDesc(){
		
		String elem1;
		
		for (DNode nodeTemp = header.next; nodeTemp.next != trailer; nodeTemp = nodeTemp.next) {
			
			for (DNode nodeIt = header.next; nodeIt.next != trailer; nodeIt = nodeIt.next) {
				
				if(nodeIt.elem.compareTo(nodeIt.next.elem)<0){
					
					elem1 = nodeIt.elem;
					
					nodeIt.elem = nodeIt.next.elem;
					
					nodeIt.next.elem = elem1;
				
				}
			}
		}
	}
	
	/** Adaptación del método de la burbuja que ordena ascendentemente la lista.
	 **/
	public void sortAsc(){
		
		String elem1;
		
		for (DNode nodeTemp = header.next; nodeTemp.next != trailer; nodeTemp = nodeTemp.next) {
			
			for (DNode nodeIt = header.next; nodeIt.next != trailer; nodeIt = nodeIt.next) {
				
				if(nodeIt.elem.compareTo(nodeIt.next.elem)>0){
					
					elem1 = nodeIt.elem;
					
					nodeIt.elem = nodeIt.next.elem;
					
					nodeIt.next.elem = elem1;
				
				}
			}
		}
	}
	
	/** Devuelve el nodo asociado en la posicion index.
	 * @return DNode
	 **/
	public DNode getNodeAt(int index){
		
		int i = 0;
		DNode result=null;
		for (DNode nodeIt = header.next; nodeIt != trailer && result==null; nodeIt = nodeIt.next) {
			if (i == index) {
				result=nodeIt;
			}
			++i;
		}
		if (result==null) System.out.println("DList: Get out of bounds");
		return result;
	}
	
	public void addFirst(String elem) {
		
		DNode newNode = new DNode(elem);
		newNode.next = header.next;
		newNode.prev= header;
		header.next.prev= newNode;
		header.next = newNode;
		size++;
	}

	
	public void addLast(String elem) {
		DNode newNode = new DNode(elem);
		newNode.next = trailer;
		newNode.prev= trailer.prev;
		trailer.prev.next = newNode;
		trailer.prev= newNode;
		size++;
	}
	
	public void insertAt(int index, String elem) {
		DNode newNode = new DNode(elem);
		int i = 0;
		boolean inserted=false;
		for (DNode nodeIt = header; nodeIt != trailer && inserted==false; nodeIt = nodeIt.next) {
			if (i == index) {
				newNode.next = nodeIt.next;
				newNode.prev= nodeIt;
				nodeIt.next.prev= newNode;
				nodeIt.next = newNode;
				inserted=true;
				size++;
			}
			++i;
		}
		if (!inserted) System.out.println("DList: Insertion out of bounds");
	}
	
	public void replaceAt(int index, String elem) {
		
		int i = 0;
		boolean found = false;
		
		for (DNode nodeIt = header.next; nodeIt != trailer && found==false; nodeIt = nodeIt.next) {
			if (i == index) {
				nodeIt.elem=elem;
				found = true;
			}
			++i;
		}
	}

	public boolean isEmpty() {
		return (header.next == trailer);
	}

	
	public boolean contains(String elem) {
		boolean found=false;
		for (DNode nodeIt = header.next; nodeIt != trailer && found==false; nodeIt = nodeIt.next) {
			if (nodeIt.elem.equals(elem)) {
				found=true;
			}
		}
		return found;
	}
	
	public int getIndexOf(String elem) {
		int index = -1;
		int pos=0;
		for (DNode nodeIt = header.next; nodeIt != trailer && index==-1; nodeIt = nodeIt.next) {
			if (nodeIt.elem.equals(elem)) {
				index=pos;
			} 
			++pos;
			
		}
		return index;
	}
	
	public DNode getNodeOf(String elem) {
		
		DNode node = null;
		for (DNode nodeIt = header.next; nodeIt != trailer && node==null; nodeIt = nodeIt.next) {
			if (nodeIt.elem.equals(elem)) {
				node=nodeIt;
			} 
		}
		return node;
	}
	
	public void removeFirst() {
		if (isEmpty()) {
			System.out.println("DList: List is empty");
			return;
		}
		header.next = header.next.next;
		header.next.prev= header;
		size--;
	}

	
	public void removeLast() {
		if (isEmpty()) {
			System.out.println("DList: List is empty");
			return;
		}
		trailer.prev= trailer.prev.prev;
		trailer.prev.next = trailer;
		size--;

	}

	
	public void removeAll(String elem) {
		for (DNode nodeIt = header.next; nodeIt != trailer; nodeIt = nodeIt.next) {
			if (nodeIt.elem.equals(elem)) {
				nodeIt.prev.next = nodeIt.next;
				nodeIt.next.prev= nodeIt.prev;
				size--;

			}
		}
	}

	
	
	public void removeAt(int index) {
		int i = 0;
		boolean removed=false;
		for (DNode nodeIt = header.next; nodeIt != trailer && removed==false; nodeIt = nodeIt.next) {
			if (i == index) {
				nodeIt.prev.next = nodeIt.next;
				nodeIt.next.prev= nodeIt.prev;
				removed=true;
				size--;

			}
			++i;
		}
		if (!removed) System.out.println("DList: Deletion out of bounds");
	}

	
	public int getSize() {
		
		return size;
	}

	public String getFirst() {
		String result=null;
		if (isEmpty()) {
			System.out.println("DList: List is empty");
		} else result=header.next.elem;
		return result;
	}

	public String getLast() {
		String result=null;

		if (isEmpty()) {
			System.out.println("DList: List is empty");
		} else result=trailer.prev.elem;
		
		return result;
	}
	
	public String getAt(int index) {
		int i = 0;
		String result=null;
		for (DNode nodeIt = header.next; nodeIt != trailer && result==null; nodeIt = nodeIt.next) {
			if (i == index) {
				result=nodeIt.elem;
			}
			++i;
		}
		if (result==null) System.out.println("DList: Get out of bounds");
		return result;
	}
	
	public void flip(int index){
		
		DNode auxNode1 = header.next;
		DNode auxNode2 = null;
		
		for (int i = 0; i < index; i++)
			auxNode1 = auxNode1.next;
		
		auxNode2 = auxNode1.next;
		
		auxNode1.prev.next = auxNode2;
		auxNode2.next.prev = auxNode1;
		
		auxNode1.next = auxNode2.next;
		auxNode2.prev = auxNode1.prev;
		
		auxNode1.prev = auxNode2;
		auxNode2.next = auxNode1;
		
	}

	public String toString() {
		String result = null;
		for (DNode nodeIt = header.next; nodeIt != trailer; nodeIt = nodeIt.next) {
			if (result == null) {
				result = String.valueOf(nodeIt.elem);
			} else {
				result += ", " + String.valueOf(nodeIt.elem);
			}
		}
		return result == null ? "empty" : result;
	}
	
	

}
