package graphAlgo;

import java.util.PriorityQueue;

import util.Log;
import util.Pair;

public class ParallelDijisktra {
	
	private PriorityQueue< Vertex > Q;
	private NetworkVoronoiDiagram nvd = null;
	
	public ParallelDijisktra(){
		Q=new PriorityQueue<Vertex>();
		nvd=new NetworkVoronoiDiagram();
	}
	
	public void init( Graph G ){
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
							 && eucledianDistance( u.pi.p, e.v.p ) < eucledianDistance( e.v.pi.p, e.v.p )
							 )
						)
					{
						Q.remove(e.v);
						e.v.dist=newDist;
						e.v.pi = u.pi;
						Q.add(e.v);
					}
					
					for(Pair<Vertex,Edge> u_p : u.pis){
						sflag=false;
						for(Pair<Vertex,Edge> v_p : e.v.pis){
							if(u_p.getElement0().p.equals(v_p.getElement0().p)){
								sflag=true;
								newDist = u_p.getElement1().w + e.w;
								if(newDist < v_p.getElement1().w){
									v_p.setElement1(new Edge(u,newDist));
								}
								break;
							}
						}
						if(!sflag)
							e.v.pis.add(new Pair<Vertex,Edge>(u_p.getElement0(),new Edge(u,newDist)));
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
			for(Pair<Vertex,Edge> pair : v.pis){
				if(v.dist == pair.getElement1().w){
					this.nvd.add( pair.getElement0().p,
							new NetworkVoronoiDiagram.Edge(pair.getElement1().v.p, v.p, pair.getElement1().w) );		
					output=v.p.getX()+" "+v.p.getY();
					output+=" | ";
					output+=pair.getElement1().v.p.getX()+" "+pair.getElement1().v.p.getY();
					output+=" | ";
					output+=pair.getElement0().p.getX()+" "+pair.getElement0().p.getY();
					Log.l(output);
				}
			}
		}
		Log.l("");
		Log.l(this.nvd);
	}
} 