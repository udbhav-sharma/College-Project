import gnu.trove.TIntProcedure;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.infomatiq.jsi.rtree.RTree;


public class Main {
	public static void main(String args[]){
		new Main().run();
	}
	
	public void run(){
	    int rowCount = 3;
	    int columnCount = 3;
	    int count = rowCount * columnCount;
		
		SpatialIndex si = new RTree();
		si.init(null);
		
		final Rectangle[] rects = new Rectangle[100];
		int id = 0;
	    for (int row = 0; row < rowCount; row++)
	      for (int column = 0; column < columnCount; column++) {
	        rects[id++] = new Rectangle(row, column, row, column); // 
	    }
	    
	    for (id=0; id < count; id++) {
	        si.add(rects[id], id);
	    }
		
		final Point p = new Point(1.5f,2.5f);
		si.nearest( p, new TIntProcedure() {	
			@Override
			public boolean execute(int i) {
				System.out.println("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p));
			    return true; 
			}
		},3);
	}
}
