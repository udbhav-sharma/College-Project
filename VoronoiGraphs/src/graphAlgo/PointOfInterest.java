package graphAlgo;

import util.Pair;

public class PointOfInterest extends Vertex {
	
	public PointOfInterest(Point p){
		super(p);
		this.dist=0;
		this.pi=this.p;
		this.pis.add(new Pair<Point,Generator>(this.p,new Generator(this.p, 0, 0)));
	}

	public String toString(){
		return super.toString();
	}
	
}
