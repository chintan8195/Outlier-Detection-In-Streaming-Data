import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class P2 {
	public String host;
	public int port;
	int windowSize;
	int countO = 0;
	ArrayList<DataPoint> window =  new ArrayList<DataPoint>();
	public void run(){
		try {
			String s;
			Socket socket = new Socket(host,port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			
			Grid grid = new Grid(windowSize); //Maintain list of grid Element such as merging, removing, adding and neighbors
			int count = 0;
			while((s = in.readLine())!=null)
			{	
				DataPoint dp = new DataPoint(s);
				count ++;
				
				//Maintaining Window
				window.add(dp);
				if(window.size()>windowSize) {
					grid.reduceDensity(window.get(0));
					window.remove(0);
				}
				
				// Identify grid element for this dataPoint
				GridElement current = grid.getGridElement(dp); //Either find the grid element from the list or make a new one
				// Outlier Logic
				// If current density is less than tau
				//Outlier detection
				if(count <= windowSize) continue;
				if(grid.isOutlier(current)) {
					System.out.println(dp + " is outlier");
					countO++;
				} 
			}socket.close();
			if(countO==0){
				System.out.println("there are no outliers.");
			}	
		}
		catch(Exception e)
		{
			System.out.println("Unable to connect \n");
			e.printStackTrace();
			
		}
	}
	
	public static void main(String args[]) {
		P2 instance = new P2();
		Scanner in = new Scanner(System.in);
		instance.windowSize = in.nextInt() ;
		if(instance.windowSize<1) {
			System.err.print("Window Size should be atleast 1");
			System.exit(0);
		}
		in.nextLine();
		String line = in.nextLine(); 
		instance.host = line.split(":")[0];
		instance.port = Integer.parseInt(line.split(":")[1]); //Todo : read port from user
		instance.run();
		in.close();
	}
}
