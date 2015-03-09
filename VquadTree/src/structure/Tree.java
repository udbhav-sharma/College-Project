package structure;

import java.util.ArrayList;
import java.util.HashSet;

import structure.Point;
import structure.Rectangle;
import util.Log;
import util.Pair;

public class Tree {
	
	public Node root;

	public Tree(){
		root=new Node();
	}
	
	public Node init (Rectangle r, ArrayList< Pair<Point, ArrayList<Point>>> points){
		this.root = formTree(r,points);
		
		return this.root;
	}
	
	public Node formTree( Rectangle r, ArrayList< Pair<Point, ArrayList<Point>>> points ){
		Node newNode = new Node();
		boolean split = false;
		ArrayList<Point> lastPOI = null;

		newNode.setR(r);
		
		if(points.size()>0){
			lastPOI = points.get(0).getElement1();
			for(Pair<Point,ArrayList<Point>> pair:points){
				lastPOI = intersect(lastPOI,pair.getElement1());
				if(lastPOI.size()==0)
					split = true;
			}
		}
		
		if(!split){
			newNode.setLeaf(true);
			if(points.size()>0)
				newNode.poi=lastPOI.get(0);
			//Log.d(newNode);
			/*for(Pair<Point,Point> pair: points){
				Log.d(pair.getElement0());
			}*/
		}
		else{
			//Log.d(newNode);
			double newX = r.getX();
			double newY = r.getY();
			double newW = r.getWidth();
			double newH = r.getHeight();
			Rectangle newRectangle;
			
			newRectangle = new Rectangle();
			newRectangle.setRect(newX,newY,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX+newW/2,newY,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX,newY-newH/2,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX+newW/2,newY-newH/2,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
		}
		
		return newNode;
	}
	
	public Pair<Point,Double> getGenerator(Point p){
		HashSet<Pair<Point,Double>> result = new HashSet<Pair<Point,Double>>();
		this.get1NN(this.root,p,result);
		
		Pair<Point,Double> minPair = null;
		
		for(Pair<Point,Double> pair:result){
			if(minPair == null || pair.getElement1()<minPair.getElement1())
				minPair=pair;
		}
		return minPair;
	}
	
	public String toString(){
		String output="---------Vquad Tree--------\n";
		output+=printTree(this.root);
		return output;
	}
	
	public int getheight(){
		return height(this.root);
	}
	
	private ArrayList<Point> intersect(ArrayList<Point> list1, ArrayList<Point> list2){
		ArrayList<Point> newList = new ArrayList<Point>();
		
		for(Point p1: list1){
			for(Point p2: list2){
				if(p1.equals(p2)){
					newList.add(new Point(p1));
					break;
				}
			}
		}
		
		return newList;
	}
	
	private ArrayList<Pair<Point, ArrayList<Point>>> filterPoints(ArrayList<Pair<Point,ArrayList<Point>>> points, Rectangle r){
		ArrayList<Pair<Point, ArrayList<Point>>> newPoints=new ArrayList<Pair<Point,ArrayList<Point>>>();
		
		for(Pair<Point,ArrayList<Point>> pair:points){
			if(r.contains( pair.getElement0())){
				newPoints.add(pair);
			}
		}
		
		return newPoints;
	}
	
	private void get1NN(Node node, Point p, HashSet<Pair<Point,Double>> result){
		if(node.r.contains(p)){
			if(node.isLeaf){
				boolean isPresent = false;
				for(Pair<Point,Double> pair:result){
					if(pair.getElement0().equals(node.poi))
						isPresent=true;
				}
				if(!isPresent)
					result.add(new Pair<Point,Double>(node.poi,p.distance(node.poi)));
			}
			else{
				for(Node child:node.children){
					get1NN(child, p, result);
				}
			}
		}
		return;
	}
	
	private int height(Node node){
		int h=0;
		for(Node child:node.children){
			Math.max(height(child)+1, h);
		}
		return h;
	}
	
	private String printTree(Node node){
		String output="";
		output+=node+"\n";
		for(Node n:node.children){	
			output+=printTree(n);
		}
		return output;
	}
}
