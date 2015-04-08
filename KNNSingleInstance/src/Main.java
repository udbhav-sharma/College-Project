
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import graphAlgo.Graph;
import graphAlgo.Vertex;
import graphAlgo.ParallelDijisktra;
import graphAlgo.Vertex.Generator;
import structure.Rectangle;
import structure.Tree;
import util.Log;
import util.Point;

public class Main {
	
	/* 
	 * VQuad Tree variables
	 * */
	private static HashMap<Point, ArrayList<Point>> points = new HashMap<Point, ArrayList<Point>>();
	
	public static void main(String args[]) throws FileNotFoundException{ 
		/*
		 * Voronoi Graphs
		 * */
		Graph G = new Graph();
		int i,j,x,y,w;
		Scanner in;
		
		in = new Scanner(new File("res/pointofinterests2"));
		while(in.hasNextInt()){
			x = in.nextInt();
			y = in.nextInt();
			G.addV(new Point(x,y),true);
		}
		in.close();
		
		in = new Scanner(new File("res/roads2"));
		while(in.hasNextInt()){
			i = in.nextInt();
			j = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();

			Vertex u = G.findV(new Point(i,j));
			Vertex v = G.findV(new Point(x,y));

			if( u == null )
				u = G.addV(new Point(i,j),false);
			
			if( v == null )
				v = G.addV(new Point(x,y),false);
			
			w = in.nextInt();
			G.addE(u,v,w);
		}
		in.close();
		
		ParallelDijisktra dijisktra = new ParallelDijisktra();
		dijisktra.init(G);
		dijisktra.generateVoronoi();
		
		/* 
		 * VQuad Tree 
		 * */
		double x1,y1,x2,y2;
		double minX=Double.MAX_VALUE,minY=Double.MAX_VALUE,maxX=0,maxY=0;
		
		for(Vertex v: G.getV()){
			if(v.pis.size()>0){
				Iterator<HashMap.Entry<Point,Generator>> it = v.pis.entrySet().iterator(); 
				while(it.hasNext()){
					HashMap.Entry<Point,Generator> pair = it.next();

					x1 = v.p.getX();
					y1 = v.p.getY();
					x2 = pair.getKey().getX();
					y2 = pair.getKey().getY();

					minX=Math.min(minX, x1);
					minY=Math.min(minY, y1);
					maxX=Math.max(maxX, x1);
					maxY=Math.max(maxY, y1);

					Point p1 = new Point(x1, y1);
					Point p2 = new Point(x2, y2);
					Main.addPoint(p1,p2);
				}
			}
		}
		
		Tree tree = new Tree();
		Rectangle rect = new Rectangle();
		rect.setRect(minX,maxY,maxX-minX,maxY-minY);
		tree.init(rect, points);
		Log.d(tree);
		
		Log.l(tree.getGenerator(new Point(1,2)));
		Log.l(tree.getGenerator(new Point(2,3)));
		Log.l(tree.getGenerator(new Point(3,4)));
		Log.l(tree.getGenerator(new Point(4,5)));
		Log.l(tree.getGenerator(new Point(5,6)));
		Log.l(tree.getGenerator(new Point(6,7)));
		
		Log.l(tree.getGenerator(new Point(2,2)));
		Log.l(tree.getGenerator(new Point(3,3)));
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
