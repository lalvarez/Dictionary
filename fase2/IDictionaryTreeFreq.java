package fase2;

import fase1.Dictionary;

public interface IDictionaryTreeFreq {

	public void save(Dictionary list);
	/*
	 * Recibe un objeto tipo DictionaryList (implementado en la fase 1), es
	 * decir, una lista de palabras con sus frecuencias (en la lista no hay
	 * palabras repetidas) y almacena cada una de las palabras en el árbol.
	 */

	void add(String word, Integer freq);
	/*
	 * Recibe una palabra con su frecuencia, y añaade la palabra al árbol.
	 */

}
