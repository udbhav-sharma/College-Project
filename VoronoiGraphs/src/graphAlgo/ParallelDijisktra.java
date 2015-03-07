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
				v.pis.add(new Pair<Vertex,Integer>(v,0));
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
					
					for(Pair<Vertex,Integer> u_p : u.pis){
						sflag=false;
						for(Pair<Vertex,Integer> v_p : e.v.pis){
							if(u_p.getElement0().p.equals(v_p.getElement0().p)){
								sflag=true;
								newDist = u_p.getElement1() + e.w;
								if(newDist < v_p.getElement1()){
									v_p.setElement1(newDist);
								}
								break;
							}
						}
						if(!sflag)
							e.v.pis.add(new Pair<Vertex,Integer>(u_p.getElement0(),newDist));
					}
				}
			}
		}
	}
	
	private double eucledianDistance( Point p1, Point p2 ){
		return p1.distance(p2);
	}
	
	public void generateVoronoiCell(Graph G){
		String output;
		
		for(Vertex v: G.getV()){
			for(Pair<Vertex,Integer> pair : v.pis){
				if(v.dist == pair.getElement1()){
					output=v.p.getX()+" "+v.p.getY();
					output+=" | ";
					output+=pair.getElement0().p.getX()+" "+pair.getElement0().p.getY();
					Log.l(output);
				}
			}
		}
	}
	
} 