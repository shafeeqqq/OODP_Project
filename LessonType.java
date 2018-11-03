
public enum LessonType {
	TYPE_A (1,0,0),
	TYPE_B(1,2,0),
	TYPE_C(1,2,2);
	
	private final int lectureCount;
	private final int tutorialCount;
	private final int labCount;
	
	LessonType(int lectureCount, int tutorialCount, int labCount) {
		this.lectureCount = lectureCount;
		this.tutorialCount = tutorialCount;
		this.labCount = labCount;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public int getTutorialCount() {
		return tutorialCount;
	}

	public int getLabCount() {
		return labCount;
	}
	

}
