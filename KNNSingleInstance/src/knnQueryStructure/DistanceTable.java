package knnQueryStructure;

import java.util.HashMap;
import java.util.Iterator;

import util.Point;

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
		Iterator< HashMap.Entry<Point,HashMap<Point,Double>> > it1 = this.dist.entrySet().iterator();
		while(it1.hasNext()){
			HashMap.Entry<Point,HashMap<Point,Double>> pair1 = it1.next();
			
			Iterator<HashMap.Entry<Point,Double>> it2 = pair1.getValue().entrySet().iterator();
			while(it2.hasNext()){
				HashMap.Entry<Point,Double> pair2 = it2.next();
				output += pair1.getKey()+", ";
				output += pair2.getKey()+", ";
				output += pair2.getValue()+"\n";
			}
		}
		return output;
	}
}
