import graphAlgo.Graph;
import graphAlgo.Graph.Vertex;
import graphAlgo.ParallelDijisktra;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import util.Log;


public class Main {
	
	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File("res/roads"));
		Graph G = new Graph();
		int i,j,x,y,w;
		while(in.hasNextInt()){
			i = in.nextInt();
			j = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();

			Vertex u = G.findV(new Point(i,j));
			Vertex v = G.findV(new Point(x,y));

			if( u == null )
				u = G.addV(new Point(i,j));
			
			if( v == null )
				v = G.addV(new Point(x,y));
			
			w = in.nextInt();
			G.addE(u,v,w);
		}
		in.close();
		
		Log.l(G);
		
		in = new Scanner(new File("res/pointofinterests"));
		ArrayList<Vertex> Sources = new ArrayList<Graph.Vertex>();
		while(in.hasNextInt()){
			x = in.nextInt();
			y = in.nextInt();
			Sources.add(G.findV(new Point(x,y)));
			
		}
		in.close();
		
		ParallelDijisktra dijisktra = new ParallelDijisktra();
		dijisktra.init(G, Sources);
		dijisktra.generateVoronoi();
		
		Log.l(G);
		
	}
}
