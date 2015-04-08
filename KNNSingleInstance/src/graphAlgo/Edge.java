package graphAlgo;

public class Edge{
	Vertex v;
	double w;
	
	Edge(Vertex v){
		this.v = v;
		this.w = 0;
	}
	
	Edge(Vertex v, double w){
		this.v = v;
		this.w = w;
	}
	
	public boolean equals(Edge e){
		return this.v==e.v && this.w==e.w;
	}
	
	public String toString(){
		return v.p+" "+w;
	}
}
