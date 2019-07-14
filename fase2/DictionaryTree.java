package fase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dlist.DList;

import queue.SQueue;

public class DictionaryTree implements IDictionaryTree {
	
	BSTNode root;
	
	@Override
	/** Recibe una cola que alimentará el diccionario. Cada elemento de la cola es añadido
	 * a la estructura hasta que la cola queda vacía.
	 * Complejidad: O(nlog n)
	 * @param SQueue queue
	 **/
	public void add(SQueue queue) {

		while(!queue.isEmpty())
			add(queue.dequeue());
	}
	
	@Override
	/** Recibe una palabra que será añadida al diccionario. Si no existe, se añade al final,
	 * si existe, se busca y se aumenta su frecuencia.
	 * Complejidad: O(log n)
	 * @param String word
	 **/
	public void add(String word){
		
		BSTNode aux = findNode(word);
		
		if(aux==null)
			insert(word, 1);
		else
			aux.elem++;
	}
	
	// Complejidad: O(log(n))
	public Integer find(String key) {
		
		return find(root, key);
	}
	
	// Complejidad: O(log(n))
	protected static Integer find(BSTNode node, String key) {
		
		if(node!=null){
			if (key.equals(node.key))
				return node.elem;
			if (key.compareTo(node.key) < 0)
				return find(node.left, key);
			else
				return find(node.right, key);
		}
		
		return null;
	}
	
	// Complejidad: O(log(n))
	public BSTNode findNode(String key) {
		
		return findNode(root, key);
	}
	
	// Complejidad: O(log(n))
	protected static BSTNode findNode(BSTNode node, String key) {
		
		if(node!=null){
			if (key.equals(node.key))
				return node;
			if (key.compareTo(node.key) < 0)
				return findNode(node.left, key);
			else
				return findNode(node.right, key);
		}
		return null;
	}
	
	// Complejidad: O(log(n))
	protected static BSTNode findMin(BSTNode node){
		
		return node.findMin();
	}
	
	// Complejidad: O(log(n))
	public void insert(String key, Integer element) {
		
		BSTNode newNode = new BSTNode(key,element);
		if (root == null)
			root = newNode;
		else
			insert(newNode, root);
	}
	
	// Complejidad: O(log(n))
	protected static void insert(BSTNode newNode, BSTNode node) {
		
		String key = newNode.key;
		if (key.compareTo(node.key) == 0) {
			System.out.println(key + " already exists. Duplicates are not allowed!!!.");
			return;
		}
		if (key.compareTo(node.key)<0) {
			if (node.left == null) {
				node.left = newNode;
				newNode.parent = node;
			} else insert(newNode,node.left);
		} else {
			if (node.right == null) {
				node.right = newNode;
				newNode.parent = node;
			} else insert(newNode,node.right);
		}
		
	}
	
	public void remove(String key) {
		
		remove(key,root);
	}

	private BSTNode remove(String key, BSTNode node) {
		
		if (node == null) {
			System.out.println("Cannot remove: The key doesn't exist");
			return null;
		}
		
		if (key.compareTo(node.key)<0) {
			
			node.left=remove(key,node.left);
			return node;
			
		}
			
		if (key.compareTo(node.key)>0) {
			
			node.right=remove(key,node.right);
			return node;
		
		}
		
		if (key.compareTo(node.key)==0) {
			
			if(node.left==null&&node.right==null)
				return null;
			
			if(node.left!=null&&node.right==null){
				
				node.left.parent = node.parent;
				return node.left;
			}
			
			if(node.right!=null&&node.left==null){
				
				node.right.parent = node.parent;
				return node.right;
			}
			
			BSTNode sucesorNode = findMin(node.right);
			node.elem = sucesorNode.elem;
			node.key = sucesorNode.key;
			node.right = remove(sucesorNode.key, node.right);
			return node.right;
		}
		return null;
		
	}

	public void showPreorder() {
		root.showPreorder();

	}
	
	public void showInorder() {
		
		 root.showInorder();
	}
	
	public void showPostorder() {
		 root.showPostorder();
	}
	
	public int getSize() {
		return root.getSize();
	}
	
	public int getHeight() {
		return root.getHeight();
	}
	
	public int getDepth() {
		return root.getDepth();
	}

	@Override
	/** Muestra el diccionario completo ordenado alfabéticamente en orden ascendente si recibe
	 * 'A', en caso contrario lo mostrará en orden descendente.
	 * Complejidad: O(log n)
	 * @param char c
	 **/
	public void show(char c) {
		
		if(c=='A')
			root.showInorder();
		else
			root.showPostorder();
		System.out.println();
		
	}

	@Override
	/** Busca la palabra recibida por parámetro y devuelve su frecuencia.
	 * Complejidad: O(log n)
	 * @param String word
	 * @return int
	 **/
	public int search(String word) {
		
		return findNode(word).elem;
	}

	@Override
	/** Devuelve una lista con las n palabras con mayor frecuencia.
	 * Complejidad: O(n2)
	 * @param int n
	 * @return DList
	 **/
	public DList getTop(int n) {
		
		if(n>getSize())
			n = getSize();
		
		DList treeList = root.getPostorder();
		treeList.sortDesc();
		DList list = new DList();
		for (int i = 0; i < n; i++) {
			list.addLast(treeList.getAt(i));
		}
		
		return list;
	}

	@Override
	/** Devuelve una lista con las n palabras con menor frecuencia.
	 * Complejidad: O(n2)
	 * @param int n
	 * @return DList
	 **/
	public DList getLow(int n) {
		
		if(n>getSize())
			n = getSize();
		
		DList treeList = root.getPreorder();
		DList list = new DList();
		treeList.sortAsc();
		for (int i = 0; i < n; i++) {
			list.addLast(treeList.getAt(i));
		}
		
		return list;
	}
	
	/** Lee un fichero de texto y vuelca sus palabras en una cola
	 * @param String fname
	 * @return SQueue
	 **/
	public static SQueue readFile(String fname){
		
		String data = "";
		String[] dataSplitted;
	
		SQueue queue = new SQueue();
		
		try{
		
			FileReader f = new FileReader(fname);
			BufferedReader b = new BufferedReader(f);
		
			String line;
			while((line = b.readLine())!=null)
				data += line+"\n";
			b.close();
		
		}
		catch(IOException e){
			System.out.print(e.toString());
			}
		
		data = data.replaceAll("\n", "");
		data = data.replaceAll("[^a-zA-Z_0-9\n]", " ").trim();
		data = data.replaceAll("  ", " ").toLowerCase().trim();
		
		dataSplitted = data.toLowerCase().split(" ");
		for(int i = 0; i<dataSplitted.length; i++)
			queue.enqueue(dataSplitted[i]);
		
		return queue;
		
	}
	
	public static void main(String args[]) {
	
		// Creamos el diccionario
		DictionaryTree dictionary = new DictionaryTree();
				
		// Leemos el fichero y lo vertemos en una cola
		SQueue queue = readFile("src/fase2/example.txt");
				
		// Alimentamos el diccionario con la cola
		dictionary.add(queue);
				
		// Añadimos una palabra manualmente
		dictionary.add("matricula");
				
		// Mostramos el diccionario en orden alfabético descendente
		System.out.println("<< Diccionario ordenado alfabéticamente (A-Z) >>");
		dictionary.show('A');
				
		// Mostramos el diccionario en orden alfabético ascendente
		System.out.println("<< Diccionario ordenado alfabéticamente (Z-A) >>");
		dictionary.show('B');
				
		// Mostramos las 3 palabras con mayor frecuencia
		System.out.println("<< Top 3 palabras con mayor frecuencia >>");
		System.out.println(dictionary.getTop(3)+"\n");
				
		// Mostramos las 3 palabras con menor frecuencia
		System.out.println("<< Top 3 palabras con menor frecuencia >>");
		System.out.println(dictionary.getLow(3)+"\n");
	}

}
