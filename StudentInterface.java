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
				courseRegControl();
				break;
				
			case 2:
				getTranscript();
				break;
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	
	private void courseRegControl() {
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
		case 3:
			currentSemester = university.getCurrentSemester();
			if (currentSemester != null)
				courseNameList = currentFaculty.getCourseNameList(currentSemester);
			else
				System.out.println("Error getting courselist");
			
			printArray(courseNameList);
			courseChoice = getChoice();
			deregisterForCourse(courseNameList.get(courseChoice));	
		}
		
	}

	private void registerForCourse(String courseCode) {
		if (currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getVacancy() > 0) {
			currentStudent.getCandidature().get(university.getCurrentSemester()).add(courseCode);
			Random rand = new Random();
			int i = rand.nextInt(2);
			StudentInfo newStudentInfo;
			if(i == 1) {
				newStudentInfo = new StudentInfo(currentStudent.getMatricNo(), "tutorialGroup 1");
			}
			else {
				newStudentInfo = new StudentInfo(currentStudent.getMatricNo(), "tutorialGroup 2");					
			}
			currentFaculty.getCourse(university.getCurrentSemester(), courseCode).getStudentInfoList().add(newStudentInfo);
		}
		else {
			System.out.println("There are no more vacancy in the course, please specify another course.");
		}
	}
	
	private void deregisterForCourse(String courseCode) {
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
				+ "2) Courses from other Faculty\n"
				+ "3) Unregister from a course."); 
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
