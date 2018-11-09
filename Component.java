
public class Component {
	
	private String title;
	private int weightage;
	private int maxMarks;
	
	
	public Component(String componentTitle, int componentWeightage) {
		this.title = componentTitle;
		this.weightage = componentWeightage;
		this.maxMarks = 100;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String componentTitle) {
		title=componentTitle;
	}
	
	
	public int getWeightage() {
		return weightage;
	}
	
	
	public void setWeightage(int componentWeightage) {
		weightage=componentWeightage;
	}
	

	public String toString() {
		return title + ": " + weightage + "%\n";
	}
	
	public int getMaxMarks() {
		return maxMarks;
	}
	
	
}
