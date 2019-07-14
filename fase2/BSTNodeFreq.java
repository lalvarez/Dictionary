package fase2;

import dlist.DList;

/** Es necesario esta nueva variante del nodo de un árbol para el desarrollo del árbol
 * de frecuencias. Usará como clave la frecuencia y como elemento una lista de palabras.
 **/

public class BSTNodeFreq {
	
	// Se usará la key para guardar el número de veces que se han repetido las palabras
	Integer key;
	
	// Se usara una lista para almacenar las palabras que comparten el número de veces
	// que aparecen
	DList elem;

	BSTNodeFreq parent;
	BSTNodeFreq left;
	BSTNodeFreq right;

	public BSTNodeFreq(Integer k, DList e) {
		
		key = k;
		elem = e;
	}
	
	public BSTNodeFreq(Integer k) {
		
		key = k;
		elem = new DList();
	}
	
	public int getSize() {
		
		return getSize(this);
	}
	
	protected BSTNodeFreq findMin(){
		
		BSTNodeFreq minNode = this;
		
		while(minNode.left!=null)
			minNode = minNode.left;
		
		return minNode;
	}

	public static int getSize(BSTNodeFreq node) {
		
		if (node == null)
			return 0;
		else
			return 1 + getSize(node.left) + getSize(node.right);
	}
	

	public int getHeight() {
		
		return getHeight(this);
	}

	protected static int getHeight(BSTNodeFreq node) {
		
		if (node == null)
			return -1;
		else
			return 1 + Math.max(getHeight(node.left),
					getHeight(node.right));
	}
	
	
	public int getDepth() {
		
		return getDepth(this);
	}

	protected static  int getDepth(BSTNodeFreq node) {
		
		if (node == null)
			return -1;
		else
			return 1 + getDepth(node.parent);
	}
	
	
	public void showPreorder() {
		
		showPreorder(this);
	}
	
	
	public void showInorder() {
		
		showInorder(this);
	}
	
	public void showPostorder() {
		
		showPostorder(this);
	}
	
	protected static  void showPreorder(BSTNodeFreq node) {
			
		if(node==null){
			return;
		}
		
		if(!node.elem.isEmpty())
			System.out.println(node.key+": "+node.elem);
		showPreorder(node.left);
		showPreorder(node.right);
	}

	protected static void showInorder(BSTNodeFreq node) {
		
		if(node==null){
			return;
		}
		
		showInorder(node.left);
		if(!node.elem.isEmpty())
			System.out.println(node.key+": "+node.elem);
		showInorder(node.right);
	}
	
	protected static void showPostorder(BSTNodeFreq node) {
		
		if(node==null){
			return;
		}
		
		
		showPostorder(node.right);
		if(!node.elem.isEmpty())
			System.out.println(node.key+": "+node.elem);
		showPostorder(node.left);
	}
}
