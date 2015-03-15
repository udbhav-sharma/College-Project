package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainTest {
	public static void main(String args[]) throws IOException{
		int i=6;
		int j=8;
		int w=1;
		PrintWriter pw = new PrintWriter(new File("res/roads2"));
		
		for(int m=1;m<=i;m++)
			for(int n=1;n<=j;n++){
				if(m-1>0)
					pw.println(m+" "+n+" "+(m-1)+" "+n+" "+w);
				if(m+1<=i)
					pw.println(m+" "+n+" "+(m+1)+" "+n+" "+w);
				if(n-1>0)
					pw.println(m+" "+n+" "+m+" "+(n-1)+" "+w);
				if(n+1<=j)
					pw.println(m+" "+n+" "+m+" "+(n+1)+" "+w);
			}
		
		pw.close();
	}
}
