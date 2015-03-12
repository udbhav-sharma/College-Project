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

public class Main {
	private static HashMap<Point, DistanceTable> distTable;
	
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
}
