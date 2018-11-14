/**
 Represents a component of a course.
 One course can one or multiple components.
 */
public class Component {
	
	/**
	 * Title of the Component
	 */
	private String title;
	
	/**
	 * Weightage of the Component
	 */
	private int weightage;
	
	/**
	 * The maximum marks a student can get from this Component. Default is 100. 											
	 */
	private int maxMarks;
	
	/**
	 * Creates a new Component.
	 * @param componentTitle This Component's title.
	 * @param componentWeightage This Component's weightage.
	 */
	public Component(String componentTitle, int componentWeightage) {
		this.title = componentTitle;
		this.weightage = componentWeightage;
		this.maxMarks = 100;
	}
	
	/**
	 * Gets the title of the Component.
	 * @return this Component's title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Changes the title of this Component.
	 * This may involve a lengthy legal process.
	 * @param componentTitle This Component's new name.
	 */
	public void setTitle(String componentTitle) {
		title=componentTitle;
	}
	
	/**
	 * Gets the weightage of the Component.
	 * @return this Component's weitghtage
	 */
	public int getWeightage() {
		return weightage;
	}
	
	/**
	 * Changes the weightage of this Component.
	 * This may involve a lengthy legal process.
	 * @param componentWeightage This Component's new weightage.
	 */
	public void setWeightage(int componentWeightage) {
		weightage=componentWeightage;
	}
	
	/**
	 * Convert the Component into string.
	 * @return a string that contains This Component's title and weightage
	 */
	public String toString() {
		return title + ": " + weightage + "%\n";
	}
	
	/**
	 * Gets the maximum marks of the Component.
	 * Default of the maximum marks is 100.
	 * @return this Component's maximum marks.
	 */
	public int getMaxMarks() {
		return maxMarks;
	}
	
}
