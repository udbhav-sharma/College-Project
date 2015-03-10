package structure;

import java.util.PriorityQueue;

import util.Log;
import util.Pair;

public class Dijisktra {
	
	private PriorityQueue< Vertex > Q = null;
	private final int sourceDist = 0;
	
	public Dijisktra(){}
	
	public void init( Graph G, Vertex s ){
		Q=new PriorityQueue<Vertex>();
		
		for(Vertex v: G.getV())
			v.reinit();
		s.dist = sourceDist;
		
		for(Vertex v: G.getV())
			Q.add(v);
	}
	
	public void run(){
		int newDist;
		
		while(!this.Q.isEmpty()){
			Vertex u = this.Q.poll();
			u.flag = true;
			
			for(Edge e:u.adjancencies){
				if(!e.v.flag){
					newDist = u.dist+e.w;
					if( (newDist < e.v.dist) ||
							(newDist == e.v.dist 
							 && eucledianDistance( u.pi.p, e.v.p ) < eucledianDistance( e.v.pi.p, e.v.p )
							 )
						)
					{
						Q.remove(e.v);
						e.v.dist=newDist;
						e.v.pi = u.pi;
						Q.add(e.v);
					}
				}
			}
		}
	}
	
	private double eucledianDistance( Point p1, Point p2 ){
		return p1.distance(p2);
	}
	
} 