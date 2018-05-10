import java.util.ArrayList;

public class GridElement {
	static int count = 0;
	int gridNo;
	int countDataPoints;
	int density;
	int[] lowerlimit;
	int[] upperlimit;
	ArrayList<GridElement> neighbors;
	GridElement(DataPoint dP,int P){
		count ++;
		gridNo = count;
		countDataPoints=1;
		density =1;
		lowerlimit = new int[dP.values.length];
		upperlimit = new int[dP.values.length];
		neighbors = new ArrayList<GridElement>();
		for(int i = 0; i<dP.values.length ; i++) {
			lowerlimit[i] = (int)Math.floor((float)dP.values[i] / P) * P ;
			upperlimit[i] = lowerlimit[i] + P;
		}	
	}
	
	public Boolean contains(DataPoint dp) {
		Boolean isContain = true;
		for(int d = 0; d<dp.values.length; d++) {
			if(lowerlimit[d] <= dp.values[d] && dp.values[d] < upperlimit[d]) {
				continue;
			} else {
				isContain = false;
				break;
			}
		}
		return isContain;
	}
	
	public boolean isNeighbor(GridElement target) {
		for(int d = 0; d < lowerlimit.length; d++) {
			if(Math.abs(lowerlimit[d] - target.lowerlimit[d]) > (upperlimit[d] - lowerlimit[d] )) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		String repr = gridNo + "[";
		repr += countDataPoints + ",";
		for(int d=0;d<lowerlimit.length;d++) {
			if(d != 0) {
				repr += ", ";
			}
			repr += "(" +lowerlimit[d] +":" + upperlimit[d] +")";
		}
		repr += "]<";
		for(GridElement ge : neighbors)  {
			repr += ge.gridNo + ",";
		}
		repr += ">";
		return repr;
	}
}