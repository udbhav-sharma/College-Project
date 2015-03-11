package structure;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
	public Point p;
	public int dist = Integer.MAX_VALUE;
	public boolean flag = false;
	public ArrayList<Edge> adjancencies;
	
	public Vertex( Point p ){
		this.p = (Point)p.clone();
		this.adjancencies = new ArrayList<Edge>();
	}
	
	public void reinit(){
		this.dist = Integer.MAX_VALUE;
		this.flag = false;
	}
	
	public void addNeighbour(Vertex v, int w){
		this.adjancencies.add(new Edge(v,w));
	}
	
	public boolean equals(Vertex v){
		return this.p.equals(v.p);
	}
	
	public int compareTo(Vertex other){
		return Integer.compare(dist, other.dist);
	}
	
	public String toString(){
		String output="-------\nVertex: ";
		output+="("+this.p.getX()+","+this.p.getY()+")\n";
		output+="Dist: "+this.dist+"\n";
		output+=adjancencies+"\n";
		
		return output;
	}
}
