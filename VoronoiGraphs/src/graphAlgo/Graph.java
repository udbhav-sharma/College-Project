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
	
	public class Vertex implements Comparable<Vertex> {
		Point p;
		Vertex pi = null;
		int dist = Integer.MAX_VALUE;
		boolean flag = false;
		ArrayList<Edge> adjancencies;
		
		public Vertex( Point p ){
			this.p = (Point)p.clone();
			this.adjancencies = new ArrayList<Graph.Edge>();
		}
		
		public void addNeighbour(Vertex v, int w){
			this.adjancencies.add(new Edge(v,w));
		}
		
		public int compareTo(Vertex other){
			return Integer.compare(dist, other.dist);
		}
		
		public String toString(){
			String output="Vertex:\n";
			output+=this.p+"\n";
			output+="Dist:"+this.dist+"\n";
			if(this.pi!=null)
				output+="PI:"+this.pi.p+"\n";
			else
				output+="PI:"+this.pi+"\n";
			return output;
		}
	}
	
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
}
