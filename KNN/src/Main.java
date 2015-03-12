import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import structure.Dijisktra;
import structure.DistanceTable;
import structure.Graph;
import structure.Point;
import structure.Vertex;
import util.Log;
import util.Pair;

public class Main {
	private static HashMap<Point, DistanceTable> distTable;
	private static HashMap<Point, ArrayList<Point>> gBPTable;
	
	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File("res/nvd"));
		Point p1,p2;
		double x,y;
		int k,w;
		Vertex g,u,v;
		Graph G;
		Dijisktra dijisktra;
		ArrayList<Vertex> borderPoints;
		distTable = new HashMap<Point, DistanceTable>();
		gBPTable = new HashMap<Point, ArrayList<Point>>();
		
		while(in.hasNext()){
			G = new Graph();
			dijisktra = new Dijisktra();
			borderPoints = new ArrayList<Vertex>();
			
			//Generator g
			x = in.nextDouble();
			y = in.nextDouble();
			p1 = new Point(x,y);
			g = G.addV(p1);
			
			k = in.nextInt();
			while(k-->0){
				//Edges belonging to generator g
				x = in.nextDouble();
				y = in.nextDouble();
				p1 = new Point(x,y);
				
				x = in.nextDouble();
				y = in.nextDouble();
				p2 = new Point(x,y);
				
				w = in.nextInt();
				
				if(p1.equals(p2))
					continue;
				
				u = G.findV(p1);
				if(u==null)
					u = G.addV(p1);
				
				v = G.findV(p2);
				if(v==null)
					v = G.addV(p2);
				
				G.addE(u, v, w);
			}
			
			k = in.nextInt();
			while(k-->0){
				//Border points belonging to generator g
				x = in.nextDouble();
				y = in.nextDouble();
				w = in.nextInt();
				borderPoints.add(G.findV(new Point(x,y)));
				addBorderPoint(g.p,new Point(x,y));
			}
			
			//Run Dijisktra Algorithm on graph formed above to compute distance between each border points
			//Log.l("-------For "+g+"------");
			for(Vertex b1:borderPoints){
				dijisktra.init(G,b1);
				dijisktra.run();
				for(Vertex b2:borderPoints){
					//Log.l(b1.p+" "+b2.p+" "+b2.dist);
					addD(g.p,b1.p,b2.p,b2.dist);
				}
				addD(g.p,b1.p,g.p,g.dist);
			}
		}
		
		Iterator it = distTable.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry<Point,DistanceTable> pair = (HashMap.Entry<Point,DistanceTable>)it.next();
			Log.l("-------For "+pair.getKey()+"------");
			Log.l(pair.getValue());
			it.remove();
		}
		
		in.close();
		//findKNN(q, nn, 3);
	}
	
	private static void addD( Point g, Point p1, Point p2, int dist ){
		DistanceTable d  = distTable.get(g);
		if(d==null){
			d = new DistanceTable();
			d.addD(p1, p2, dist);
			distTable.put(g, d);
		}
		else
			d.addD(p1,p2,dist);
	}
	
	private static void addBorderPoint(Point g, Point b){
		ArrayList<Point> list  = gBPTable.get(g);
		if(list==null){
			list = new ArrayList<Point>();
			list.add(b);
			gBPTable.put(g, list);
		}
		else
			list.add(b);
	}
	
	private static void findKNN( Point q, Point nn, int K ){
		ArrayList<Pair<Point,Integer>>  bPDTable = new ArrayList<Pair<Point,Integer>>();
		Point POI = null;
		int minDist;
		
		//Add q distance with all Border Points in NN
		for(Point b:gBPTable.get(nn)){
			minDist = eucledianDistance(b, q);
			bPDTable.add(new Pair<Point, Integer>(b, minDist));
		}
		
		for(int i=1;i<K;i++){
			//select min poi as POI from all reachable with b
			
			//Update new Border Points to check
			for(Point b:gBPTable.get(POI)){
				minDist = Integer.MAX_VALUE;
				for(Pair<Point,Integer> pair: bPDTable){
					try{
						minDist = Math.min(minDist, distTable.get(POI).getDistanceTwoPoints(b, pair.getElement0()));
					}
					catch(Exception e){}
				}
				bPDTable.add(new Pair<Point, Integer>(b, minDist));
			}
		}
	}
	
	private static int eucledianDistance( Point p1, Point p2 ){
		return (int)p1.distance(p2);
	}
}
