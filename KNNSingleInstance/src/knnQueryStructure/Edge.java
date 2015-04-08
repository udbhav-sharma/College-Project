package knnQueryStructure;

public class Edge{
	Vertex v;
	double w;
	
	Edge(Vertex v){
		this.v = v;
		this.w = 0;
	}
	
	Edge(Vertex v,double w){
		this.v = v;
		this.w = w;
	}
	
	public String toString(){
		return v.p+" "+w;
	}
}
