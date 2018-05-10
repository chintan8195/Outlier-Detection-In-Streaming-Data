
public class DataPoint {
	
	int timestamp;
	int[] values;
	DataPoint(String dataString){
	//Assuming data contains timestamp and data values seperated in CSV format
		String[] s = dataString.split(",");
		timestamp = Integer.parseInt(s[0].trim());
		values = new int[s.length-1];
		for(int i = 1; i<s.length; i++)
		{
			values[i-1]=Integer.parseInt(s[i].trim());
		}
		
	}
	
	public String toString() {
		String repr = "";
		repr += "[" + timestamp + "]" + "<";
		for(int i = 0;i<values.length;i++) {
			if(i!=0)
				repr += ", ";
			repr += values[i];
		}
		repr += "> ";
		return repr;
	}
}
