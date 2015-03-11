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
					if( newDist < e.v.dist ){
						Q.remove(e.v);
						e.v.dist=newDist;
						Q.add(e.v);
					}
				}
			}
		}
	}
} 