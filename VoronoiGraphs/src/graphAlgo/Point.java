package graphAlgo;

import java.io.Serializable;

public class Point extends java.awt.Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private final double x;
    private final double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public boolean equals(Object p){
    	return (this.x==((Point)p).getX() && this.y==((Point)p).getY());
    }
    
    public boolean isNull(){
    	return (x==-1 || y==-1);
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")"); 
    }
}

