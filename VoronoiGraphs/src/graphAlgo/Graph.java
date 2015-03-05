package graphAlgo;

import java.awt.Point;
import java.util.ArrayList;

public class Graph {

	private ArrayList< Vertex > V;
	
	public Graph(){
		V=new ArrayList<Vertex>();
	}
	
	public Vertex addV( Point p ){
		this.V.add(new Vertex(p));
		return this.V.get(this.V.size()-1);
	}
	
	public void addE(Vertex u, Vertex v, int w){
		if(u!=null && v!=null)
			u.addNeighbour(v,w);
	}
	
	public ArrayList< Vertex > getV(){
		return this.V;
	}
	
	public Vertex findV( Point p ){
		Vertex u=null;
		for(Vertex v:this.V){
			if(p.equals(v.p)){
				u=v;
			}
		}
		return u;
	}
	
	public String toString(){
		String output="----Graph----\n";
		for(Vertex v:this.V)
			output+=v.toString();
		return output;
	}
}
