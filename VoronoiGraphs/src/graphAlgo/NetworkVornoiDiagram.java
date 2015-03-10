package graphAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NetworkVornoiDiagram {
	public HashMap<Point, ArrayList<Edge>> nvp;
	
	public NetworkVornoiDiagram(){
		this.nvp = new HashMap<Point, ArrayList<Edge>>();
	}
	
	public void add(Point p,Edge e){
		ArrayList<Edge> list = this.nvp.get(p);
		if(list==null){
			list=new ArrayList<Edge>();
			list.add(e);
			this.nvp.put(p, list);
		}
		else
			list.add(e);
	}
	
	public String toString(){
		String output="--------Network Vornoi Diagram-------\n";
		Iterator it = this.nvp.entrySet().iterator();
		while(it.hasNext()){
			output+="Network Vornoi Polygon\n";
			HashMap.Entry<Point,ArrayList<Point>> pair = (HashMap.Entry<Point,ArrayList<Point>>)it.next();
			output+="Generator: "+pair.getKey() + "\n";
			output+= pair.getValue()+"\n";
			it.remove(); // avoids a ConcurrentModificationException
					
		}
		return output;
	}
	
	public static class Edge{
		public Point p1;
		public Point p2;
		int w;
		
		public Edge(Point p1,Point p2,int w){
			this.p1=p1;
			this.p2=p2;
			this.w=w;
		}
		
		public String toString(){
			return p1+" "+p2+" "+w;
		}
		
	}
}
