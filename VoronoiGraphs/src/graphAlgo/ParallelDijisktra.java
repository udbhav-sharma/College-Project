package graphAlgo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import util.Log;
import util.Pair;

public class ParallelDijisktra {
	
	private PriorityQueue< Vertex > Q;
	
	public ParallelDijisktra(){
		Q=new PriorityQueue<Vertex>();
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
		int newDist;
		boolean sflag;
		
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
					
					if(newDist <= e.v.dist){
						sflag=false;
						for(Pair<Vertex,Integer> p : e.v.pis)
							if(p.getElement0().p.equals(u.pi.p)) {
								sflag=true;
								if( newDist < p.getElement1())
									p.setElement1(newDist);
							}
						if(!sflag)
							e.v.pis.add(new Pair<Vertex,Integer>(u.pi,newDist));
					}
				}
			}
		}
	}
	
	private double eucledianDistance( Point p1, Point p2 ){
		return p1.distance(p2);
	}
	
	public void generateVoronoiCell(Graph G,ArrayList<Vertex> Sources){
		String output;
		for(Vertex v: Sources){
			output="";
			output+="("+v.p.getX()+","+v.p.getY()+")";
			output+=" | ";
			output+="("+v.p.getX()+","+v.p.getY()+")";
			Log.l(output);
		}
		
		for(Vertex v: G.getV()){
			for(Pair<Vertex,Integer> pair : v.pis){
				if(v.dist == pair.getElement1()){
					output="";
					output+="("+v.p.getX()+","+v.p.getY()+")";
					output+=" | ";
					output+="("+pair.getElement0().p.getX()+","+pair.getElement0().p.getY()+")";
					Log.l(output);
				}
			}
		}
	}
	
} 