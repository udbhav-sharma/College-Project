package structure;

import java.util.HashMap;
import java.util.Iterator;

import util.Log;

public class DistanceTable {
	public HashMap<Point, HashMap<Point, Double>> dist;
	public DistanceTable(){
		dist = new HashMap<Point, HashMap<Point,Double>>();
	}
	
	public void addD(Point p1, Point p2, double d){
		HashMap<Point,Double> list = dist.get(p1);
		if(list==null){
			list = new HashMap<Point, Double>();
			list.put(p2, d);
			dist.put(p1, list);
		}
		else
			list.put(p2,d);
	}
	
	public double getDistanceTwoPoints(Point b1, Point b2){
		double d = Double.MAX_VALUE;
		try{
			d = dist.get(b1).get(b2);
		}
		catch(NullPointerException e){
			//Log.e("No distance between "+b1+" "+b2);
		}
		return d;
	}
	
	public String toString(){
		String output = "";
		Iterator it1 = this.dist.entrySet().iterator();
		while(it1.hasNext()){
			HashMap.Entry<Point,HashMap<Point,Integer>> pair1 = (HashMap.Entry<Point,HashMap<Point,Integer>>)it1.next();
			
			Iterator it2 = pair1.getValue().entrySet().iterator();
			while(it2.hasNext()){
				HashMap.Entry<Point,Integer> pair2 = (HashMap.Entry<Point,Integer>)it2.next();
				output += pair1.getKey()+", ";
				output += pair2.getKey()+", ";
				output += pair2.getValue()+"\n";
			}
		}
		return output;
	}
}
