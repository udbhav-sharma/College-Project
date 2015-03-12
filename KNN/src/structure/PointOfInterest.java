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
	
	public int compareTo(PointOfInterest other){
		return Integer.compare(dist, other.dist);
	}
	
	public String toString(){
		return "{"+p+", "+dist+"}";
	}
}
