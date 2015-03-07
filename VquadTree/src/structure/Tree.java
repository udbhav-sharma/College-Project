package structure;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
		
		if(split){
			double newX = r.getX();
			double newY = r.getY();
			double newW = r.getWidth();
			double newH = r.getHeight();
			
			Rectangle newRectangle = new Rectangle();
			
			newRectangle.setRect(newX,newY,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
			
			newRectangle.setRect(newX+newW/2,newY,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
			
			newRectangle.setRect(newX,newY-newW/2,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
			
			newRectangle.setRect(newX+newW/2,newY-newW/2,newW/2,newH/2);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
		}
		else{
			newNode.setLeaf(true);
			if(points.size()>0)
				newNode.poi=lastPOI;
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
		
		int h=getheight();
		for(int i=h;i>=0;i--){
			printTree(this.root, h, i, output);
			output+="\n";
		}
		
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
	
	private void printTree(Node node, int h, int l, String output){
		if(h==l){
			if(node.isLeaf){
				output+="Rect("+node.r.getMinX()+","+node.r.getMinY()+", "+node.r.getMaxX()+","+node.r.getMaxY()+")";
			}
			else{
				output+="Point("+node.r.getX()+","+node.r.getY()+")";
			}
		}
		else
			output+="                 ";
	}
}
