import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class StudentInterface {
	
	Scanner sc = new Scanner(System.in);
	/*
	 * register for course
	 * print transcript
	 * 
	 */
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
		this.currentStudent = currentFaculty.getStudent(matricNo);
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
		
			if (currentSemester != null)
				courseNameList = currentFaculty.getCourseNameList(currentSemester);
			else
				System.out.println("Error getting courselist");
			
			printArray(courseNameList);	// TODO: print vacancy too
			currentFaculty.registerForCourse(courseNameList.get(getChoice() -1), currentSemester, currentStudent);
			break;
			
		case 2:
			//TODO: display faculty list
		case 3:
			currentSemester = university.getCurrentSemester();
			if (currentSemester != null)
				courseNameList = currentFaculty.getCourseNameList(currentSemester);
			else
				System.out.println("Error getting courselist");
			
			printArray(courseNameList);
			courseChoice = getChoice();
			unregisterForCourse(courseNameList.get(courseChoice-1));	
		}
		
	}
	

		
	
	/**
	 * this method unregister student from the course of code given
	 * @param courseCode
	 */
	private void unregisterForCourse(String courseCode) {
		StudentInfo temp = new StudentInfo();
		for (StudentInfo studInfo : currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getStudentInfoList()) {
			if (studInfo.getMatricNo() == currentStudent.getMatricNo()) {
				temp = studInfo;
			}
		}
		if (temp != null) {
			currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getStudentInfoList().remove(temp);
			currentStudent.getCandidature().get(university.getCurrentSemester()).remove("courseCode");
		}
		else 
			System.out.println("You are not in the course. Please check again!");
	}
	
	
	/**
	 * this method prints all the available course of the student
	 * @param list
	 */
	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println((i+1) + ") " + list.get(i));
		
	}
	
	
	/**
	 * this method print the menu for the student interface
	 */
	private void printMenu() {
		System.out.print(
				  "### Student Menu ###\n"
				  + " 1. Course registration/deregistration\n"
				  + " 2. Print Transcript\n"
				  + " 3. Quit"); 
		
	}
	
	
	/**
	 * this method print the menu for the available course registration and unregistration
	 */
	private void printCourseRegMenu() {
		System.out.print(		
				  "### Course Registration ###\n"
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
