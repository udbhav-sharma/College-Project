package structure;

public class PointOfInterest implements Comparable<PointOfInterest> {
	public Point p;
	public int dist = 0;
	
	public PointOfInterest(Point p){
		this.p = p;
	}
	
	public PointOfInterest(Point p, int dist) {
		this.p = p;
		this.dist = dist;
	}
	
	public boolean equals(PointOfInterest v){
		return this.p.equals(v.p);
	}
	
	public int compareTo(PointOfInterest other){
		return Integer.compare(dist, other.dist);
	}
	
	public String toString(){
		return "{"+p+", "+dist+"}";
	}
}
