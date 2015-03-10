import java.util.Scanner;

import structure.Point;

public class Main {
	public static void main(String args[]){
		Scanner in = new Scanner("res/nvd");
		Point g,u,v;
		double x,y;
		int k,w;
		
		while(in.hasNext()){
			//generator g
			x = in.nextDouble();
			y = in.nextDouble();
			g = new Point(x,y);
			
			k = in.nextInt();
			while(k-->0){
				//Edges belonging to generator g
				x = in.nextDouble();
				y = in.nextDouble();
				u = new Point(x,y);
				
				x = in.nextDouble();
				y = in.nextDouble();
				v = new Point(x,y);
				
				w = in.nextInt();
			}
			
			//run Dijikstra Algorithm on graph formed above to compute distance between each border points
		}
		
		in.close();
	}
}
