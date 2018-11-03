public class Component {
	private String title;
	private int weightage;
	public Component(String componentTitle,int componentWeightage) {
		title=componentTitle;
		weightage=componentWeightage;
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
}
