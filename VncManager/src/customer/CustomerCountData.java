package customer;

import java.util.ArrayList;

public class CustomerCountData {
	private ArrayList<String> countNames = new ArrayList<>();
	private ArrayList<Integer> counts = new ArrayList<>();
	
	
	
	public ArrayList<String> getCountName() {
		return countNames;
	}
	public void setCountName(ArrayList<String> countNames) {
		this.countNames = countNames;
	}
	public ArrayList<Integer> getCount() {
		return counts;
	}
	public void setCount(ArrayList<Integer> counts) {
		this.counts = counts;
	}
	
	
}
