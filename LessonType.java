/**
 * Possible lesson types of a course
 *
 */
public enum LessonType {
	/**
	 * TYPE_A (one lecture) 
	 */
	TYPE_A (1,0,0),
	/**
	 * TYPE_B (one lecture and two tutorial groups)
	 */
	TYPE_B(1,2,0),
	/**
	 * TYPE_C (one lecture and two tutorial groups and 2 lab groups)
	 */
	TYPE_C(1,2,2);
	/**
	 * attribute to store the number of lecture groups in a course
	 */
	private final int lectureCount;
	/**
	 * attribute to store the number of tutorial groups in a course
	 */
	private final int tutorialCount;
	/** 
	 * attribute to store the number of lab groups in a course
	 */
	private final int labCount;
	
	/**
	 * constructor for the LessonType enum
	 * @param lectureCount		int number of lecture groups
	 * @param tutorialCount		int number of tutorial groups
	 * @param labCount			int number of lab groups
	 */
	LessonType(int lectureCount, int tutorialCount, int labCount) {
		this.lectureCount = lectureCount;
		this.tutorialCount = tutorialCount;
		this.labCount = labCount;
	}

	/**
	 * get method to retrieve the number of lecture groups of an enum type
	 * @return	number of lecture groups
	 */
	public int getLectureCount() {
		return lectureCount;
	}

	/**
	 * get method to retrieve the number of tutorial groups of an enum type
	 * @return	number of tutorial groups
	 */
	public int getTutorialCount() {
		return tutorialCount;
	}

	/**
	 * get method to retrieve the number of lab groups of an enum type
	 * @return	number of lab groups
	 */
	public int getLabCount() {
		return labCount;
	}

	/**
	 * method to print the number of lecture,tutorials and labs in an enum type
	 * method directly prints within itself
	 */
	public static void printLessonTypes() {
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
