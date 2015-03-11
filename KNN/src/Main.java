import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import structure.Dijisktra;
import structure.Graph;
import structure.Point;
import structure.Vertex;
import util.Log;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File("res/nvd"));
		Point g,p1,p2;
		double x,y;
		int k,w;
		Vertex u,v;
		Graph G;
		Dijisktra dijisktra;
		ArrayList<Vertex> borderPoints;
		
		while(in.hasNext()){
			G = new Graph();
			dijisktra = new Dijisktra();
			borderPoints = new ArrayList<Vertex>();
			
			//Generator g
			x = in.nextDouble();
			y = in.nextDouble();
			g = new Point(x,y);
			G.addV(g);
			
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
			//Log.d(G);
			//Log.d(borderPoints);
			Log.l("-------For "+g+"------");
			for(Vertex b1:borderPoints){
				dijisktra.init(G,b1);
				dijisktra.run();
				for(Vertex b2:borderPoints){
					Log.l(b1.p+" "+b2.p+" "+b2.dist);
				}
			}
		}
		in.close();
	}
}
