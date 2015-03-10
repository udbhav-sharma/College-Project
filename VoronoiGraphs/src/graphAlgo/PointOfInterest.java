package graphAlgo;

import util.Pair;

public class PointOfInterest extends Vertex {
	
	public PointOfInterest(Point p){
		super(p);
		this.dist=0;
		this.pi=this;
		this.pis.add(new Pair<Vertex,Edge>(this,new Edge(this,0)));
	}

	public String toString(){
		return super.toString();
	}
	
}
