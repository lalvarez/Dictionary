package fase2;

import queue.SQueue;
import dlist.DList;

public interface IDictionaryTree{
	
	public void add(SQueue queue);
	/*  Recibe una cola de palabras y añade cada palabra de la cola al
		diccionario, utilizando el siguiente método add. Recuerda que en
		programación orientada a objetos es posible tener dos métodos con el
		mismo nombre gracias a la propiedad de sobrecarga
	 */
	
	public void add(String word);
	/*  Recibe una palabra y añade ésta al diccionario en orden alfabético
		ascendente (A-Z). Si la palabra ya existe en el diccionario, simplemente
		incrementará su frecuencia. Si, por el contrario, la palabra no existe, debe
		ser insertada en el lugar que le corresponda dentro del diccionario. En este
		caso, la frecuencia de la palabra será 1.
	 */
	
	public void show(char c);
	/*  Recibe un carácter c. Si c es ‘A’, el método deberá mostrar el
		diccionario en orden alfabético ascendente. Si el carácter es cualquier otro,
		el diccionario se mostrará en orden alfabético descendente. Por cada
		palabra, además se deberá mostrar su frecuencia.
	 */
	
	public int search(String word);
	/*	Recibe una palabra y devuelve su frecuencia asociada en el
		diccionario. 
	 */
	
	public DList getTop(int n);
	/*  Recibe un número entero n y devuelve una lista con las n palabras
		con mayor frecuencia del diccionario. La lista debe estar ordenada por
		frecuencia. En caso de empate, es decir, palabras con la misma frecuencia,
		éstas deberán aparecer por orden alfabético. 
	 */
	
	public DList getLow(int n);
	/* 	Recibe un número entero n, y devuelve una lista con las n palabras
		con menor frecuencia del diccionario. La lista debe estar ordenada por 
		frecuencia. En caso de empate, es decir, palabras con la misma frecuencia,
		éstas deberán aparecer por orden alfabético. 
	 */
	
}
