package graphAlgo;

import graphAlgo.Vertex.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import util.Log;
import util.Pair;

public class ParallelDijisktra {
	
	private PriorityQueue< Vertex > Q;
	private Graph G=null;
	private NetworkVoronoiDiagram nvd = null;
	
	public ParallelDijisktra(){}
	
	public void init( Graph G ){
		this.G=G;
		Q=new PriorityQueue<Vertex>();
		nvd=new NetworkVoronoiDiagram();
		
		for(Vertex v: G.getV()){
			Q.add(v);
		}
	}
	
	public void generateVoronoi(){
		double newDist;
		
		while(!this.Q.isEmpty()){
			Vertex u = this.Q.poll();
			u.flag = true;
			
			for(Edge e:u.adjacencies){
				if(!e.v.flag){
					newDist = u.dist+e.w;
					if( (newDist < e.v.dist)){
						Q.remove(e.v);
						e.v.dist=newDist;
						e.v.pi = u.pi;
						Q.add(e.v);
					}
					
					Iterator it = u.pis.entrySet().iterator(); 
					while(it.hasNext()){
						HashMap.Entry<Point,Generator> u_p = (HashMap.Entry<Point,Generator>)it.next();
						newDist = u_p.getValue().dist + e.w;
						
						Generator v_g = e.v.pis.get(u_p.getKey());
						if(v_g!=null){
							if(newDist < v_g.dist){
								v_g.p = u.p;
								v_g.w = e.w;
								v_g.dist = newDist;
							}
						}
						else if(v_g==null && newDist <= e.v.dist)
							e.v.pis.put(u_p.getKey(),new Generator(u.p,e.w,newDist));
					}
				}
			}
		}
		Log.l(G);
		this.updatePOI();
		this.addNewBorderPoint();
		this.generateNVD();
	}
	
	private void addNewBorderPoint(){
		ArrayList<Vertex> vertices = this.G.getV();
		
		for(int i=0;i<vertices.size();i++){
			Vertex v = vertices.get(i);
			ArrayList<Edge> newAdjacency = new ArrayList<Edge>();
			for(Edge e : v.adjacencies ){
				if(splitEdge(v, e.v)){
					
					//Add new Edges
					Pair<Double,Double> newWeights = getNewEdgeWeight(v,e.v,e.w);
					Pair<Double,Double> newCoordinates = getNewCoordinates(v,e.v);
					Vertex u = G.findV(new Point(newCoordinates.getElement0(), newCoordinates.getElement1()));
					if(u==null){
						u = this.G.addV(new Point(newCoordinates.getElement0(), newCoordinates.getElement1()), false);
						u.dist = v.dist+newWeights.getElement0();
						u.adjacencies.add(new Edge(v,newWeights.getElement0()));
						u.pis = union(v,e.v);
					}
					newAdjacency.add(new Edge(u,newWeights.getElement0()));
				}
				else
					newAdjacency.add(e);
			}
			v.adjacencies = newAdjacency;
		}
	}
	
	public void generateNVD(){
		String output;
		
		for(Vertex v: this.G.getV()){
			//Fetching Border Points
			if(v.pis.size()>1){
				Iterator it = v.pis.entrySet().iterator(); 
				while(it.hasNext()){
					HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
					this.nvd.addB(pair.getKey(), v.p, pair.getValue().dist);
					output=v.p+"";
					output+=" | ";
					output+=pair.getValue().p;
					output+=" | ";
					output+=pair.getKey();
					Log.l(output);
				}
			}
			
			//Add Edges to the NVP
			for(Edge e: v.adjacencies){
				ArrayList<Point> pois= intersect(v,e.v);
				for(Point p:pois)
					this.nvd.addE(p, new NetworkVoronoiDiagram.Edge(v.p, e.v.p, e.w));
			}
		}
		Log.l("");
		Log.l(this.nvd);
	}
	
	private void updatePOI(){
		for(Vertex v: this.G.getV()){
			Iterator it = v.pis.entrySet().iterator(); 
			while(it.hasNext()){
				HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
				if(v.dist == pair.getValue().dist){}
				else it.remove();
			}
		}
	}
	
	private ArrayList<Point> intersect( Vertex u, Vertex v ){
		ArrayList<Point> intersection = new ArrayList<Point>();
		
		Iterator it = u.pis.entrySet().iterator(); 
		while(it.hasNext()){
			HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
			if(v.pis.containsKey(pair.getKey()))
				intersection.add(pair.getKey());
		}
		
		return intersection;
	}
	
	private HashMap<Point,Generator> union( Vertex u, Vertex v ){
		HashMap<Point, Generator> newPOI = new HashMap<Point, Vertex.Generator>();

		newPOI.putAll(u.pis);
		
		Iterator it = v.pis.entrySet().iterator(); 
		while(it.hasNext()){
			HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
			if(!newPOI.containsKey(pair.getKey()))
				newPOI.put(pair.getKey(),pair.getValue());
		}
		
		return newPOI;
	}
	
	private boolean splitEdge( Vertex u, Vertex v ){
		if(u.p.isNull() || v.p.isNull())
			return false;
		
		if(u.pis.size()<=v.pis.size()){
			Iterator it = u.pis.entrySet().iterator(); 
			while(it.hasNext()){
				HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
				if(!v.pis.containsKey(pair.getKey()))
					return true;
			}
			return false;
		}
		else{
			Iterator it = v.pis.entrySet().iterator(); 
			while(it.hasNext()){
				HashMap.Entry<Point,Generator> pair = (HashMap.Entry<Point,Generator>)it.next();
				if(!u.pis.containsKey(pair.getKey()))
					return true;
			}
			return false;
		}
	}
	
	private Pair<Double,Double> getNewEdgeWeight( Vertex u, Vertex v, double w ){
		double x = (w+Math.abs(u.dist-v.dist))/2.0;
		double y = (w+Math.abs(v.dist-u.dist))/2.0;
		return new Pair<Double, Double>(x, y);
	}
	
	private Pair<Double,Double> getNewCoordinates( Vertex u, Vertex v ){
		double x = (u.p.getX()+v.p.getX())/2.0;
		double y = (u.p.getY()+v.p.getY())/2.0;
		return new Pair<Double, Double>(x, y);
	}
} 