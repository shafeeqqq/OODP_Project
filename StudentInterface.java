import java.util.ArrayList;
import java.util.Scanner;

public class StudentInterface {

	Scanner sc = new Scanner(System.in);

	private Faculty currentFaculty;
	private University university;
	private Student currentStudent;
	private String matricNo;
	Semester currentSemester;

	/**
	 * this method construct a student interface object for the main function
	 * @param currentFaculty
	 * @param matricNo
	 * @param university
	 */
	public StudentInterface(Faculty currentFaculty, String matricNo, University university) {
		this.university = university;
		this.currentFaculty = currentFaculty;
		this.matricNo = matricNo;
		this.currentStudent = currentFaculty.getStudentObj(matricNo);
		currentSemester = university.getCurrentSemester();
	}


	/**
	 * this method let the student register or unregister or print his transcript from the main function
	 */
	public void run() {
		int run = 1;
		while (run == 1) {
			printMenu();
			int choice = getChoice();

			switch (choice) {
			case 1:
				courseRegControl();
				break;
			case 2:
				getTranscript();
				break;
			case 3:
				run = 2;
				System.out.println("You are logged out!");
				break;
			default:
				System.out.println("Invalid input");
			}
		}
	}


	/**
	 * this method controls the registration and unregistration of the courses of the student from the run()
	 */
	private void courseRegControl() {

		printCourseRegMenu();
		int menuChoice = getChoice();


		switch (menuChoice) {
		case 1:
			registerCourseSameFaculty();
			break;	
		case 2:
			registerCourseDiffFaculty();
			break;
			
		case 3:
			unregisterForCourse();
			break;
		
		}
	}


	private void registerCourseDiffFaculty() {
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName);
		String tutorialGroup = "N.A";
		Faculty faculty = university.getFacultyByName(facultyName);
		
		if (currentStudent.alreadyRegistered(courseCode, currentSemester)) {
			System.out.println("You have already registered for this course.");
			return;
		}

		if (university.getCourseVacancy(facultyName, currentSemester, courseCode) > 0) {
			tutorialGroup = chooseTutorialGroup(facultyName, courseCode);
			faculty.registerForCourseDiffFaculty(currentSemester, courseCode, currentStudent, tutorialGroup);

		} else 
			System.out.println("There is no more vacancy in the course.\n");
	}


	private void registerCourseSameFaculty() {
		String courseCode = chooseCourse(currentFaculty.getFacultyName());
		String tutorialGroup = "N.A";

		if (currentStudent.alreadyRegistered(courseCode, currentSemester)) {
			System.out.println("You have already registered for this course.");
			return;
		}

		if (currentFaculty.getCourseVacancy(currentSemester, courseCode) > 0) {
			tutorialGroup = chooseTutorialGroup(currentFaculty.getFacultyName(), courseCode);
			currentFaculty.registerForCourse(currentSemester, courseCode, matricNo, tutorialGroup);

		} else 
			System.out.println("There is no more vacancy in the course.\n");
	}


	private String chooseTutorialGroup(String facultyName, String courseCode) {
		String tutGroup = "";
		Faculty faculty = university.getFacultyByName(facultyName);
		ArrayList<String> tutGroupList = faculty.getAvailTutGroups(currentSemester, courseCode);
		if (tutGroupList == null)
			return "N.A.";

		else {
			System.out.println("### AVAILABLE TUTORIAL GROUPS ###");
			printArray(tutGroupList);
			int choice = getChoice() - 1 ;
			tutGroup = processStringSpace(tutGroupList.get(choice));
		}
		System.out.println(tutGroup);
		return tutGroup;
	}


	private String processStringSpace(String string) {
		int index = string.indexOf(' ');
		return string.substring(0, index);
	}


	private String chooseCourse(String facultyName) {
		Faculty faculty = university.getFacultyByName(facultyName);
		ArrayList<String> courseNameList = faculty.getCourseNameList(currentSemester, true);

		System.out.println("Choose Course:");
		printArray(courseNameList);
		String courseCode = processString(courseNameList.get(getChoice() - 1));	
		return courseCode;
	}


	private String processString(String string) {
		int index = string.indexOf('\t');
		return string.substring(0, index);

	}
	
	private String chooseFaculty() {
		ArrayList<String> facultyNameList = university.getFacultyNameList();

		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = null;
		boolean error = true;
		do {
			try {
				facultyName = facultyNameList.get(getChoice() - 1);	
				error = false;
			}
			catch(IndexOutOfBoundsException IndexOutOfBounds) {
				System.out.println("You have entered a wrong input \n"
						+ "Please enter a number from the list: ");
			}
		} while(error);


		return facultyName;
	}


	/**
	 * this method unregister student from the course of code given
	 */
	private void unregisterForCourse() {
		ArrayList<String> registeredCourses = currentStudent.getRegisteredCoursesList(currentSemester);
		System.out.println("### UNREGISTER FOR COURSE ###");
		displayCourses(registeredCourses);

		int choice = getChoice();
		String courseCode = registeredCourses.get(choice-1);

		currentFaculty.unregisterCourse(matricNo, currentSemester, courseCode);
	}


	private void displayCourses(ArrayList<String> list) {
		System.out.println("eneter");
		for (int i=0; i<list.size(); i++) {
			String courseName = currentFaculty.getCourseName(currentSemester, list.get(i));
			System.out.println((i+1) + ". " + list.get(i) + "\t"+ courseName);
		}
	}


	/**
	 * this method prints all the available course of the student
	 * @param list
	 */
	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println((i+1) + ". " + list.get(i));

	}


	/**
	 * this method print the menu for the student interface
	 */
	private void printMenu() {
		System.out.print(
				"### STUDENT MENU ###\n"
						+ " 1. Course registration/deregistration\n"
						+ " 2. Print Transcript\n"
						+ " 3. Quit\n"); 

	}


	/**
	 * this method print the menu for the available course registration and unregistration
	 */
	private void printCourseRegMenu() {
		System.out.print(		
				"### COURSE REGISTRATION ###\n"
						+ " 1. Courses from my Faculty\n"
						+ " 2. Courses from other Faculty\n"
						+ " 3. Unregister from a course\n"); 
	}


	/**
	 * this method prints the transcript of the current logged in student
	 */
	private void getTranscript() {
		String transcript = university.getTranscript(currentStudent.getCandidature(),
				currentStudent.getStudentName(), matricNo, currentFaculty.getFacultyName());
		System.out.print(transcript);
	}


	/**
	 * this method get the user input
	 * @return
	 */
	private int getChoice() {
		System.out.println("Enter choice: ");
		boolean error = true;
		int choice = 0;
		do {//make sure it is the right input
			try {
				choice = sc.nextInt();
				error = false;
			}
			catch (Exception e) {
				System.out.println("You have entered a wrong input!");
				System.out.println("Please enter 1 or 2");
				sc.reset();
				sc.next();
			}			
		}while (error);
		sc.nextLine();
		return choice;
	}
}
