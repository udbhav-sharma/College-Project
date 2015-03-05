package graphAlgo;

import java.awt.Point;

public class PointOfInterest extends Vertex {
	
	public PointOfInterest( Point p ) {
		super(p);
	}
	
	public String toString(){
		String output="Point of Interest";
		output+=this.p+"\n";
		output+="Dist:"+this.dist+"\n";
		if(this.pi!=null)
			output+="PI:"+this.pi.p+"\n";
		else
			output+="PI:"+this.pi+"\n";
		return output;
	}

}
