import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	
	private String courseCode;
	private String facultyName;
	private String courseName;
	private String coordinator;
	private int maxEnrollment;
	private ArrayList<StudentInfo> studentInfoList  = new ArrayList<>();
	private HashMap<String, Integer> tutorialGroups = new HashMap<>();
	private ArrayList<Component> assessment  = new ArrayList<>();
	private LessonType lessonType;
	
	/**
	 * this method construct a course object with the given parameters, anything else is initially 0 or NULL
	 * @param Code
	 * @param Name
	 * @param coordinator_staff
	 * @param group
	 * @param Type
	 */
	public Course(String facultyName, String courseCode, String courseName, String coordinator, LessonType Type, ArrayList<Component> assessment) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.coordinator = coordinator;
		this.lessonType = Type;
		this.assessment.addAll(assessment);
		initialiseTutorialGroups();
		this.maxEnrollment = initialiseMaxEnrollment();
		this.facultyName = facultyName;
	}
	
	
	Course(String facultyName2, String courseCode2, String courseName2, String coordinator2,
			LessonType lessonType2, ArrayList<Component> assessment2, ArrayList<StudentInfo> studentInfoList2) {
		this.courseCode = courseCode2;
		this.courseName = courseName2;
		this.coordinator = coordinator2;
		this.lessonType = lessonType2;
		this.assessment.addAll(assessment2);
		initialiseTutorialGroups();
		this.maxEnrollment = initialiseMaxEnrollment();
		this.facultyName = facultyName2;
		this.studentInfoList = studentInfoList2;
	}


	private int initialiseMaxEnrollment() {
		int num = 0;
		if (tutorialGroups == null) {
			return 10;
		}
		
		else {
			for (String key: tutorialGroups.keySet())
				num += tutorialGroups.get(key);
		}
		
		return num;
		
	}


	private void initialiseTutorialGroups() {
		int tutorialCount = lessonType.getTutorialCount();
		
		if (tutorialCount == 0)
			tutorialGroups = null;
		
		else {
			for (int i=0; i<tutorialCount; i++) 
				tutorialGroups.put("TUT" + (i+1), 10);
		}
	}
	
	
	public void printDetails() {
		System.out.print(
				"Course Code: " + courseCode + "\n"
			  + "Course Name: " + courseName + "\n"
			  + "Coordinator: " + coordinator + "\n"
			  + "Lesson Type: " + lessonType.toString() + "\n"
			  + "Max Enrollment: " + maxEnrollment + "\n"
			  + "Assessment: \n"  + getAssessmentString());
		
		if (tutorialGroups == null)
			System.out.println("Tutorial Groups: Not Applicable");
		
		else if (!tutorialGroups.isEmpty())
			System.out.println("Tutorial Groups: " + tutorialGroups.toString());
	}
	
	
	public String getAssessmentString() {
		String result = "";
		for (Component item: assessment)
			result += item.toString();
		return result;
	}
	
	
	public ArrayList<String> getAvailTutGroups() {
		ArrayList<String> result = new ArrayList<>();
		if (tutorialGroups == null)
			return null;
		
		else {
			for (String tutGroup: tutorialGroups.keySet()) {
				int vacancy = getTutorialGroupVacancy(tutGroup);
				if (vacancy != 0)
					result.add(tutGroup + " [" + vacancy + "]" );
			}
		}
		return result;
	}
	
	
	private int getTutorialGroupVacancy(String tutGroup) {
		int count = 0;
		for (StudentInfo item: studentInfoList) {
			if (item.getTutorialGroup().equals(tutGroup))
				++count;
		}
		return tutorialGroups.get(tutGroup) - count;
	}

	
	public void printStudentInfoOfStudent(String matricNo) {
		for (StudentInfo item: studentInfoList)
			if (item.getMatricNo().equals(matricNo)) {
				item.printMarksByComponent();
				break;
			}
	}


	/**This method returns the courseCode as a String
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	
	/**
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	
	
	/**
	 * @return maxEnrollment
	 */
	public int getMaxEnrollment() {
		return maxEnrollment;
	}
	

	
	public ArrayList<StudentInfo> getStudentInfoList() {
		return studentInfoList;
	}
	
	
	/**
	 * this method returns the coordinator
	 * @return
	 */
	public String getCoordinator() {
		return coordinator;
	}
	
	
	/**
	 * this method set the type of assessment
	 * @param assessment
	 */
	public void setAssessment(ArrayList<Component> assessment) {
		this.assessment.clear();
		this.assessment.addAll(assessment);
	}
	
	
	/**
	 * this method returns the remaining spaces available
	 * @return
	 */
	public int getVacancy() {
		System.out.println(studentInfoList.toString());
		int count = studentInfoList.size();
		return (maxEnrollment - count);
	}
	
	
	/**
	 * @return lessonType
	 */
	public LessonType getLessonType() {
		return lessonType;
	}
	
	
	/**
	 * this method set the type of lesson
	 * @param lessonType
	 */
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	

	public ArrayList<Component> getAssessment() {
		return assessment;
	}



	public ArrayList<String> getAssessmentTitles() {
		if (assessment == null) return null;

		ArrayList<String> result = new ArrayList<>();
		for (Component item: assessment)
			result.add(item.getTitle());
		
		return result;
	}



	public StudentInfo getStudentInfoOfStudent(String matricNo) {
		StudentInfo result=null;
		for (StudentInfo item: studentInfoList) {
			if (matricNo.equals(item.getMatricNo()))
				return item;
		}
		return result;
	}
	
	public ArrayList<String> getComponentTitles() {
		ArrayList<String> result = new ArrayList<>();
		
		for (Component item: assessment) 
			result.add(item.getTitle());

		return result;	
	}
	
	public ArrayList<String> getMatricNoList() {
		ArrayList<String> result = new ArrayList<>();
		
		for (StudentInfo item: studentInfoList) 
			result.add(item.getMatricNo());
		
		return result;	
	}



	public void updateMarks(String matricNo, HashMap<String, Double> updatedMarks) {
		StudentInfo si = getStudentInfoOfStudent(matricNo);
		System.out.println(updatedMarks.toString());
		si.setMarks(updatedMarks);
		
	}
	
	
	public HashMap<String, ArrayList<String>> getStudentListByGroup() {
		HashMap<String, ArrayList<String>> result = new HashMap<>();
		for (StudentInfo item: studentInfoList) {
			String tutorialGroup = item.getTutorialGroup();
			
			if (!result.containsKey(tutorialGroup)) 
				result.put(tutorialGroup, new ArrayList<String>());
			
			result.get(tutorialGroup).add(item.getMatricNo());			
		}
		
		return result;
	}


	public ArrayList<String> getAllStudentList() {
		ArrayList<String> result = new ArrayList<>();
		for (StudentInfo item: studentInfoList) {
			result.add(item.getMatricNo());
		}
		
		return result;
	}


	public void printCourseStats() {
		System.out.println("Calculating...");
		int courseworkWeightage = getCourseworkWeightage();
		boolean setMarksComplete = true;
		ArrayList<Character> examMarksList = new ArrayList<>();
		ArrayList<Character> courseworkMarksList = new ArrayList<>();

		
		// initialise dictionary with titles
		HashMap<String, ArrayList<Double>> marksListByComponent = new HashMap<>();
		for (String title: getComponentTitles())
			marksListByComponent.put(title, new ArrayList<Double>());
		
		// populate dictionary with data
		for (StudentInfo studentInfo : studentInfoList) {
			for (String title: getComponentTitles()) {
				if (studentInfo.getMarksByComponent(title) != null) {
					double componentMark = studentInfo.getMarksByComponent(title);
					marksListByComponent.get(title).add(componentMark);	
				}
				else
					setMarksComplete = false;
			}
		}
		
		if (checkIsEmpty(marksListByComponent)) {
			System.out.println("You have not set marks for the students");
			return;
		}
		
		if (marksListByComponent.containsKey("exam"))
			examMarksList = initialiseExamMarksList(marksListByComponent);
		
		if (courseworkWeightage != 0)
			courseworkMarksList = initialiseCourseworkMarksList(marksListByComponent, courseworkWeightage);
		
		ArrayList<Character> overall = initialiseOverallMarksList(marksListByComponent);	
		
		printComponentStats(examMarksList, "Exam stats");
		printComponentStats(courseworkMarksList, "Coursework stats");
		printComponentStats(overall, "Overall stats");
		
		if (setMarksComplete == false)
			System.out.println("Not all students' marks are set!");
		
	}

	private ArrayList<Character> initialiseOverallMarksList(HashMap<String, ArrayList<Double>> marksListByComponent) {
		ArrayList<Character> overall = new ArrayList<>();
		
		for (int i=0; i<studentInfoList.size(); i++) {
			double total = 0;
			System.out.println("Overall: ");
			for (String title: getComponentTitles()) {
					Component component = getComponentByTitle(title);
					total += marksListByComponent.get(title).get(i) * component.getWeightage()/100.0;	
				}
			overall.add(getGrade(total, 100));
	
		}
		return overall;
	}



	private ArrayList<Character> initialiseCourseworkMarksList(
			HashMap<String, ArrayList<Double>> marksListByComponent, int courseworkWeightage) {
		
		ArrayList<Character> courseworkMarksList = new ArrayList<>();
		for (int i=0; i<studentInfoList.size(); i++) {
			double totalCoursework = 0;

			for (String title: getComponentTitles()) {
				if (title.equalsIgnoreCase("exam"))
					continue;
				else {
					System.out.println("Coursework:");
					Component component = getComponentByTitle(title);
					System.out.println(marksListByComponent.toString());
					totalCoursework += marksListByComponent.get(title).get(i) * component.getWeightage()/100.0;
					courseworkMarksList .add(getGrade(totalCoursework, courseworkWeightage));	
				}
			}	
		}
		return courseworkMarksList;
	}



	private ArrayList<Character> initialiseExamMarksList(HashMap<String, ArrayList<Double>> marksListByComponent) {
		ArrayList<Character> examMarksList = new ArrayList<>();

		int maxMarks = getComponentByTitle("exam").getMaxMarks();
		
		for (Double mark: marksListByComponent.get("exam"))	{
			examMarksList.add(getGrade(mark, maxMarks ));
			System.out.println("exam");
		}		
		return examMarksList;
	}



	private boolean checkIsEmpty(HashMap<String, ArrayList<Double>> hashmap) {
		for (String key: hashmap.keySet()) {
			if (hashmap.get(key).isEmpty())
				return true;
		}
		return false;
	}


	private void printComponentStats(ArrayList<Character> array, String msg) {
		System.out.println(msg);
		HashMap<Character, Integer> temp = new HashMap<>();
		for (Character grade: array) {
			if (!temp.containsKey(grade)) 
				temp.put(grade, 0);
			temp.put(grade, temp.get(grade) + 1) ;
			}		
	}


	private int getCourseworkWeightage() {
		int weightage = 0;
		for (Component item: assessment) {
			if (!item.getTitle().equalsIgnoreCase("exam"))
				weightage += item.getWeightage();
		}
		return weightage;
	}


	private Component getComponentByTitle(String title) {
		for (Component component: assessment) {
			if (component.getTitle().equalsIgnoreCase(title))
				return component;	
		}
		return null;
	}


	private char getGrade(Double mark, int maxMarks) {
		char grade;
		
		if(mark >= 70*maxMarks/100) 
			grade = 'A';
		else if (mark >= 60*maxMarks/100)
			grade = 'B';
		else if (mark >= 50*maxMarks/100)
			grade = 'C';
		else
			grade = 'F';
	
		System.out.println(mark + " " + maxMarks + " " + grade);
		return grade;
	}


	public void addStudent(String matricNo, String tutorialGroup) {
		StudentInfo newStudent = new StudentInfo(matricNo, tutorialGroup, getAssessmentTitles());
		studentInfoList.add(newStudent);
		
	}
	
}
