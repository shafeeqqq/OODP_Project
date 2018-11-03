import java.util.ArrayList;
import java.util.Scanner;

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
	
	
	public StudentInterface(Faculty currentFaculty, String matricNo, University university) {
		this.university = university;
		this.currentFaculty = currentFaculty;
		this.matricNo = matricNo;
		this.currentStudent = currentFaculty.getStudent(matricNo);
	}

	public void run() {
		int run = 1;
		while (run == 1) {
			printMenu();
			int choice = getChoice();
			switch (choice) {
			case 1:
				courseReg();
				break;
				
			case 2:
				getTranscript();
				break;
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	
	private void courseReg() {
		ArrayList<String> courseNameList = new ArrayList<>();
		printCourseRegMenu();
		int menuChoice = getChoice();
		int courseChoice = -1;
		
		
		switch (menuChoice) {
		case 1:
			Semester currentSemester = university.getCurrentSemester();
			if (currentSemester != null)
				courseNameList = currentFaculty.getCourseNameList(currentSemester);
			else
				System.out.println("Error getting courselist");
			
			printArray(courseNameList);	// TODO: print vacancy too
			courseChoice = getChoice();
			registerForCourse(courseNameList.get(courseChoice));
			break;
			
		case 2:
			//TODO: display faculty list
			
		}
		
	}

	private void registerForCourse(String string) {
		System.out.println("implement register course method" + string);
		//TODO: implement register course method
		
	}

	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i + ") " + list.get(i));
		
	}

	private void printMenu() {
		System.out.print(
				  "1) Course registration\n"
				+ "2) Print Transcript"); 
		
	}
	
	private void printCourseRegMenu() {
		System.out.print(
				  "1) Courses from my Faculty\n"
				+ "2) Courses from other Faculty" ); 
	}

	public void getTranscript() {
		String transcript = currentFaculty.getTranscript(currentStudent);
		System.out.print(transcript);
	}
	
	private int getChoice() {
		int choice = sc.nextInt();
		return choice;
	}
}
