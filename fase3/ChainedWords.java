package fase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dlist.DList;
import dlist.DNodeVertex;
import dlist.DListVertex;

public class ChainedWords implements IChainedWords{
	
	int numVertices;
	int maxVertices;
	
	DListVertex[] vertices;
	
	/** Como en esta implementacion del grafo los vertices(sus adyacencias) están
	 * almacenados en un array, y este es solo accesible por el índice de elemento,
	 * para localizar una palabra he creado una lista (DList) que guarda las palabras
	 * en el mismo orden que son guardadas en vertices[].
	 * De esta forma comparte el mismo índice y gracias a esta lista secundaria, puedo
	 * obtener el índice dentro del array vertices[] de una palabra,
	 * llamando a verticesWords.getIndexOf(word).
	 */
	
	DList verticesWords;
	
	boolean directed;
	
	public ChainedWords(DList words, int max) {
		
		int n = words.getSize();
		if (max <= 0) 
			throw new IllegalArgumentException("Negative maximum number of vertices!!!");
		if (n <= 0) 
			throw new IllegalArgumentException("Negative number of vertices!!!.");
		if (n > max) 
			throw new IllegalArgumentException("number of vertices can never "
					+ "be greater than the maximum.");
		
		maxVertices = max;
		vertices = new DListVertex[maxVertices];
		verticesWords = words;
		numVertices = n;

		for (int i=0; i<numVertices;i++) {
			vertices[i] = new DListVertex(); 
		}
		directed = true;
		
		join();
		
	}
	
	/** Realiza el encadenamiento de las palabras encadenadas. No permite
	 * que se repiten palabras.
	 **/
	public void join(){
		
		for (int i = 0; i < verticesWords.getSize(); i++)
			for (int j = 0; j < verticesWords.getSize(); j++){
				
				String word1 = verticesWords.getAt(i);
				String word2 = verticesWords.getAt(j);
				
				if(i!=j){
					if(word1.regionMatches(word1.length()-2, word2, 0, 2)){
						addEdge(word1, word2, 0);
					}
				}
			}
	}
	
	/** Devuelve una DList con las palabras sumidero, es decir, las palabras a las que podemos
	 * llegar y no podemos salir. Si no podemos llegar, no será sumidero.
	 **/
	@Override
	public DList getSinks(){
		
		DList sinks = new DList();
		
		for (int i = 0; i < vertices.length; i++) {
			if(vertices[i].isEmpty()){
				
				boolean isAccessible = false;
				for (int j = 0; (j < vertices.length)&&!isAccessible; j++) {
					if(vertices[j].contains(verticesWords.getAt(i)))
						isAccessible = true;
				}
				
				if(isAccessible)
					sinks.addLast(verticesWords.getAt(i));
				}
		}
		return sinks;
		
	}
	
	@Override
	public void replaceSinkWords(String fname){
		
		DList sinks = getSinks();
		
		if(readFile(fname).getSize()-numVertices<sinks.getSize()){
			
			System.out.println("No hay suficientes palabras disponibles para reemplazar\n" +
					"los sumideros.");
			return;
			
		}
			
		while(!sinks.isEmpty()){
			
			// Cojo una palabra que no este en el grafo del fichero
			String newWord = null;
			do{
				newWord = getWords(1, readFile(fname)).getAt(0);
			}
			while(verticesWords.contains(newWord));
			
			// Elimino las aristas que unian los vertices al sumidero
			for (int i = 0; i < verticesWords.getSize(); i++) {
					removeEdge(verticesWords.getAt(i), sinks.getLast());
			}
			
			// Reemplazo la palabra sumidero por la nueva
			verticesWords.replaceAt(verticesWords.getIndexOf(sinks.getLast()), newWord);
				
			// Uno
			join();
			
			// Borro la palabra de la lista de palabras sumidero
			sinks.removeLast();
			
		}
		
	}
	
	public void addEdge(String word1, String word2) {
		
		//by default, we add an edge with value 0;
		addEdge(word1,word2,0);
	}
	//check if i is a right vertex
	private boolean checkVertex(String word) {
		
		int i = verticesWords.getIndexOf(word);
		if (i>=0 && i<numVertices) return true;
		else return false;
	}
		
	public void addEdge(String word1, String word2, float w) {
		
		int i = verticesWords.getIndexOf(word1);
		int j = verticesWords.getIndexOf(word2);
		
		if (!checkVertex(word1)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + word1);
		if (!checkVertex(word2)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + word2);
		
		if (vertices[i].contains(word2)) {
			
			int index=vertices[i].getIndexOf(word2);
			DNodeVertex node=vertices[i].getAt(index);
			node.weight=w;
			
			if (!directed) {
				index=vertices[j].getIndexOf(word1);
				node=vertices[j].getAt(index);
				node.weight=w;
			}
		} else {
			vertices[i].addLast(word2,w);
			//if it is a non-directed graph
			if (!directed) vertices[j].addLast(word1,w);
		}
	}
	
	public void removeEdge(String word1, String word2) {
		
		int i = verticesWords.getIndexOf(word1);
		int j = verticesWords.getIndexOf(word2);
		
		if (!checkVertex(word1)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + i);
		if (!checkVertex(word2)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + j);
		
		int index=vertices[i].getIndexOf(word2);
		vertices[i].removeAt(index);
		
		if (!directed) {
				index=vertices[j].getIndexOf(word1);
				vertices[j].removeAt(index);
		}
	}

	public boolean isEdge(String word1, String word2) {
		
		int i = verticesWords.getIndexOf(word1);
		
		if (!checkVertex(word1))
			throw new IllegalArgumentException("Nonexistent vertex  " + word1);
		if (!checkVertex(word2)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + word2);
		
		boolean result=vertices[i].contains(word2);
		return result;
	}

	public void show() {
		
		System.out.println("Adyacencias del grafo:");
		for (int i=0; i<numVertices; i++) {
			if (vertices[i]!=null) {
				String word = verticesWords.getAt(i);
				System.out.println("Los vértices adyacentes a " + word + ": " + vertices[i].toString());
			}
		}
	}

	public String[] getAdjacents(String word1) {
		
		int i = verticesWords.getIndexOf(word1);
		
		if (!checkVertex(word1)) 
			throw new IllegalArgumentException("Nonexistent vertex  " + word1);
		//gets the number of adjacent vertices
		int numAdj = vertices[i].getSize();
		//creates the array
		String[] adjVertices = new String[numAdj];
		//saves the adjacent vertices into the array
		for (int j = 0; j<numAdj; j++) {
			adjVertices[j] = vertices[i].getAt(j).vertex;
		}
		//return the array with the adjacent vertices of i
		return adjVertices;
	}
	
	/** Algoritmo de busqueda de la cadena más larga de toda las estructura.
	 * Para ello llama a la función searchLongChainAt(String word), pasando
	 * cómo argumento cada pañabra del grafo. Devuelve la mayor de ellas.
	 **/
	@Override
	public DList searchLongChain(){
		
		// Inicializo el array de cadenas
		DList[] chains = new DList[vertices.length];
		for (int i = 0; i < chains.length; i++) {
			chains[i] = null;
		}
		
		// Busco la cadena más larga empezando desde todos los vértices
		for (int i = 0; i < verticesWords.getSize(); i++) {
			chains[i] = search(verticesWords.getAt(i));
		}
		
		// Busco el máximo
		DList longChain = null;
		int length = 0;
		for(DList chain:chains)
			if (chain!=null)
				if(chain.getSize()>length){
					length = chain.getSize();
					longChain = chain;
			}
				
		return longChain;
		
	}
	
	/** Algoritmo de busqueda de la cadena más larga.
	 * @params:
	 * String word: Palabra que empieza la cadena.
	 **/
	@Override
	public DList search(String word){
		
		// Inicializo el array de cadenas
		DList[] chains = new DList[vertices.length];
		for (int i = 0; i < chains.length; i++) {
			chains[i] = null;
		}
		
		// Creo la lista de partida
		DList lista = new DList();
		
		// Empiezo la busqueda de cadenas desde la palabra recibida
		next(word, lista, chains);
		
		// Busco el máximo
		DList longChain = null;
		int length = 0;
		for(DList chain:chains)
			if (chain!=null)
				if(chain.getSize()>length){
					length = chain.getSize();
					longChain = chain;
				}
		
		return longChain;
		
	}
	
	public void next(String word, DList list, DList[] defList){
		
		// Copio la lista para no sobreescribirla
		DList ownList = list.copy();
		
		// Añado la palabra que recibo si no está ya en la cadena
		if(!ownList.contains(word)){
			
			ownList.addLast(word);
			
			//Obtengo los adyacentes
			String[] adjs = getAdjacents(word);
			
			// Por cada adyacente, llamo recursivamente a next
			for(String adj:adjs)
				next(adj, ownList, defList);
			
			// Cuando no tenga más adyacentes, guardo mi lista en el array definitivo
			// Sobreescribe si es mayor que la que había guardada en el indice de la palabra
			int index = verticesWords.getIndexOf(word);
			if(defList[index] == null)
				defList[index] = ownList;
			else{
				
				if(ownList.getSize()>defList[index].getSize())
					defList[index] = ownList;
				
			}
			return;
		}
		else{
			// Si está la palabra, finalizo la cadena
			// Sobreescribe si es mayor que la que había guardada en el indice de la palabra
			int index = verticesWords.getIndexOf(ownList.getLast());
			if(defList[index] == null)
				
				defList[index] = ownList;
			else{
				
				if(ownList.getSize()>defList[index].getSize())
					defList[index] = ownList;
				
			}
			return;
		}
		
	}
	
	/** Este método lee un fichero de texto y guarda las palabras del mismo
	 * en una lista.
	 * @param String word
	 * @return DList
	 **/
	public static DList readFile(String fname){
		
		String data = "";
		String[] dataSplited;
	
		DList list = new DList();
		
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
		
		dataSplited = data.toLowerCase().split(" ");
		for(int i = 0; i<dataSplited.length; i++)
			list.addLast(dataSplited[i]);
		
		return list;
		
	}
	
	/** Este método recibe una lista de palabras y un número entero n.
	 * Devuelve una lista de tamaño n con palabras aleatorias no repetidas de la
	 * lista pasada por parámetro.
	 * @param int n
	 * @param DList list
	 * @return DList
	 **/
	public static DList getWords (int n, DList list){
		
		if(n>list.getSize()-1)
			n = list.getSize()-1;
		
		DList words = new DList();
		
		int randomNumber;
		boolean[] numbersChosen = new boolean[list.getSize()];
		int wordsSaved = 0;
		
		while(wordsSaved != n){
			
			randomNumber = (int) (Math.random()*list.getSize()-1);
			
			if(checkNumber(randomNumber, numbersChosen)&&!words.contains(list.getAt(randomNumber))){
				words.addLast(list.getAt(randomNumber));
				numbersChosen[randomNumber] = true;
				wordsSaved = wordsSaved +1;
			}
			
		}
		return words;
	}
	
	public static boolean checkNumber(int number, boolean[] numbers){
		
		for (int i = 0; i < numbers.length; i ++){
			if (numbers[number])
				return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
		
		DList list = getWords(14, readFile("src/fase3/example.txt"));
		ChainedWords graph = new ChainedWords(list,list.getSize());
		
		// Muestra las adyacencias del grafo.
		graph.show();
		System.out.println();
		
		// Muestra los sumideros
		System.out.println("Los palabras sumidero del grafo son: ");
		System.out.println(graph.getSinks());
		System.out.println();
		
		// Vamos a cambiarlos por otras palabras
		System.out.println("Vamos a cambiarlos por otras palabras ");
		graph.replaceSinkWords("src/fase3/example.txt");
		System.out.println();
		
		// Mostramos el nuevo grafo
		System.out.println("Mostramos el nuevo grafo");
		graph.show();
		System.out.println();
		
		// Muestra la cadena más larga del grafo
		System.out.println("La cadena más larga del grafo es: ");
		System.out.println(graph.searchLongChain());
		System.out.println("Y mide: "+ graph.searchLongChain().getSize());
		
	} 
	
}
