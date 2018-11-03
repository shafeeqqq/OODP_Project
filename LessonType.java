
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

	public static void printLessonTypes() {
		// TODO Auto-generated method stub

		int i = 1;
		for (LessonType type: LessonType.values()) {
			System.out.println(i++ + ") " + type.toString());
			System.out.println("Lectures: " + type.getLectureCount());
			System.out.println("Tutorials: " + type.getTutorialCount());
			System.out.println("Labs: " + type.getLabCount());
			System.out.println();
		}
	}


}
