import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	/**
	 * CourseCode of this course, it uniquely identify the course object
	 */
	private String courseCode;
	/**
	 * The name of the faculty that this course belongs to
	 */
	private String facultyName;
	/**
	 * The name of the the course
	 */
	private String courseName;
	/**
	 * The name of the coordinator of the course
	 */
	private String coordinator;
	/**
	 * The number of maximum students that can enroll in the course
	 */
	private int maxEnrollment;
	/**
	 * This arraylist stores an arraylist of studentinfo objects which contains marks of each student
	 */
	private ArrayList<StudentInfo> studentInfoList  = new ArrayList<>();
	/**
	 * This hashmap maps the max intger capacity to the name of the tutorial group
	 */
	private HashMap<String, Integer> tutorialGroups = new HashMap<>();
	/**
	 * This arraylist contains all the components of the assessment of the course
	 */
	private ArrayList<Component> assessment  = new ArrayList<>();
	/**
	 * This lessontype object stores the number of tutorial groups, labs groups with enum
	 */
	private LessonType lessonType;
	
	/**
	 * this method construct a course object with the given parameters, anything else is initially 0 or NULL
	 * This method is used in the run-time of the application
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
//		printDetails();
	}
	
	/**
	 * This method construct a course object with the given parameters
	 * This method is used in the reading of data from the text file
	 * @param facultyName
	 * @param courseCode
	 * @param courseName
	 * @param coordinator
	 * @param lessonType
	 * @param assessment
	 * @param studentInfoList
	 */
	Course(String facultyName, String courseCode, String courseName, String coordinator,
			LessonType lessonType, ArrayList<Component> assessment, ArrayList<StudentInfo> studentInfoList) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.coordinator = coordinator;
		this.lessonType = lessonType;
		this.assessment.addAll(assessment);
		initialiseTutorialGroups();
		this.maxEnrollment = initialiseMaxEnrollment();
		this.facultyName = facultyName;
		this.studentInfoList = studentInfoList;
	}

	/**
	 * This method sets the max enrollemnt based on the type of lesson
	 * @return a integer value 
	 */
	private int initialiseMaxEnrollment() {
		int num = 0;
		if (tutorialGroups == null) {
			return 20;
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
	public String getFacultyName() {
		return facultyName;
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
	 * This method changes all the assessment criteria to the input of the users
	 * @param assessment
	 */
	public void setAssessment(ArrayList<Component> assessment) { 
		boolean scratch = isAssessmentScratch(assessment, getComponentTitles());
		this.assessment.clear();
		this.assessment.addAll(assessment);
		if (scratch) {
			updateStudInfoAssessment();
		}
		
		System.out.println("Assessment Weightage Updated\n");
		System.out.println(getAssessmentString());
	}
	
	/**
	 * if the updated assessment has the same title as before, returns false, else returns true
	 * @param newer
	 * @param old
	 * @return
	 */
	private boolean isAssessmentScratch(ArrayList<Component> newer, ArrayList<String> old) { // TODO:cd
		
		ArrayList<String> updates = new ArrayList<>();
		
		for (Component item: newer)
			updates.add(item.getTitle());
		if (updates.size() != old.size())
			return true;
		
		for (String title: old) {
			if (!updates.contains(title))
				return true;
		}
		return false;
	}

	/**
	 * if it is a complete change of assessment, this method is called and changes all the
	 * components inside studentinfo list
	 */
	private void updateStudInfoAssessment() {	//TODO: cd
		for (StudentInfo studentInfo: studentInfoList)
			studentInfo.updateComponents(getComponentTitles());
	}


	/**
	 * this method returns the remaining spaces available
	 * @return
	 */
	public int getVacancy() {
		int count = studentInfoList.size();
		return (maxEnrollment - count);
	}
	
	/**
	 * This method returns a string that includes the courseCode, courseName
	 * overall and each tutorial group vacancy
	 * @return
	 */
	public String getVacancyMsg() {
		String msg = "";
		msg += this.courseCode + "\t" + this.courseName + "\n" ;
		
		msg += "Overall: " + (maxEnrollment - studentInfoList.size()) +"/"+ this.maxEnrollment + "\n";
		for (String key: tutorialGroups.keySet()) {
			msg += key + ": " + getTutorialGroupVacancy(key)  + "/" + tutorialGroups.get(key) +"\n";
		}
		return msg;
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
	
	/**
	 * This method return the assessment of the current course
	 * @return
	 */
	public ArrayList<Component> getAssessment() {
		return assessment;
	}

	/**
	 * @return assessmentTitles as an array list of strings
	 */
	public ArrayList<String> getAssessmentTitles() {
		if (assessment == null) return null;

		ArrayList<String> result = new ArrayList<>();
		for (Component item: assessment)
			result.add(item.getTitle());
		return result;
	}
	
	/**
	 * This method searches for the student info object by matric number
	 * @param matricNo
	 * @return StudentInfo object with the passed matricNo
	 */
	public StudentInfo getStudentInfoOfStudent(String matricNo) {
		StudentInfo result=null;
		for (StudentInfo item: studentInfoList) {
			if (matricNo.equals(item.getMatricNo()))
				return item;
		}
		return result;
	}
	
	/**
	 * This method returns all components titles as an arraylist of string
	 * @return all component tiltes as arraylist of string
	 */
	public ArrayList<String> getComponentTitles() {
		ArrayList<String> result = new ArrayList<>();
		
		for (Component item: assessment) 
			result.add(item.getTitle().toLowerCase());

		return result;	
	}
	/**
	 * This method returns all matricNo as arrayList of strings
	 * @return all matricNo as arraylist of strings
	 */
	public ArrayList<String> getMatricNoList() {
		ArrayList<String> result = new ArrayList<>();
		
		for (StudentInfo item: studentInfoList) 
			result.add(item.getMatricNo());
		
		return result;	
	}

	/**
	 * This method updates the mark for the students
	 * @param matricNo
	 * @param updatedMarks
	 */
	public void updateMarks(String matricNo, HashMap<String, Double> updatedMarks) {
		StudentInfo si = getStudentInfoOfStudent(matricNo);
		System.out.println(updatedMarks.toString());
		si.setMarks(updatedMarks);
		
	}
	
	/**
	 * This method return a hashmap of student list arranged by tutorial group
	 * @return arraylist of student mapped to their tutorial group
	 */
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

	/**
	 * This method returns all student lists as array of student matricNo
	 * @return all student matricNo
	 */
	public ArrayList<String> getAllStudentList() {
		ArrayList<String> result = new ArrayList<>();
		for (StudentInfo item: studentInfoList) {
			result.add(item.getMatricNo());
		}
		return result;
	}

	/**
	 * This method calculates all the course statistics and prints it
	 */
	public void printCourseStats() {
		System.out.println("Calculating...");
		int courseworkWeightage = getCourseworkWeightage();
		boolean setMarksComplete = true;
		ArrayList<Character> examMarksList = new ArrayList<>();
		ArrayList<Character> courseworkMarksList = new ArrayList<>();

		System.out.println(getComponentTitles().toString());
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
		System.out.println(marksListByComponent.keySet());
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
	/**
	 * This method returns an arraylist of all the grades of all the students
	 * @param marksListByComponent
	 * @return
	 */
	private ArrayList<Character> initialiseOverallMarksList(HashMap<String, ArrayList<Double>> marksListByComponent) {
		ArrayList<Character> overall = new ArrayList<>();
		
		for (int i=0; i<studentInfoList.size(); i++) {
			double total = 0;
			for (String title: getComponentTitles()) {
					Component component = getComponentByTitle(title);
					total += marksListByComponent.get(title).get(i) * component.getWeightage()/100.0;	
				}
			overall.add(getGrade(total, 100));
	
		}
		return overall;
	}

	/**
	 * This method returns an arraylist of all the grades of all the coursework of all students
	 * @param marksListByComponent
	 * @param courseworkWeightage
	 * @return
	 */
	private ArrayList<Character> initialiseCourseworkMarksList(
			HashMap<String, ArrayList<Double>> marksListByComponent, int courseworkWeightage) {
		
		ArrayList<Character> courseworkMarksList = new ArrayList<>();
		for (int i=0; i<studentInfoList.size(); i++) {
			double totalCoursework = 0;

			for (String title: getComponentTitles()) {
				if (title.equalsIgnoreCase("exam"))
					continue;
				else {
					Component component = getComponentByTitle(title);
					totalCoursework += marksListByComponent.get(title).get(i) * component.getWeightage()/100.0;
					courseworkMarksList .add(getGrade(totalCoursework, courseworkWeightage));	
				}
			}	
		}
		return courseworkMarksList;
	}


	/**
	 * This method returns an arraylist of all the exam grades
	 * @param marksListByComponent
	 * @return
	 */
	private ArrayList<Character> initialiseExamMarksList(HashMap<String, ArrayList<Double>> marksListByComponent) {
		ArrayList<Character> examMarksList = new ArrayList<>();

		int maxMarks = getComponentByTitle("exam").getMaxMarks();
		
		for (Double mark: marksListByComponent.get("exam"))	{
			examMarksList.add(getGrade(mark, maxMarks));
		}	

		return examMarksList;
	}


	/**
	 * This method checks whether a hashmap is empty
	 * @param hashmap
	 * @return true if corect, false if wrong
	 */
	private boolean checkIsEmpty(HashMap<String, ArrayList<Double>> hashmap) {
		for (String key: hashmap.keySet()) {
			if (hashmap.get(key).isEmpty())
				return true;
		}
		return false;
	}

	/**
	 * This method prints the percentages for each grade
	 * @param array
	 * @param msg
	 */
	private void printComponentStats(ArrayList<Character> array, String msg) {
		DecimalFormat df = new DecimalFormat("#.0");
		System.out.println(msg);
		HashMap<Character, Integer> temp = new HashMap<>();
		for (Character grade: array) {
			if (!temp.containsKey(grade)) 
				temp.put(grade, 0);
			temp.put(grade, temp.get(grade) + 1) ;
		}		
		
		for (Character grade: temp.keySet()) {
			
			double cent = 100.0*temp.get(grade)/array.size();
			System.out.print(grade +": " + df.format(cent) + "% \t");
		}
		System.out.println("");
	}

	/**
	 * This method returns the weightage of the course work after minusing the exam components
	 * @return
	 */
	private int getCourseworkWeightage() {
		int weightage = 0;
		for (Component item: assessment) {
			if (!item.getTitle().equalsIgnoreCase("exam"))
				weightage += item.getWeightage();
		}
		return weightage;
	}

	/**
	 * This method returns the component object by title
	 * @param title
	 * @return component object by title
	 */
	private Component getComponentByTitle(String title) {
		for (Component component: assessment) {
			if (component.getTitle().equalsIgnoreCase(title))
				return component;	
		}
		return null;
	}

	/**
	 * This method converts a double into a respective grade 
	 * @param mark
	 * @param maxMarks
	 * @return grade in character
	 */
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
	
		return grade;
	}

	/**
	 * This method adds a new student to the studentInfoList
	 * @param matricNo
	 * @param tutorialGroup
	 */
	public void addStudent(String matricNo, String tutorialGroup) {
		StudentInfo newStudent = new StudentInfo(matricNo, tutorialGroup, getAssessmentTitles());
		studentInfoList.add(newStudent);
		
	}
	/**
	 * THis method returns the strings that will be printed for transcript
	 * @param matricNo
	 * @return transcript as a string
	 */
	public String getTranscriptMsg(String matricNo) {
		String error = "You are not registered for this course.";
		StudentInfo studentInfo = getStudentInfoOfStudent(matricNo);
		String result = "";
		
		boolean allSet = true;
		
		if (studentInfo != null) {	
			double total = 0;
			for (Component item: assessment) {
				result += item.getTitle() + " [" +item.getWeightage() + "] : " ;
				if (studentInfo.getMarksByComponent(item.getTitle()) == null) {
					result += "Pending\n";
					allSet = false;
				}
				else {
					result += studentInfo.getMarksByComponent(item.getTitle()) + "\n" ;
					total += studentInfo.getMarksByComponent(item.getTitle())  * item.getWeightage()/100.0;
				}
			}
			
			result += "Overall Grade: ";
			if (allSet)
				result += String.valueOf(getGrade(total, 100)) + "\n";
			else
				result += "Pending\n";
			
			return result;
		}
		return error;
	}

	/**
	 * This method removes the student from the course
	 * @param matricNo
	 * @return true if successful, false if not
	 */
	public boolean unregisterStudent(String matricNo) {
		StudentInfo studentInfo = getStudentInfoOfStudent(matricNo);
		boolean deleted = false;
		
		if (studentInfo == null)
			System.out.println("Student is registered for this course");
		else 
			deleted = studentInfoList.remove(studentInfo);
		
		return deleted;
	}
}
