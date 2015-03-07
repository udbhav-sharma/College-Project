package structure;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Point;

public class Node {
	public Rectangle r=null;
	public Point poi=null;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isLeaf = false;
	
	public Node(){
	}
	
	public void setR(Rectangle r){
		this.r = r;
	}
	
	public void setLeaf(boolean isLeaf){
		this.isLeaf=isLeaf;
	}
}
