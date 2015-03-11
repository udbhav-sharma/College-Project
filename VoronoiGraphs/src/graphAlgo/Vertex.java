package graphAlgo;

import java.util.ArrayList;

import util.Pair;

public class Vertex implements Comparable<Vertex> {
	public Point p;
	public Point pi = null;
	public int dist = Integer.MAX_VALUE;
	public boolean flag = false;
	public ArrayList<Edge> adjancencies;
	public ArrayList<Pair<Point, Generator>> pis;
	
	public Vertex( Point p ){
		this.p = (Point)p.clone();
		this.adjancencies = new ArrayList<Edge>();
		this.pis = new ArrayList<Pair<Point,Generator>>();
	}
	
	public void addNeighbour(Vertex v, int w){
		this.adjancencies.add(new Edge(v,w));
	}
	
	public int compareTo(Vertex other){
		return Integer.compare(dist, other.dist);
	}
	
	public String toString(){
		String output="-------\nVertex: ";
		output+="("+this.p.getX()+","+this.p.getY()+")\n";
		output+="Dist: "+this.dist+"\n";
		output+="PI: "+this.pi+"\n";
		output+="PI's:\n";
		for(Pair<Point, Generator> p: this.pis){
			output += p.getElement0()+" : "+p.getElement1()+"\n";
		}
		
		return output;
	}
	
	public static class Generator{
		Point p;
		int w;
		int dist;
		
		public Generator(Point p, int w, int dist){
			this.p = p;
			this.w = w;
			this.dist = dist;
		}
		
		public String toString(){
			return "{"+p+", "+w+", "+dist+"}";
		}
	}
}
