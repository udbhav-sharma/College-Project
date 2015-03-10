import java.util.Scanner;

public class Main {
	public static void main(String args[]){
		Scanner in = new Scanner("res/nvd");
		
		while(in.hasNext()){
			in.nextDouble();
		}
		
		in.close();
	}
}
