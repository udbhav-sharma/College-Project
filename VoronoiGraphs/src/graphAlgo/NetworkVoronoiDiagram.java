package graphAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import util.Pair;

public class NetworkVoronoiDiagram {
	public HashMap<Point, NetworkVoronoiPolygon> nvps;
	
	public NetworkVoronoiDiagram(){
		this.nvps = new HashMap<Point, NetworkVoronoiPolygon>();
	}
	
	public void add(Point p,Edge e){
		NetworkVoronoiPolygon nvp = this.nvps.get(p);
		if(nvp==null){
			nvp = new NetworkVoronoiPolygon(p);
			nvp.graph.add(e);
			this.nvps.put(p, nvp);
		}
		else
			nvp.graph.add(e);
	}
	
	public String toString(){
		String output="--------Network Voronoi Diagram-------\n";
		Iterator it = this.nvps.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry<Point,ArrayList<Point>> pair = (HashMap.Entry<Point,ArrayList<Point>>)it.next();
			output+= pair.getValue()+"\n";
			it.remove(); // avoids a ConcurrentModificationException
		}
		return output;
	}
	
	public static class NetworkVoronoiPolygon{
		public Point p = null;
		public ArrayList<Edge> graph = null;
		public ArrayList<Pair<Point,Integer>> borderPoints = null;
		
		public NetworkVoronoiPolygon(Point p){
			this.p = p;
			this.graph = new ArrayList<NetworkVoronoiDiagram.Edge>();
			this.borderPoints = new ArrayList<Pair<Point,Integer>>();
		}
		
		public String toString(){
			String output = "Network Voronoi Polygon\n";
			output += "Generator: "+p+"\n";
			output += "Graph: "+this.graph.size()+"\n";
			output += this.graph+"\n";
			output += "Border Points: "+this.borderPoints.size()+"\n";
			output += this.borderPoints;
			return output;
		}
	}
	
	public static class Edge{
		public Point p1;
		public Point p2;
		public int w;
		public int dist;
		
		public Edge(Point p1, Point p2, int w, int dist){
			this.p1=p1;
			this.p2=p2;
			this.w=w;
			this.dist=dist;
		}
		
		public String toString(){
			return "{"+p1+", "+p2+", "+w+", "+dist+"}";
		}
	}
}
