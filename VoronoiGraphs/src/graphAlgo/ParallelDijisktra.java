package graphAlgo;

import graphAlgo.Graph.Edge;
import graphAlgo.Graph.Vertex;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ParallelDijisktra {
	
	private PriorityQueue< Graph.Vertex > Q;
	
	public ParallelDijisktra(){
		Q=new PriorityQueue<Graph.Vertex>();
	}
	
	public void init( Graph G, ArrayList<Vertex> Sources ){
		boolean isSource;
		
		for(Vertex v: G.getV()){
			isSource=false;
			for(Vertex s: Sources){
				if(v==s)
					isSource=true;
			}
			if(isSource){
				v.dist = 0;
				v.pi = v;
			}
			Q.add(v);
		}
	}
	
	public void generateVoronoi(){
		while(!this.Q.isEmpty()){
			Vertex u = this.Q.poll();
			u.flag = true;
			
			for(Edge e:u.adjancencies){
				if(!e.v.flag){
					int newDist = u.dist+e.w;
					if(newDist<e.v.dist){
						Q.remove(e.v);
						e.v.dist=newDist;
						e.v.pi = u.pi;
						Q.add(e.v);
					}
				}
			}
		}
	}
	
} 