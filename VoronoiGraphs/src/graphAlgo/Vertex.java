package graphAlgo;

import java.awt.Point;
import java.util.ArrayList;

import util.Pair;

public class Vertex implements Comparable<Vertex> {
	public Point p;
	public Vertex pi = null;
	public int dist = Integer.MAX_VALUE;
	public boolean flag = false;
	public ArrayList<Edge> adjancencies;
	public ArrayList<Pair<Vertex, Integer>> pis;
	
	public Vertex( Point p ){
		this.p = (Point)p.clone();
		this.adjancencies = new ArrayList<Edge>();
		this.pis = new ArrayList<Pair<Vertex,Integer>>();
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
		output+="PI's:\n";
	
		for(Pair<Vertex, Integer> p: this.pis){
			output += p.getElement0().p+" : "+p.getElement1()+"\n";
		}
		return output;
	}
}
