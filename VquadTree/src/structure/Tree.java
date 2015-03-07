package structure;

import java.util.ArrayList;
import java.util.LinkedList;

import structure.Point;
import structure.Rectangle;
import util.Log;
import util.Pair;

public class Tree {
	
	public Node root;

	public Tree(){
		root=new Node();
	}
	
	public Node init (Rectangle r, ArrayList< Pair<Point, Point>> points){
		this.root = formTree(r,points);
		
		return this.root;
	}
	
	public Node formTree( Rectangle r, ArrayList< Pair<Point, Point>> points ){
		Node newNode = new Node();
		boolean split = false;
		Point lastPOI = null;

		newNode.setR(r);
		
		if(points.size()>0){
			lastPOI = points.get(0).getElement1();
			for(Pair<Point,Point> pair:points){
				if(!pair.getElement1().equals(lastPOI))
					split = true;
			}
		}
		
		if(!split){
			newNode.setLeaf(true);
			if(points.size()>0)
				newNode.poi=lastPOI;
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
	
	public ArrayList< Pair<Point, Point>> filterPoints(ArrayList< Pair<Point, Point>> points, Rectangle r){
		ArrayList< Pair<Point, Point>> newPoints=new ArrayList<Pair<Point,Point>>();
		
		for(Pair<Point,Point> pair:points){
			if(r.contains( pair.getElement0())){
				newPoints.add(pair);
			}
		}
		
		return newPoints;
	}
	
	public String toString(){
		String output="---------Vquad Tree--------\n";
		output+=printTree(this.root);
		return output;
	}
	
	public int getheight(){
		return height(this.root);
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
