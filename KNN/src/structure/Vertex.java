package structure;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
	public Point p;
	public Vertex pi = null;
	public int dist = Integer.MAX_VALUE;
	public boolean flag = false;
	public ArrayList<Edge> adjancencies;
	
	public Vertex( Point p ){
		this.p = (Point)p.clone();
		this.adjancencies = new ArrayList<Edge>();
	}
	
	public void reinit(){
		this.pi = null;
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
		if(this.pi!=null)
			output+="PI: "+"("+this.pi.p.getX()+","+this.pi.p.getY()+")\n";
		else
			output+="PI: "+this.pi+"\n";
		
		return output;
	}
}
