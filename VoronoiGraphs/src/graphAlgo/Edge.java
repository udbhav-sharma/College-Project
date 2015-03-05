package graphAlgo;

public class Edge{
	Vertex v;
	int w;
	
	Edge(Vertex v){
		this.v = v;
		this.w = 0;
	}
	
	Edge(Vertex v,int w){
		this.v = v;
		this.w = w;
	}
}
