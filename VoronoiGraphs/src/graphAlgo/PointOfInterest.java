package graphAlgo;

public class PointOfInterest extends Vertex {
	
	public PointOfInterest(Point p){
		super(p);
		this.dist=0;
		this.pi=this.p;
		this.pis.put(this.p,new Generator(this.p, 0, 0));
	}

	public String toString(){
		return super.toString();
	}
	
}
