
package fase1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dlist.DList;
import queue.SQueue;

public class Dictionary implements IDictionary {
	
	Word header;
	Word trailer;
	int size = 0;

	public Dictionary() {
		
		header = new Word(null, -1);
		trailer = new Word(null, -1);
		
		header.next = trailer;
		trailer.prev= header;
		
	}
	
	public int getSize() {
		
		return size;
	}
	
	public String getWordAt(int index) {
		
		int i = 0;
		String result=null;
		for (Word nodeIt = header.next; nodeIt != trailer && result==null; nodeIt = nodeIt.next) {
			if (i == index) {
				result=nodeIt.elem;
			}
			++i;
		}
		if (result==null) System.out.println("DList: Get out of bounds");
		return result;	
	}
	
	public int getFrequencyAt(int index) {
		
		int i = 0;
		String result=null;
		for (Word nodeIt = header.next; nodeIt != trailer && result==null; nodeIt = nodeIt.next) {
			if (i == index) {
				return nodeIt.frequency;
			}
			++i;
		}
		if (result==null) System.out.println("DList: Get out of bounds");
		return -1;
	}
	
	/** Método auxiliar que devuelve el nodo asociado a una palabra.
	 * Ayuda a bajar la comlejidad computacional en álgunos métodos.
	 **/
	public Word getNodeOf(String elem) {
		
		Word node = null;
		for (Word nodeIt = header.next; nodeIt != trailer && node==null; nodeIt = nodeIt.next) {
			if (nodeIt.elem.equals(elem)) {
				node=nodeIt;
			} 
		}
		return node;
	}
	
	public Word removeLast () {
		
		if(size == 0)
			return null;
		
		Word lastWord = trailer.prev;
	
		trailer.prev = lastWord.prev;
		lastWord.prev.next = trailer;
		size--;
		
		return lastWord; 
	}
	
	/** Adapatación del método de la burbuja para ordenar el diccionario
	 * alfabéticamente.
	 * Complejidad: O(n2)
	 **/
	private void sortAlphabetically(){
		
		String elem1;
		int freq1;
		
		for (Word nodeTemp = header.next; nodeTemp.next != trailer; nodeTemp = nodeTemp.next) {
			
			for (Word nodeIt = header.next; nodeIt.next != trailer; nodeIt = nodeIt.next) {
				
				if(nodeIt.elem.compareTo(nodeIt.next.elem)<0){
					
					elem1 = nodeIt.elem;
					freq1 = nodeIt.frequency;
					
					nodeIt.elem = nodeIt.next.elem;
					nodeIt.frequency = nodeIt.next.frequency;
					
					nodeIt.next.elem = elem1;
					nodeIt.next.frequency = freq1;
				}
			}
		}
	}
	
	/** Adapatación del método de la burbuja para ordenar el diccionario
	 * por frecuencia.
	 * Complejidad: O(n2)
	 **/
	private void sortByFrequency(){
		
		String elem1;
		int freq1;
		
		for (Word nodeTemp = header.next; nodeTemp != trailer; nodeTemp = nodeTemp.next) {
			
			for (Word nodeIt = header.next; nodeIt.next != trailer; nodeIt = nodeIt.next) {
				
				if(nodeIt.frequency>nodeIt.next.frequency){
					
					elem1 = nodeIt.elem;
					freq1 = nodeIt.frequency;
					
					nodeIt.elem = nodeIt.next.elem;
					nodeIt.frequency = nodeIt.next.frequency;
					
					nodeIt.next.elem = elem1;
					nodeIt.next.frequency = freq1;
				
				}
			}
		}
	}
	
	@Override
	/** Recibe una cola que alimentará el diccionario. Cada elemento de la cola es añadido
	 * a la estructura hasta que la cola queda vacía.
	 * Complejidad: O(n2)
	 * @param SQueue queue
	 **/
	public void add(SQueue queue) {

		while(!queue.isEmpty())
			add(queue.dequeue());

	}
	
	@Override
	/** Recibe una palabra que será añadida al diccionario. Si no existe, se añade al final,
	 * si existe, se busca y se aumenta su frecuencia.
	 * Complejidad: O(n)
	 * @param String word
	 **/
	public void add(String word) {
			
		Word newWord = new Word(word,1);
		Word wordNode = getNodeOf(word);
		
		if(wordNode == null){
			
			newWord.next = header.next;
			newWord.prev = header;
				
			header.next.prev = newWord;
			header.next = newWord;
			size++;
		}
		
		else{
			
			wordNode.frequency++;
		}	
	}
	
	@Override
	/** Muestra el diccionario completo ordenado alfabéticamente en orden ascendente si recibe
	 * 'A', en caso contrario lo mostrará en orden descendente.
	 * Complejidad: O(n2)
	 * @param char c
	 **/
	public void show(char c) {
	
		String output = "";
		sortAlphabetically();
		
		if ( c == 'A'){
				
			// Ascendente
			for(Word nodeIt = trailer.prev; nodeIt != header; nodeIt = nodeIt.prev)
				output += nodeIt.elem+" : "+nodeIt.frequency+"\n";
			
		}
		else{
					
			// Descendente
			for(Word nodeIt = header.next; nodeIt != trailer; nodeIt = nodeIt.next)
				output += nodeIt.elem+" : "+nodeIt.frequency+"\n";
			
		}
		System.out.println(output);
				
	}
	
	@Override
	/** Busca la palabra recibida por parámetro y devuelve su frecuencia.
	 * Complejidad: O(n)
	 * @param String word
	 * @return int
	 **/
	public int search(String word) {
		
		for(Word nodeIt = header.next; nodeIt != trailer; nodeIt = nodeIt.next)
			if(nodeIt.elem.equals(word))
				return nodeIt.frequency;
		
		return -1 ;
	}

	@Override
	/** Devuelve una lista con las n palabras con mayor frecuencia.
	 * Complejidad: O(n2)
	 * @param int n
	 * @return DList
	 **/
	public DList getTop(int n) {
		
		if(n > size)
			n = 0;
		else
			n = size-n;
		
		sortByFrequency();
		DList top = new DList();
		for(int i = size-1; i >= n ; i--){
			top.addLast(getWordAt(i)+"("+getFrequencyAt(i)+")");
		}
			
		return top;
	}
	
	@Override
	/** Devuelve una lista con las n palabras con menor frecuencia.
	 * Complejidad: O(n2)
	 * @param int n
	 * @return DList
	 **/
	public DList getLow(int n) {
		
		if(n > size)
			n = size;
		
		sortByFrequency();
		DList low = new DList();
		for(int i = 0; i < n ; i++){
			low.addLast(getWordAt(i)+"("+getFrequencyAt(i)+")");
		}
		
		return low;
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
	
	public static void main(String[] args) {
		
		// Creamos el diccionario
		Dictionary dictionary = new Dictionary();
		
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
		System.out.println(dictionary.getTop(3));
		
		// Mostramos las 3 palabras con menor frecuencia
		System.out.println("<< Top 3 palabras con menor frecuencia >>");
		System.out.println(dictionary.getLow(3));

	}

}
