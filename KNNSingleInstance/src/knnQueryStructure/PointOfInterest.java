package knnQueryStructure;

import util.Point;

public class PointOfInterest implements Comparable<PointOfInterest> {
	public Point p;
	public double dist = Double.MAX_VALUE;
	public boolean popped = false;
	
	public PointOfInterest(Point p){
		this.p = p;
	}
	
	public PointOfInterest(Point p, double dist) {
		this.p = p;
		this.dist = dist;
	}
	
	public boolean equals(PointOfInterest v){
		return this.p.equals(v.p);
	}
	
	public int compareTo(PointOfInterest other){
		return Double.compare(dist, other.dist);
	}
	
	public String toString(){
		return "{"+p+", "+dist+"}";
	}
}
