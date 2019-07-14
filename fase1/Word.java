package fase1;

public class Word {
	
	public Word next;
	public Word prev;
	
	public String elem;
	public int frequency;
	
	public Word(String elem, int frequency){
		
		this.elem = elem;
		this.frequency = frequency;
	}
	
	public String toString(){
		
		String word = elem+": "+ frequency+"\n";
		return word;
	}

}
