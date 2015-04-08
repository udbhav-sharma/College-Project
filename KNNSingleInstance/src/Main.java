
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import graphAlgo.Graph;
import graphAlgo.Vertex;
import graphAlgo.ParallelDijisktra;
import graphAlgo.Vertex.Generator;
import knnQueryStructure.DistanceTable;
import knnQueryStructure.PointOfInterest;
import knnQueryStructure.Dijisktra;
import util.Log;
import util.Pair;
import util.Point;
import vquadTreeStructure.Rectangle;
import vquadTreeStructure.Tree;

public class Main {
	
	/* 
	 * VQuad Tree variables
	 * */
	private static HashMap<Point, ArrayList<Point>> points = new HashMap<Point, ArrayList<Point>>();
	
	/*
	 * kNN query variables
	 * */
	private static HashMap<Point, DistanceTable> distTable;
	private static HashMap<Point, ArrayList<Point>> gBPTable;
	private static HashMap<Point,ArrayList<Point>> bPGTable;
	
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
		
		ParallelDijisktra parallelDijisktra = new ParallelDijisktra();
		parallelDijisktra.init(G);
		parallelDijisktra.generateVoronoi();
		
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
	
		/*
		 * kNN query
		 * */
		
		/*Point p1,p2;
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
		findKNN(new Point(2,4), new Point(2,4), 3, pointOfInterests);*/
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
		
		poppedPOI = Q.poll();
		poppedPOI.popped=true;
		Log.l(poppedPOI.p);
	
		for(int i=1;i<K && Q.size()>0;i++){
			poppedPOI = Q.poll();
			poppedPOI.popped=true;
			
			Log.l(poppedPOI.p);
			
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
