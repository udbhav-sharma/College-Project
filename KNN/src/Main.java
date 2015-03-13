import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import structure.Dijisktra;
import structure.DistanceTable;
import structure.Graph;
import structure.Point;
import structure.PointOfInterest;
import structure.Vertex;
import util.Log;
import util.Pair;

public class Main {
	private static HashMap<Point, DistanceTable> distTable;
	private static HashMap<Point, ArrayList<Point>> gBPTable;
	private static HashMap<Point,ArrayList<Point>> bPGTable;
	
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
		bPGTable = new HashMap<Point, ArrayList<Point>>();
		
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
				addBPGMapping(g.p,new Point(x,y));
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
		}
		
		in.close();
		findKNN(new Point(2,2), new Point(1,2), 3);
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
	
	private static void addBPGMapping(Point g, Point b){
		
		ArrayList<Point> list  = gBPTable.get(g);
		if(list==null){
			list = new ArrayList<Point>();
			list.add(b);
			gBPTable.put(g, list);
		}
		else
			list.add(b);
		
		list  = bPGTable.get(g);
		if(list==null){
			list = new ArrayList<Point>();
			list.add(g);
			bPGTable.put(b, list);
		}
		else
			list.add(g);
	}
	
	private static void findKNN( Point q, Point nn, int K ){
		ArrayList<Pair<Point,Integer>>  bPDTable = new ArrayList<Pair<Point,Integer>>();
		PriorityQueue<PointOfInterest> Q = new PriorityQueue<PointOfInterest>();
		Point POI = null;
		PointOfInterest poi;
		int minDist,gDist;
		Iterator<PointOfInterest> it = null;
		
		//Add q distance with all Border Points in NN
		for(Point b:gBPTable.get(nn)){
			minDist = eucledianDistance(b, q);
			
			for(Point g:bPGTable.get(b)){
				it = Q.iterator();
				gDist = minDist+distTable.get(g).getDistanceTwoPoints(b, g);
				
				while(it.hasNext()){
					poi = it.next();
					if(poi.equals(g)){
						//Update distance
						gDist = Math.min(poi.dist,gDist);
						Q.remove(poi);
						break;
					}
				}
				Q.add(new PointOfInterest(g, gDist));
			}
			
			bPDTable.add(new Pair<Point, Integer>(b, minDist));
		}
		
		for(int i=1;i<K && Q.size()>0;i++){
			//select minimum Point of Interest as POI from all reachable with b
			POI = Q.poll().p;
			
			Log.l(POI);
			
			//Update new Border Points to check
			// Add/Update distance of POI reachable now
			boolean bPPresent;
			for(Point b:gBPTable.get(POI)){
				minDist = Integer.MAX_VALUE;
				bPPresent = false;
				for(Pair<Point,Integer> pair: bPDTable){
					if(pair.getElement0().equals(b)){
						bPPresent = true;
						break;
					}
					try{
						minDist = Math.min(minDist, distTable.get(POI).getDistanceTwoPoints(b, pair.getElement0()));
					}
					catch(Exception e){}
				}
				if(!bPPresent){
					bPDTable.add(new Pair<Point, Integer>(b, minDist));
					
					for(Point g:bPGTable.get(b)){
						it = Q.iterator();
						gDist = minDist+distTable.get(g).getDistanceTwoPoints(b, g);
						
						while(it.hasNext()){
							poi = it.next();
							if(poi.equals(g)){
								//Update distance
								gDist = Math.min(poi.dist,gDist);
								Q.remove(poi);
								break;
							}
						}
						Q.add(new PointOfInterest(g, gDist));
					}
				}
			}
		}
	}
	
	private static int eucledianDistance( Point p1, Point p2 ){
		return (int)p1.distance(p2);
	}
}