import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import util.Log;
import structure.Point;
import structure.Rectangle;
import structure.Tree;

public class Main {
	
	private static HashMap<Point, ArrayList<Point>> points = new HashMap<Point, ArrayList<Point>>();
	
	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File("res/points2"));
		double x1,y1,x2,y2;
		double minX=Double.MAX_VALUE,minY=Double.MAX_VALUE,maxX=0,maxY=0;
		
		while(in.hasNext()){
			x1 = in.nextDouble();
			y1 = in.nextDouble();
			x2 = in.nextDouble();
			y2 = in.nextDouble();
			
			minX=Math.min(minX, x1);
			minY=Math.min(minY, y1);
			maxX=Math.max(maxX, x1);
			maxY=Math.max(maxY, y1);
			
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			Main.addPoint(p1,p2);
		}
		
		Tree tree = new Tree();
		Rectangle rect = new Rectangle();
		rect.setRect(minX,maxY,maxX-minX,maxY-minY);
		tree.init(rect, points);
		Log.l(tree);
		
		Log.l(tree.getGenerator(new Point(1,2)));
		Log.l(tree.getGenerator(new Point(2,3)));
		Log.l(tree.getGenerator(new Point(3,4)));
		Log.l(tree.getGenerator(new Point(4,5)));
		Log.l(tree.getGenerator(new Point(5,6)));
		Log.l(tree.getGenerator(new Point(6,7)));
		
		Log.l(tree.getGenerator(new Point(2,2)));
		Log.l(tree.getGenerator(new Point(3,3)));
		
		in.close();
	}
	
	private static void addPoint(Point p1, Point p2){
		ArrayList<Point> newList = points.get(p1);
		if(newList == null){ 
			newList = new ArrayList<Point>();
			newList.add(p2);
			points.put(p1, newList);
		}
		else{
			newList.add(p2);
		}
	}
}
