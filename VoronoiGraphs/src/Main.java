import graphAlgo.Graph;
import graphAlgo.Vertex;
import graphAlgo.ParallelDijisktra;
import graphAlgo.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException{ 
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
	}
}
