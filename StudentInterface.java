import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;


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
		System.out.println("found student");
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
		ArrayList<String> courseNameList = new ArrayList<>();
		printCourseRegMenu();
		int menuChoice = getChoice();
		int courseChoice = -1;
		
		switch (menuChoice) {
		case 1:
			registerCourseSameFaculty();
			break;	
		case 2:
			unregisterForCourse();
		case 3:
			courseNameList = currentFaculty.getCourseNameList(currentSemester, false);
			
			printArray(courseNameList);
			courseChoice = getChoice();
			unregisterForCourse();	
		}
		
	}
	
	
	private void registerCourseSameFaculty() {
		String courseCode = chooseCourse();
		String tutorialGroup = "N.A";
		if (currentFaculty.getCourseVacancy(currentSemester, courseCode) > 0) {
			tutorialGroup = chooseTutorialGroup(courseCode);
			currentFaculty.registerForCourse(currentSemester, courseCode, matricNo, tutorialGroup);
		
		} else 
			System.out.println("There is no more vacancy in the course.\n");
	}


	private String chooseTutorialGroup(String courseCode) {
		String tutGroup = "";
		ArrayList<String> tutGroupList = currentFaculty.getTutorialGroupsVacancy(currentSemester, courseCode);
		if (tutGroupList == null)
			return "N.A.";
		
		else {
			System.out.println("### AVAILABLE TUTORIAL GROUPS ###");
			printArray(tutGroupList);
			System.out.println("ENTER CHOICE: ");
			int choice = getChoice() - 1 ;
			tutGroup = processStringSpace(tutGroupList.get(choice));
		}
		return tutGroup;
	}


	private String processStringSpace(String string) {
		int index = string.indexOf(' ');
		return string.substring(0, index);
	}


	private String chooseCourse() {
		ArrayList<String> courseNameList = currentFaculty.getCourseNameList(currentSemester, true);
		
		System.out.println("Choose Course:");
		printArray(courseNameList);
		String courseCode = processString(courseNameList.get(getChoice() - 1));	
		return courseCode;
	}


	private String processString(String string) {
		int index = string.indexOf('\t');
		return string.substring(0, index);
		
	}

			
	/**
	 * this method unregister student from the course of code given
	 */
	private void unregisterForCourse() {
//		StudentInfo temp = new StudentInfo();
//		for (StudentInfo studInfo : currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getStudentInfoList()) {
//			if (studInfo.getMatricNo() == currentStudent.getMatricNo()) {
//				temp = studInfo;
//			}
//		}
//		if (temp != null) {
//			currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getStudentInfoList().remove(temp);
//			currentStudent.getCandidature().get(university.getCurrentSemester()).remove("courseCode");
//		}
//		else 
//			System.out.println("You are not in the course. Please check again!");
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
	public void getTranscript() {
		String transcript = currentFaculty.getTranscript(currentStudent);
		System.out.print(transcript);
	}
	
	
	/**
	 * this method get the user input
	 * @return
	 */
	private int getChoice() {
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
}
