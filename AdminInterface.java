import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminInterface {

	/*
	 * TODO:
	 * check vacancy in course
	 * print student list by tutorial group; lec - everyone
	 * enter course assessment weightage
	 * enter coursework marks --
	 * print course stats --
	 *
	 */

	Scanner sc = new Scanner(System.in);
	private University university;


	public AdminInterface(University university) {
		this.university = university;
	}


	public void run() {
		int run = 1;
		while (run == 1) {
			printMenu();
			int choice = getChoice();

			switch (choice) {
			case 1:
				addFaculty();
				break;

			case 2:
				addCourse();
				break;

			case 3:
				addFacultyStaff();
				break;

			case 4:
				addStudent();
				break;

			case 5:
				printCourseStats();
				break;

			case 6:
				setMarks();
				break;

			case 7:
				editCourseWeightage();
				break;

			case 8:
				printStudentListByGroup();
				break;

			case 9:
				checkCourseVacancy();
				break;

			case 10:
				run = 0;
				System.out.println("You are logged out of the system!\n");
				break;

			default:
				System.out.println("You have entered a wrong input! \n"
						+ "Please enter a number from 1 to 10!");
				System.out.println();
			}
		}
	}


	private void printMenu() {
		System.out.print(
				"### ADMIN MENU ###\n"
						+ " 1. Add Faculty\n"
						+ " 2. Add Course\n"
						+ " 3. Add Faculty Staff\n"
						+ " 4. Add Student\n"
						+ " 5. Print Course Statistics\n"
						+ " 6. Set Marks\n"
						+ " 7. Edit Course Assessment\n"
						+ " 8. Print student list by group\n"
						+ " 9. Check Course Vacancy\n"
						+ " 10. Quit\n"
						+ "~~~~~~~~~~~~~~~~~~\n"); 

	}


	private void addFaculty() {
		String facultyName = getStringInput("Enter Faculty Name:");
		university.addFaculty(facultyName);

		// TODO: success message
	}


	private void addCourse() {
		String facultyName = chooseFaculty();
		String courseCode = getStringInput("Enter Course Code:");
		String courseName = getStringInput("Enter Course Name:");
		String staffID = chooseCoordinator(facultyName);
		Semester sem = university.getCurrentSemester();

		LessonType lessonType = chooseLessonType();	
		ArrayList<Component> assessment = getAssessmentInput();

		university.addCourseToFaculty(facultyName, courseCode, courseName, 
				staffID, lessonType, assessment, university.getCurrentSemester());
		//TODO: success message
	}




	private void addFacultyStaff() {
		String staffName = getStringInput("Enter Staff Name:");
		String facultyName = chooseFaculty();

		university.addStaffToFaculty(facultyName, staffName);

		//TODO: success message	
	}


	private void addStudent() {
		String studentName = getStringInput("Enter Student Name:");
		String facultyName = chooseFaculty();

		university.addStudentToFaculty(facultyName, studentName, university.getCurrentSemester());

		//TODO: success message
	}


	private void editCourseWeightage() {
		Semester sem = university.getCurrentSemester();	// TODO other semester? 
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);

		ArrayList<Component> assessment  = university.getCourseAssessment(facultyName, sem, courseCode);		
		assessment = executeEditCourseAssessment(assessment);

		university.updateCourseAssessment(facultyName, sem, courseCode, assessment);
		//TODO: success message
	}


	private ArrayList<Component> executeEditCourseAssessment(ArrayList<Component> assessment) {
		printAssessment(assessment);
		printEditAssessmentOptions();
		int choice = getChoice();

		switch (choice) {
		case 1:
			assessment = updateAssessment(assessment);
			break;

		case 2:
			assessment = getAssessmentInput();
			break;

		default:
			System.out.print("Invalid input. "); 
		}
		return assessment;
	}


	private void checkCourseVacancy() {
		Semester sem = university.getCurrentSemester();	// TODO other semester? 

		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);

		int vacancy = university.getCourseVacancy(facultyName, sem,  processString(courseCode));
		System.out.println(courseCode);
		System.out.println("Vacancy: " + vacancy);

	}


	private void printStudentListByGroup() {
		Semester sem = university.getCurrentSemester();	// TODO other semester? 
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);
		char type = getStringInput("Print student list by lecture group(L) or tutorial group(T)?").charAt(0);

		university.printStudentListByGroup(facultyName, sem,  processString(courseCode), type);

	}


	private void printCourseStats() {
		Semester sem = university.getCurrentSemester();	// TODO other semester? 

		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);

		university.printCourseStats(facultyName, sem,  processString(courseCode));
	}


	private void setMarks() {
		Semester sem = university.getCurrentSemester();

		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);

		ArrayList<String> components = university.getComponentTitles(facultyName, sem,  processString(courseCode));
		HashMap<String, Double> updatedMarks = new HashMap<>();

		String matricNo = getMatricNoInput(university.getMatricNoList(facultyName, sem,  processString(courseCode)));

		for (String item: components) {
			System.out.println("Enter marks for " + item + ": ");
			Double mark = sc.nextDouble();
			updatedMarks.put(item, mark);
		}
		university.updateMarks(facultyName, sem,  processString(courseCode), matricNo, updatedMarks);
	}


	private String chooseCoordinator(String facultyName) {
		ArrayList<String> staffNameList = university.getAvailableStaff(facultyName);
		System.out.println("Choose Course Coordinator:");
		printArray(staffNameList);
		String staffID = null;
		boolean error = true;
		do {
			try {
				staffID = processString(staffNameList.get(getChoice() - 1));
				error = false;
			}
			catch(IndexOutOfBoundsException IndexOutOfBounds) {
				System.out.println("You have entered a wrong input \n"
						+ "Please enter a number from the list: ");
			}
		} while(error);




		return staffID;
	}


	private ArrayList<Component> getAssessmentInput() {
		ArrayList<Component> assessment = new ArrayList<>();
		int more = 1;
		System.out.println("ENTER COURSE ASSESSMENT ");

		while (addsUp(assessment) != 100) {
			while (more == 1) {
				System.out.println("Enter Component name: ");
				String title = sc.nextLine();

				System.out.println("Enter Component weightage (%) for " + title + ": ");
				int weightage = getChoice();

				assessment.add(new Component(title, weightage));

				if (addsUp(assessment) >= 100)
					more = 0;
			}
			if (addsUp(assessment) != 100) {
				System.out.println("Component weightage does not add up to 100%. Please re-enter Course Assessment.");
				more = 1;
				assessment.clear();
			}
			else 
				break;
		}
		return assessment;
	}


	private ArrayList<Component> updateAssessment(ArrayList<Component> assessment) {

		int more = 1;
		System.out.println("UPDATE COURSE ASSESSMENT ");

		while (more == 1) {
			for (Component item: assessment)  {
				System.out.println("Enter weightage for: " + item.getTitle());
				int weightage = getChoice();
				item.setWeightage(weightage);
			}

			if (addsUp(assessment) != 100) 
				System.out.println("Component weightage does not add up to 100%. Please re-enter Course Assessment.");

			else 
				break;
		}
		return assessment;
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
		}while(error);


		return facultyName;
	}

	private String chooseCourse(String facultyName, Semester sem) {
		ArrayList<String> courseNameList = university.getCourseListByFaculty(facultyName, sem);

		System.out.println("Choose Course:");
		printArray(courseNameList);
		boolean error = true;
		String courseCode = null;
		do {
			try {
				courseCode = courseNameList.get(getChoice() - 1);
				error = false;
			}
			catch(IndexOutOfBoundsException IndexOutOfBounds) {
				System.out.println("You have entered a wrong input \n"
						+ "Please enter a number from the list: ");
			}
		} while(error);
		return courseCode;
	}


	private LessonType chooseLessonType() {
		System.out.println("Choose Lesson Type:");
		LessonType.printLessonTypes();
		System.out.println("Enter Lesson Type (e.g. 'A'): ");
		char type = sc.nextLine().toLowerCase().charAt(0);
		do {
			switch(type) {
			case 'a':
				return LessonType.TYPE_A;

			case 'b':
				return LessonType.TYPE_B;

			case 'c':
				return LessonType.TYPE_C;

			default:
				System.out.println("You have entered a wrong input \n"
						+ "Please enter a valid type from the list: ");
				type = sc.nextLine().toLowerCase().charAt(0);
			}
		} while(true);
	}


	private String getMatricNoInput(ArrayList<String> list) {
		printMethod();
		String matricNo = null;
		System.out.println("Choose method:");
		int method = getChoice();

		switch (method) {
		case 1:
			matricNo = getStringInput("Enter matricNo: ");
			return matricNo;

		case 2:
			printArray(list);
			return list.get(getChoice()-1); 
		}
		return null;
	}


	private int addsUp(ArrayList<Component> assessment) {
		if (assessment.isEmpty())
			return 0;
		else {
			int total = 0;
			for (Component item: assessment) {
				total += item.getWeightage();
			}
			return total;
		}
	}


	private String processString(String string) {
		int index = string.indexOf('\t');
		return string.substring(0, index);

	}


	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ". " + list.get(i));
	}


	private void printAssessment(ArrayList<Component> assessment) {
		System.out.print("### COURSE ASSESSMENT COMPONENTS ###"); 
		for (Component item: assessment) 
			System.out.println(item.toString());
		System.out.print("~~~~~~~~~~~~~~~~~~\n"); 
	}


	private void printEditAssessmentOptions() {
		System.out.print(
				"### EDIT COURSE ASSESSMENT ###\n"
						+ " 1. Update weightage for existing components\n"
						+ " 2. Re-enter assessment (from scratch)\n"
						+ "~~~~~~~~~~~~~~~~~~\n"); 
	}


	private void printMethod() {
		System.out.print(
				"### CHOOSE METHOD ###\n"
						+ " 1. Enter Matric No.\n"
						+ " 2. Choose from student list\n"
						+ "~~~~~~~~~~~~~~~~~~\n"); 
	}


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
				System.out.println("Please enter a number from the menu: ");
				sc.reset();
				sc.next();
			}			
		}while (error);
		sc.nextLine();
		return choice;
	}


	private String getStringInput(String msg) {
		System.out.println(msg); 
		return sc.nextLine();
	}

}
