package fase2;

import dlist.DList;

public class BSTNode implements IBSTNode{
	
	String key;
	Integer elem;

	BSTNode parent;
	BSTNode left;
	BSTNode right;
	
	public BSTNode(String k, Integer e) {
		
		key = k;
		elem = e;
	}
	
	@Override
	public int getSize() {
		
		return getSize(this);
	}
	
	protected BSTNode findMin(){
		
		BSTNode minNode = this;
		
		while(minNode.left!=null)
			minNode = minNode.left;
		
		return minNode;
	}

	public static int getSize(BSTNode node) {
		
		if (node == null)
			return 0;
		else
			return 1 + getSize(node.left) + getSize(node.right);
	}
	
	@Override
	public int getHeight() {
		
		return getHeight(this);
	}

	protected static int getHeight(BSTNode node) {
		
		if (node == null)
			return -1;
		else
			return 1 + Math.max(getHeight(node.left),
					getHeight(node.right));
	}
	
	@Override
	public int getDepth() {
		
		return getDepth(this);
	}

	protected static  int getDepth(BSTNode node) {
		
		if (node == null)
			return -1;
		else
			return 1 + getDepth(node.parent);
	}
	
	@Override
	public void showPreorder() {
		
		showPreorder(this);
	}
	
	public DList getPreorder() {
		
		DList listNodes = new DList();
		getPreorder(this, listNodes);
		return listNodes;
	}
	
	@Override
	public void showInorder() {
		
		showInorder(this);
	}
	
	@Override
	public void showPostorder() {
		
		showPostorder(this);
	}
	
	public DList getPostorder() {
		
		DList listNodes = new DList();
		getPostorder(this, listNodes);
		return listNodes;
	}
	
	protected static  void showPreorder(BSTNode node) {
			
		if(node==null){
			return;
		}
			
		System.out.println(node.key+": "+node.elem.toString());
		showPreorder(node.left);
		showPreorder(node.right);
	}
	
	protected static void getPreorder(BSTNode node, DList listNodes) {
		
		if(node==null){
			return;
		}
			
		listNodes.addLast(node.elem.toString()+": "+node.key);
		getPreorder(node.left, listNodes);
		getPreorder(node.right, listNodes);
	}

	protected static void showInorder(BSTNode node) {
		
		if(node==null){
			return;
		}
		
		showInorder(node.left);
		System.out.println(node.key+": "+node.elem.toString());
		showInorder(node.right);
	}
	
	protected static void showPostorder(BSTNode node) {
		
		if(node==null){
			return;
		}
		
		showPostorder(node.right);
		System.out.println(node.key+": "+node.elem.toString());
		showPostorder(node.left);
	}
	
	protected static void getPostorder(BSTNode node, DList listNodes) {
		
		if(node==null){
			return;
		}
		getPostorder(node.right, listNodes);
		listNodes.addLast(node.elem.toString()+": "+node.key);
		getPostorder(node.left, listNodes);
	}

}
