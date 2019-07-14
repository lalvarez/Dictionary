package dlist;

public class DNodeVertex {

	public String vertex;
	public Float weight;
	
	public DNodeVertex prev;
	public DNodeVertex next;
	
	public DNodeVertex(String v, Float w) {
		vertex = v;
		weight = w;
	}

}

