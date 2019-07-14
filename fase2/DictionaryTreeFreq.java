package fase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import fase1.Dictionary;
import queue.SQueue;

public class DictionaryTreeFreq implements IDictionaryTreeFreq {

	BSTNodeFreq root;
	
	@Override
	/**
	 * Recibe un objeto tipo DictionaryList (implementado en la fase 1), es
	 * decir, una lista de palabras con sus frecuencias (en la lista no hay
	 * palabras repetidas) y almacena cada una de las palabras en el árbol.
	 */
	public void save(Dictionary list) {
		
		for (int i = 0; i < list.getSize(); i++) {
			
			insert(list.getFrequencyAt(i), list.getWordAt(i));
		}
	}
	
	@Override
	/**
	 * Recibe una palabra con su frecuencia, y añaade la palabra al árbol.
	 */
	public void add(String word, Integer freq) {
		
		insert(freq, word);
	}
	
	// Complejidad: O(log(n))
	public void insert(Integer key, String element) {
		
		//Si la palabra no esta inicialmente en el arbol
		if(findNode(element) == null){
			
			//Se crea el nodo
			BSTNodeFreq newNode = new BSTNodeFreq(key);
			newNode.elem.addFirst(element);
			
			if (root == null){
				//Si el arbol esta vacio la raiz sera ese nuevo nodo
				root = newNode;
			}
				
			else
				//Si el arbol contenia elementos se inserta la nueva palabra
				insert(newNode, root);
		}
		
		else{
			
			//Si el arbol ya contenia la palabra, localizamos el nodo donde se encuentra
			BSTNodeFreq node = findNode(element);
			
			//Almacenamos la frecuencia actual de esa palabra para saber donde habra que anadirla
			int freq = node.key + key;
			
			//Eliminamos la palabra de esa lista
			node.elem.removeAll(element);
			
			if(node.elem.isEmpty()){
				
				remove(node.key);
			}	
			
			//Si el nodo que contiene la nueva frecuencia existe, añadimos la palabra a su lista
			BSTNodeFreq newNode = findNode(freq);
			
			if (newNode != null)
				newNode.elem.addLast(element);
			else{
				
				BSTNodeFreq newNode2 = new BSTNodeFreq(freq);
				newNode2.elem.addLast(element);
				insert(newNode2, root);
			}
		}
		
	}
	
	// Complejidad: O(log(n))
	protected static void insert(BSTNodeFreq newNode, BSTNodeFreq node) {
		
		int key = newNode.key;
		if (key == node.key) {
			
			//Se inserta el elemento en la lista de los elementos con su misma frequencia
			//Puedo hacer getFirst porque el nodo creado contiene una lista de un solo elemento
			 node.elem.addLast(newNode.elem.getFirst());
			return;
		}
		if (key < node.key) {
			if (node.left == null) {
				
				node.left = newNode;
				newNode.parent = node;
			}else insert(newNode,node.left);
			
		}else {
			if (node.right == null) {
				node.right = newNode;
				newNode.parent = node;
			}else insert(newNode,node.right);
		}
		
	}
	
	public BSTNodeFreq findNode(String element){
		
		return findNode(element, root);
	}
	
	public BSTNodeFreq findNode(String word, BSTNodeFreq node){
		
		if(node!=null){
			
			if(node.elem.contains(word))
				return node;
		
		else if (node.right != null)
			return findNode(word, node.right);
		
		else if (node.left != null)
			return findNode (word, node.left);
		}

		return null;	
	}
	
	public BSTNodeFreq findNode(int key){
		
		return findNode(key, root);
	}
	
	public BSTNodeFreq findNode(int key, BSTNodeFreq node){
		
		if(node!=null){
			if (key == node.key)
				return node;
			if (key < node.key)
				return findNode(key, node.left);
			else
				return findNode(key, node.right);
		}
		return null;
		
	}
	
	public void remove(int key) {
		
		remove(key,root);
	}
	
	private BSTNodeFreq remove(Integer key, BSTNodeFreq node) {
		
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
		if (node.left==null && node.right==null) {
			return null;
		}

		if (node.left==null)  {
			node.right.parent = node.parent;
			return node.right;
		}
		
		if (node.right==null) {
			node.left.parent = node.parent;
			return node.left;
		}

		BSTNodeFreq sucesorNode = node.right;
		while (sucesorNode.left!=null) {
			sucesorNode=sucesorNode.left;
		}

		node.elem=sucesorNode.elem;
		node.key=sucesorNode.key;
		
		node.right=remove(sucesorNode.key,node.right);
		return node;	
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
	
	public void show(char c) {
			
		if(c=='A')
			root.showInorder();
		else
			root.showPostorder();	
	}
	
	public static void main(String args[]) {
		
		// Creamos nuestro Diccionario de frecuencias
		DictionaryTreeFreq tree1 = new DictionaryTreeFreq();
		
		// Leemos el fichero y lo volcamos a una cola
		SQueue queue = readFile("src/fase2/example.txt");
		System.out.println(queue+"\n");	
		
		// Creamos nuestro Diccionario de la fase 1 y lo alimentamos con la cola
		Dictionary dic1 = new Dictionary();
		dic1.add(queue);
		
		// Con nuestro diccionario de la fase 1, alimentamos al DictionaryTreeFreq
		tree1.save(dic1);
		
		// Mostramos el DictionaryTreeFreq
		System.out.println("DictionaryTreeFreq (ascendente): ");
		tree1.show('A');
		System.out.println();
		
		// Lo volvemos a alimentar y mostrar
		tree1.save(dic1);
		System.out.println("DictionaryTreeFreq (ascendente): ");
		tree1.show('A');
		System.out.println();
		
		// Añadimos una palabra manualmente
		System.out.println("Se ha añadido \"un\"");
		tree1.add("un", 1);
		tree1.show('A');
		System.out.println();

		// Lo volvemos a alimentar y mostrar
		System.out.println("DictionaryTreeFreq (ascendente): ");
		tree1.save(dic1);
		tree1.show('A');
		System.out.println();
		
		// Mostramos el DictionaryTreeFreq en otro orden
		System.out.println("DictionaryTreeFreq (descendente): ");
		tree1.show('r');
		
	}
}
