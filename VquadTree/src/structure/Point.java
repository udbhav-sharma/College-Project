package structure;

import java.io.Serializable;

public class Point extends java.awt.Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public boolean equals(Point p){
    	return (this.x==p.getX() && this.y==p.getY());
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")"); 
    }
}
