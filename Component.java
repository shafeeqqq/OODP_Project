
public class Component {
	
	private String title;
	private int weightage;
	
	
	public Component(String componentTitle, int componentWeightage) {
		this.title = componentTitle;
		this.weightage = componentWeightage;
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
}
