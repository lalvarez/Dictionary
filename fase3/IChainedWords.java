package fase3;

import dlist.DList;

public interface IChainedWords {
	
	public DList getSinks();
	public void replaceSinkWords(String fname);
	public DList search(String word);
	public DList searchLongChain();

}
