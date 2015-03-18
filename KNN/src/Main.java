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
		Scanner in = new Scanner(new File("res/nvd2"));
		Point p1,p2;
		double x,y;
		int k;
		double w;
		Vertex g,u,v;
		Graph G;
		Dijisktra dijisktra;
		ArrayList<Vertex> borderPoints;
		ArrayList<PointOfInterest> pointOfInterests = new ArrayList<PointOfInterest>();
		
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
			pointOfInterests.add(new PointOfInterest(p1));
			
			k = in.nextInt();
			while(k-->0){
				//Edges belonging to generator g
				x = in.nextDouble();
				y = in.nextDouble();
				p1 = new Point(x,y);
				
				x = in.nextDouble();
				y = in.nextDouble();
				p2 = new Point(x,y);
				
				w = in.nextDouble();
				
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
				w = in.nextDouble();
				borderPoints.add(G.findV(new Point(x,y)));
				addBPGMapping(g.p,new Point(x,y));
			}
			
			//Run Dijisktra Algorithm on graph formed above to compute distance between each border points
			for(Vertex b1:borderPoints){
				dijisktra.init(G,b1);
				dijisktra.run();
				for(Vertex b2:borderPoints){
					addD(g.p,b1.p,b2.p,b2.dist);
				}
				addD(g.p,b1.p,g.p,g.dist);
			}
		}
		
		//Log.d(distTable);
		in.close();
		findKNN(new Point(1,1), new Point(2,4), 3, pointOfInterests);
		findKNN(new Point(1,3), new Point(2,4), 3, pointOfInterests);
		findKNN(new Point(2,5), new Point(2,4), 3, pointOfInterests);
		findKNN(new Point(4,6), new Point(4,7), 3, pointOfInterests);
		findKNN(new Point(2,4), new Point(2,4), 3, pointOfInterests);
	}
	
	private static void addD( Point g, Point p1, Point p2, double dist ){
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
		
		ArrayList<Point> gBPList = gBPTable.get(g);
		if(gBPList==null){
			gBPList = new ArrayList<Point>();
			gBPList.add(b);
			gBPTable.put(g, gBPList);
		}
		else
			gBPList.add(b);
		
		ArrayList<Point> bPGList  = bPGTable.get(b);
		if(bPGList==null){
			bPGList = new ArrayList<Point>();
			bPGList.add(g);
			bPGTable.put(b, bPGList);
		}
		else
			bPGList.add(g);
	}
	
	private static void findKNN( Point q, Point nn, int K, ArrayList<PointOfInterest> pointOfInterests ){
		ArrayList<Pair<Point,Double>>  bPDTable = new ArrayList<Pair<Point,Double>>();
		PriorityQueue<PointOfInterest> Q = new PriorityQueue<PointOfInterest>();
		
		PointOfInterest poi, poppedPOI;
		double minDist,gDist;
		Iterator<PointOfInterest> it = null;
		
		for(PointOfInterest tempPOI: pointOfInterests){
			if(tempPOI.p.equals(nn))
				tempPOI.dist=0;
			else
				tempPOI.dist = Double.MAX_VALUE;
			Q.add(tempPOI);
		}
		
		for(Point b:gBPTable.get(nn)){
			minDist = eucledianDistance(b, q);
			
			for(Point g:bPGTable.get(b)){
				it = Q.iterator();
				gDist = minDist+distTable.get(g).getDistanceTwoPoints(b, g);
				while(it.hasNext()){
					poi = it.next();
					if(poi.p.equals(g)){
						gDist = Math.min(poi.dist,gDist);
						Q.remove(poi);
						Q.add(new PointOfInterest(g, gDist));
						break;
					}
				}
			}
			bPDTable.add(new Pair<Point, Double>(b, minDist));
		}
		
		/*Log.d("----------bPDTable--------");
		Log.d(bPDTable);
		
		Log.d("");
		Log.d("----------Q----------");
		Log.d(Q);
		Log.d("");*/
		
		poppedPOI = Q.poll();
		poppedPOI.popped=true;
		Log.l(poppedPOI.p);
		
		/*Log.d("----------bPDTable--------");
		Log.d(bPDTable);
		
		Log.d("");
		Log.d("----------Q----------");
		Log.d(Q);
		Log.d("");*/
	
		for(int i=1;i<K && Q.size()>0;i++){
			poppedPOI = Q.poll();
			poppedPOI.popped=true;
			
			Log.l(poppedPOI.p);
			
			/*Log.d("----------bPDTable--------");
			Log.d(bPDTable);
			
			Log.d("");
			Log.d("----------Q----------");
			Log.d(Q);
			Log.d("");*/
			
			boolean bPPresent;
			for(Point b:gBPTable.get(poppedPOI.p)){
				minDist = Double.MAX_VALUE;
				bPPresent = false;
				for(Pair<Point,Double> pair: bPDTable){
					if(pair.getElement0().equals(b)){
						bPPresent = true;
						break;
					}
					try{
						minDist = Math.min(minDist, distTable.get(poppedPOI.p).getDistanceTwoPoints(b, pair.getElement0())+pair.getElement1());
					}
					catch(Exception e){}
				}
				if(!bPPresent){
					bPDTable.add(new Pair<Point, Double>(b, minDist));
					
					for(Point g:bPGTable.get(b)){
						it = Q.iterator();
						gDist = minDist+distTable.get(g).getDistanceTwoPoints(b, g);
						
						while(it.hasNext()){
							poi = it.next();
							if(!poi.popped && poi.equals(g)){
								gDist = Math.min(poi.dist,gDist);
								Q.remove(poi);
								Q.add(new PointOfInterest(g, gDist));
								break;
							}
						}
					}
				}
			}
		}
		Log.l("-------");
	}
	
	private static int eucledianDistance( Point p1, Point p2 ){
		return (int)p1.distance(p2);
	}
}