package graphAlgo;

import graphAlgo.Vertex.Generator;

import java.util.PriorityQueue;

import util.Log;
import util.Pair;

public class ParallelDijisktra {
	
	private PriorityQueue< Vertex > Q;
	private NetworkVoronoiDiagram nvd = null;
	
	public ParallelDijisktra(){
	}
	
	public void init( Graph G ){
		Q=new PriorityQueue<Vertex>();
		nvd=new NetworkVoronoiDiagram();
		
		for(Vertex v: G.getV()){
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
							 && eucledianDistance( u.pi, e.v.p ) < eucledianDistance( e.v.pi, e.v.p )
							 )
						)
					{
						Q.remove(e.v);
						e.v.dist=newDist;
						e.v.pi = u.pi;
						Q.add(e.v);
					}
					
					for(Pair<Point,Generator> u_p : u.pis){
						sflag=false;
						for(Pair<Point,Generator> v_p : e.v.pis){
							if(u_p.getElement0().equals(v_p.getElement0())){
								sflag=true;
								newDist = u_p.getElement1().dist + e.w;
								if(newDist < v_p.getElement1().dist){
									v_p.setElement1(new Generator(u.p,e.w,newDist));
								}
								break;
							}
						}
						if(!sflag)
							e.v.pis.add(new Pair<Point,Generator>(u_p.getElement0(),new Generator(u.p,e.w,newDist)));
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
			for(Pair<Point,Generator> pair : v.pis){
				if(v.dist == pair.getElement1().dist){
					this.nvd.add( pair.getElement0(),
							new NetworkVoronoiDiagram.Edge(
									pair.getElement1().p, v.p, pair.getElement1().w, pair.getElement1().dist
									) 
					);		
					output=v.p+"";
					output+=" | ";
					output+=pair.getElement1().p;
					output+=" | ";
					output+=pair.getElement0();
					Log.l(output);
				}
			}
		}
		Log.l("");
		Log.l(this.nvd);
	}
} 