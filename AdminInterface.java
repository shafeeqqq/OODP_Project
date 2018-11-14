import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminInterface {

	Scanner sc = new Scanner(System.in);
	/**
	 * This attribute is used to access the university object
	 */
	private University university;
	
	/**
	 * This constructor initialises the university object for the admin interface
	 * @param university
	 */
	public AdminInterface(University university) {
		this.university = university;
	}

	
	/**
	 * this method gets the user choice and depending on the choice
	 * 1. add a new faculty,
	 * 2. add a new course,
	 * 3. add a new faculty staff,
	 * 4. add a new student,
	 * 5. prints the course statistics,
	 * 6. sets marks for a students corresponding to a course,
	 * 7. edit the course weightage,
	 * 8. prints the student list by group
	 * 9. checks course vacancy
	 */
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

	
	/**
	 * this method prints the menu for the admin interface
	 */
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
	
	
	/**
	 * This method passes the faculty name 
	 * and calls the addFaculty method in university
	 * to add faculty
	 */
	private void addFaculty() {
		String facultyName = getStringInput("Enter Faculty Name:");
		university.addFaculty(facultyName);
	}

	
	/**
	 * This method adds a new course to the faculty, 
	 * by calling the addCourse method in university 
	 * while passing in the required parameters
	 */
	private void addCourse() {
		String facultyName = chooseFaculty();
		String courseCode = getStringInput("Enter Course Code:");
		String courseName = getStringInput("Enter Course Name:");
		String staffID = chooseCoordinator(facultyName);
		Semester sem = university.getCurrentSemester();

		LessonType lessonType = chooseLessonType();	
		ArrayList<Component> assessment = getAssessmentInput();

		university.addCourseToFaculty(facultyName, courseCode, courseName, 
				staffID, lessonType, assessment, sem);
	}

	
	/**
	 * This method adds a new facultyStaff
	 * by calling the addStaffToFaculty method in university
	 * passing facultyName and staffName
	 */
	private void addFacultyStaff() {
		String staffName = getStringInput("Enter Staff Name:");
		String facultyName = chooseFaculty();
		university.addStaffToFaculty(facultyName, staffName);
	}

	
	/**
	 * This method adds a new facultyStaff
	 * by calling the addStudent method in university
	 * passing facultyName and studentName
	 */
	private void addStudent() {
		String studentName = getStringInput("Enter Student Name:");
		String facultyName = chooseFaculty();

		university.addStudentToFaculty(facultyName, studentName, university.getCurrentSemester());
	}


	/**
	 * This method consolidates the parameters
	 * required for the updateCourseAssessment method in 
	 * university to update the assessment
	 */
	private void editCourseWeightage() {
		Semester sem = university.getCurrentSemester();	
		String facultyName = chooseFaculty();	
		String courseCode = processString(chooseCourse(facultyName, sem));
		ArrayList<Component> assessment  = university.getCourseAssessment(facultyName, sem, courseCode);		
		assessment = executeEditCourseAssessment(assessment);
		university.updateCourseAssessment(facultyName, sem, courseCode, assessment);
	}


	/**
	 * This method controls the execution of getting 
	 * updated course assessment. It asks the users if
	 * they would like to re-enter the assessment from scratch
	 * or simply edit the weightage for the existing components
	 */
	private ArrayList<Component> executeEditCourseAssessment(ArrayList<Component> assessment) {
		printAssessment(assessment); // TODO: make it string
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


	/**
	 * This method consolidates the parameters
	 * required for getCourseVacancyMsg: facultyName,
	 * semester and course code and makes a call to that
	 * method in university to get the transcript and
	 * prints it
	 */
	private void checkCourseVacancy() {
		Semester sem = university.getCurrentSemester();	
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);
		String msg = university.getCourseVacancyMsg(facultyName, sem,  processString(courseCode));

		System.out.println(msg);
	}

	/**
	 * This method consolidates the parameters
	 * required for printStudentListByGroup method in
	 * university : facultyName, semester and course code
	 * type, i.e. which group (lecture or tutorial - lab group
	 * is the same as the tutorial group) and makes a 
	 * call to print the result
	 */
	private void printStudentListByGroup() {
		Semester sem = university.getCurrentSemester();	
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);
		Character type = getStringInput("Print student list by lecture group(L) or tutorial group(T)?").toUpperCase().charAt(0);;
		while (type != 'L' && type !='T' ) {
			if (type != null)
				System.out.println("Invalid Input!");
			type = getStringInput("Print student list by lecture group(L) or tutorial group(T)?").toUpperCase().charAt(0);
		}
		university.printStudentListByGroup(facultyName, sem,  processString(courseCode), type);
	}

	/**
	 * This method consolidates the parameters
	 * required for printCourseStats method in
	 * university: facultyName, semester and course code
	 * and makes a call to print the result
	 */
	private void printCourseStats() {
		Semester sem = university.getCurrentSemester();	// TODO other semester? 
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);

		university.printCourseStats(facultyName, sem,  processString(courseCode));
	}


	/**
	 * This method gets the user input for the marks
	 * for the corresponding components of the course.
	 * It also does input validation to prevent invalid data
	 * from being saved. It then calls the updateMarks method
	 * in university to update the marks stored in course.
	 */
	private void setMarks() {

		Semester sem = university.getCurrentSemester();
		String facultyName = chooseFaculty();	
		String courseCode = chooseCourse(facultyName, sem);
		ArrayList<String> components = university.getComponentTitles(facultyName, sem,  processString(courseCode));
		HashMap<String, Double> updatedMarks = new HashMap<>();
		String matricNo = getMatricNoInput(university.getMatricNoList(facultyName, sem,  processString(courseCode)));
		
		while (!university.isValidMatricNo(matricNo)) {
			System.out.println("You have entered a wrong matric no!");
			matricNo = getMatricNoInput(university.getMatricNoList(facultyName, sem,  processString(courseCode)));
		}
		
		System.out.println("The list of components are: ");
		printArray(components);
		
		boolean error = true;
		int choice=-100;
		do {
			try {
				choice =chooseOption();
				while(choice < 0 || choice > components.size()) {
					if ((choice) > components.size() || choice <0) {
						System.out.println("You have entered an invalid option, please re-enter your choice!");
					}
					choice = chooseOption();
				}
				error = false;
			} catch(InputMismatchException inputMismatchException) {
				System.out.println("You have entered an invalid option, please re-enter your choice!");
				sc.nextLine();
			}
		} while (error);
		error = true;

		while (choice != -1) {

			Double mark = -100.00;
			while (mark < 0 || mark>100) {
				System.out.println("Enter mark for "+ components.get(choice)+ ":");
				mark = sc.nextDouble();
				if (mark < 0||mark >100) {
					System.out.println("You have entered an invalid mark! Please enter again");
				}
			}

			updatedMarks.put(components.get(choice), mark);
			System.out.println(components.get(choice) +" updated to "+ mark);
			System.out.println("To continue, choose the next components, (enter -1 to quit)");
			printArray(components);
			do {
				try {
					choice = chooseOption();
					if (choice == -2) {
						break;
					}
					while(choice < 0 || choice > components.size()) {
						if ((choice) > components.size() || choice <0) {
							System.out.println("You have entered an invalid option, please re-enter your choice!");
						}
						choice = chooseOption();
					}
					error = false;
				} catch(InputMismatchException inputMismatchException) {
					System.out.println("You have entered an invalid option, please re-enter your choice!");
					sc.nextLine();
				}
			}while(error);

			if (choice == -2) {
				break;
			}

		}
		university.updateMarks(facultyName, sem,  processString(courseCode), matricNo, updatedMarks);
	}

	
	/**
	 * This method gets an integer input from the user
	 * for the component and returns the value after 
	 * subtracting one
	 */
	private int chooseOption() {
		System.out.println("Choose component you want to edit: ");
		int result = sc.nextInt();
		return result-1;
	}

	
	/**
	 * This method displays a list of available staff for the users
	 * to choose coordinator for the course. It is a supporting method
	 * for the addCourse method.
	 */
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

	
	/**
	 * This method gets user input for assessment components
	 * and the corresponding weightage. It is a supporting method
	 * for the addCourse and editCourseWeightage methods
	 */
	private ArrayList<Component> getAssessmentInput() {
		ArrayList<Component> assessment = new ArrayList<>();
		int more = 1;
		System.out.println("Enter Course Assessment");

		while (addsUp(assessment) != 100) {
			while (more == 1) {
				System.out.println("Enter Component name: ");
				String title = sc.nextLine().toLowerCase();
				System.out.println("Enter Component weightage (%) for " + title + ": ");
				int weightage = getIntegerInput();

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


	/**
	 * This method gets user input for weightage for existing
	 * assessment components. It is a supporting method editCourseWeightage method
	 */
	private ArrayList<Component> updateAssessment(ArrayList<Component> assessment) {
		ArrayList<Component> updated = new ArrayList<>();
		int more = 1;
		System.out.println("Enter Course Assessment");

		while (more == 1) {
			for (Component item: assessment)  {
				System.out.println("Enter weightage for " + item.getTitle() + ":");
				int weightage = getIntegerInput();
				updated.add(new Component(item.getTitle(), weightage));
			}

			if (addsUp(assessment) != 100) 
				System.out.println("Component weightage does not add up to 100%. Please re-enter Course Assessment.");

			else 
				break;
		}
		return updated;
	}

	
	/**
	 * This method displays the list of faculty and returns the facultyName of the
	 * chosen faculty. It is a supporting method for the addCourse method among others
	 * which require choosing faculty
	 */
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

	
	/**
	 * This method displays the list of courses by faculty and semester and
	 * returns the course code of the chosen course. It is a supporting method
	 * for the editCourseWeigthage method among others which require choosing course
	 */
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

	
	/**
	 * This method displays the lesson types along with the details
	 * returns the chosen lessonType. It is a supporting method
	 * for the addCourse method
	 */
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
						+ "Please enter a valid type (e.g. 'A') from the list: ");
				type = sc.nextLine().toLowerCase().charAt(0);
			}
		} while(true);
	}

	/**
	 * This method provides 2 
	 */
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

	
	/**
	 * The purpose of this method is to check if the entered component weightage 
	 * adds up to 100%. Returns the sum of the component weightage
	 */
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

	
	/**
	 * This method gets and returns the course code from a string
	 * containing course code and course name
	 */
	private String processString(String string) {
		int index = string.indexOf('\t');
		return string.substring(0, index);

	}

	/**
	 * The method prints a list of string sequentially with a number
	 * for the user to choose the corresponding item easily
	 */
	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ". " + list.get(i));
	}

	
	/**
	 * This method formats and prints a list of components so that it 
	 * is readable
	 */
	private void printAssessment(ArrayList<Component> assessment) {
		System.out.print("### COURSE ASSESSMENT COMPONENTS ###\n"); 
		for (Component item: assessment) 
			System.out.print(item.toString());
		System.out.print("~~~~~~~~~~~~~~~~~~\n"); 
	}

	
	/**
	 * This method prints the options for editing assessment
	 */
	private void printEditAssessmentOptions() {
		System.out.print(
				"### EDIT COURSE ASSESSMENT ###\n"
						+ " 1. Update weightage for existing components\n"
						+ " 2. Re-enter assessment (from scratch)\n"
						+ "~~~~~~~~~~~~~~~~~~\n"); 
	}

	
	/**
	 * This method prints the options for getting matric no input
	 */
	private void printMethod() {
		System.out.print(
				"### CHOOSE METHOD ###\n"
						+ " 1. Enter Matric No.\n"
						+ " 2. Choose from student list\n"
						+ "~~~~~~~~~~~~~~~~~~\n"); 
	}

	
	/**
	 * This method gets and returns the integer input and does input validation.
	 * This is primarily used for choosing an item from a displayed list of items 
	 */
	private int getChoice() {
		System.out.println("Enter choice: ");
		boolean error = true;
		int choice = 0;
		do {
			try {
				choice = sc.nextInt();
				error = false;
			}
			catch (Exception e) {
				System.out.println("Invalid input!");
				System.out.println("Please enter a number from the menu: ");
				sc.reset();
				sc.next();
			}			
		} while (error);
		sc.nextLine();
		return choice;
	}


	/**
	 * This method prints the message and returns the string input. This is
	 * mainly used for getting string input such as faculty name, student name etc.
	 */
	private String getStringInput(String msg) {
		System.out.println(msg); 
		return sc.nextLine();
	}
	
	
	/**
	 * This method gets and returns the integer input and does input validation. This
	 * is primarily used for getting integer input such as assessment weightage etc.
	 */
	private int getIntegerInput() {
		boolean error = true;
		int choice = 0;
		do {
			try {
				choice = sc.nextInt();
				error = false;
			}
			catch (Exception e) {
				System.out.println("Invalid input!");
				System.out.println("Please enter an Integer value: ");
				sc.reset();
				sc.next();
			}			
		} while (error);
		sc.nextLine();
		return choice;
	}

}
