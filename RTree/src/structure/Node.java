package structure;

import java.util.ArrayList;

public class Node {
	
	public Rectangle I;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isLeaf = true;
	
	public Node(){
	}
	
	public Node(boolean isLeaf){
		this.isLeaf=false;
	}
	
	
}
