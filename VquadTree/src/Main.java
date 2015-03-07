import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import util.Log;
import util.Pair;
import structure.Point;
import structure.Rectangle;
import structure.Tree;

public class Main {
	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File("res/points"));
		double x1,y1,x2,y2;
		double minX=0,minY=0,maxX=0,maxY=0;
		String delimiter;
		
		
		ArrayList<Pair<Point, Point>> points = new ArrayList<Pair<Point,Point>>();
		
		while(in.hasNext()){
			x1 = in.nextDouble();
			y1 = in.nextDouble();
			delimiter = in.next();
			x2 = in.nextDouble();
			y2 = in.nextDouble();
			
			minX=Math.min(minX, x1);
			minY=Math.min(minY, y1);
			maxX=Math.max(maxX, x1);
			maxY=Math.max(maxY, y1);
			
			points.add(new Pair<Point, Point>( new Point(x1, y1), new Point(x2, y2) ));
			
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
		Log.l(tree.getGenerator(new Point(7,8)));
		Log.l(tree.getGenerator(new Point(8,9)));
		Log.l(tree.getGenerator(new Point(9,10)));
		Log.l(tree.getGenerator(new Point(11,12)));
		
		Log.l(tree.getGenerator(new Point(2,2)));
		//Log.l(tree.getGenerator(new Point(3,3)));
		Log.l(tree.getGenerator(new Point(10,11)));
		
		in.close();
	}
}
