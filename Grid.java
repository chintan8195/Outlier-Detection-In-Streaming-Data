import java.util.ArrayList;

public class Grid {
	ArrayList<GridElement> gridElementList = new ArrayList<GridElement>();
	int densityThreshold;
	int binWidth;
	int dimension;
	int windowSize;
	boolean init = false;
	Grid(int windowSize){
		 this.windowSize= windowSize ;
	}
	public GridElement getGridElement(DataPoint dP) {
		if(!init) {
			int range = 65535;
			dimension = dP.values.length;
			if(dimension<1) {
				System.err.print("Dimension should be atleast 1");
				System.exit(0);
			}
			int no_of_grid = (int)Math.floor((float)Math.pow(windowSize, 1.0 / (dimension+1)));; //Number of maximum grid possible in one dimension
			binWidth = (int)Math.ceil((float)range/no_of_grid);
			densityThreshold = (int)Math.ceil((float)Math.log(no_of_grid));
			System.out.println("Dimension "+"->"+dimension + "  binWidth"+ "->"+ binWidth +"  densityThreshold"+ "->" +densityThreshold);
			init = true;
		}
		
		// Assume GE will hold the grid element with null
		// iterate through each grid element in the arraylist and check if grid element contains data point
		// if it contains, assign GE = that grid element , increase density/no of dp break out of the loop
		GridElement GE=null;
		for(GridElement temp : gridElementList) {
			if(temp.contains(dP)) {
				GE = temp;
				GE.countDataPoints ++;
				GE.density ++;
				break;
			}
		}
		
		// if GE is still null, this means we didn't find any existing grid element
		// assign GE = create new grid element with dp and bin width
		if(GE==null) {
			GE = new GridElement(dP,binWidth);
			GE.neighbors.addAll(findNeighbors(GE));
			for(GridElement neighborElement : GE.neighbors) {
				neighborElement.neighbors.add(GE);
			}
			gridElementList.add(GE);
		}
		
		// return Grid Element
		return GE;
	}
	//If the datapoint goes out the windowsize we will remove that point and decrease the density of that grid
	public void reduceDensity(DataPoint dP) {
		GridElement c = null;
		for(GridElement temp : gridElementList) {
			if(temp.contains(dP)) {
				temp.density--;
				temp.countDataPoints--;
				c = temp;
				break;		
			}	
			
		}
		//if density of grid becomes less than 0 than remove that grid from list.
		if(c!= null && c.density==0) {
			gridElementList.remove(c);
		}
	}
	
	//Finding Neighbors
	public ArrayList<GridElement> findNeighbors(GridElement GE) {
		ArrayList<GridElement> neighborList = new ArrayList<GridElement>();	
		for(GridElement temp : gridElementList) {
			if(GE.isNeighbor(temp)) {
				neighborList.add(temp);
			}
		}
		return neighborList;
	}
	
	//Finding if the datapoint is an Outlier or not
	public boolean isOutlier(GridElement GE) {
		if(GE.density>=densityThreshold) {
			return false;
		}
		for(GridElement temp: GE.neighbors) {
			if(temp.density>=densityThreshold){
				return false;
			}
		}
		return true;
	}
	
}
