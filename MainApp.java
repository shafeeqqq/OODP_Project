import java.io.IOException;
import java.util.Scanner;

/**
 * TODO
 * check vacancy for tutorial/lab group?
 * tutorial group == lab group ??
 * add student to course with 0 vacancy in tut/lab
 * vacancy message 3/10
 * set marks marks range validation
 * set marks individually? or everyth at once
 * transcript -- display component marks
 */

//display all listing of students after addition of 1 student
public class MainApp {
	/**
	 * the main interfaces through university to other classes
	 */
	public static University university;
	/**
	 * this object reads and saves to the respective files
	 */
	public static FileIO file;
	static Scanner sc = new Scanner(System.in);

	/**
	 * The main function gives users 3 choices,
	 * to use as admin, students or to save the changes
	 */
	public static void main(String[] args) {
		university = new University("NTU");
		file = new FileIO(university);

		// dummy data -- faculty
		university.addFaculty("SCSE");
		university.addFaculty("NBS");

		file.populateData();

		printMainMenu();
		int choice = getChoice();
		while(choice != 6) {
			switch(choice) {
			case 1:{
				launchInterface("admin");
				break;				
			}
			case 2:{
				launchInterface("student");
				break;				
			}

			case 3:{
				try {
					Saves();
				} catch (IOException e) {
					System.out.println("Cannot save!");
					e.printStackTrace();
				}
				break;		
			}
			default:
				System.out.println("You have entered a wrong input!\n" + "Please enter 1,2 or 3!");
				break;
			}
			//printMainMenu();
			choice = getChoice();			
		}
	}

	
	/**
	 * this method saves all the data to the respective files
	 * @throws IOException
	 */
	private static void Saves() throws IOException {
		file.saveStudents("students.txt",university.getAllStudents());
		file.saveStaffs("facultystaff.txt", university.getAllStaffs());
		file.saveCourses("courses.txt",university.getallFaculty());
	}
	
	
	/**
	 * this method launches 2 interface, admin if mode is 1 and student if mode is 2
	 * @param mode
	 */
	private static void launchInterface(String mode) {
		if (mode.equals("student")) {
			String matricNo = getMatricNo();
			Faculty currentFaculty = university.getFacultyOfStudent(matricNo);

			if (currentFaculty == null) 
				System.out.println("Student not found");

			else {
				System.out.println("Launching student interface...");
				StudentInterface si = new StudentInterface(currentFaculty, matricNo, university);
				si.run();
			}	
		}
		else {
			AdminInterface ai = new AdminInterface(university);
			ai.run();
		}

	}

	
	/**
	 * this method gets the input of the user to be used as matricNo to be used to instantiate
	 * student object
	 * @return matricNo
	 */
	private static String getMatricNo() {
		System.out.println("Enter matric No: ");
		String matricNo = sc.nextLine();
		return matricNo;
	}

	
	/**
	 * this method gets an integer input from user
	 * @return a integer
	 */
	private static int getChoice() {
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
				System.out.println("Please enter 1,2 or 3: ");
				sc.reset();
				sc.next();
			}			
		}while (error);
		sc.nextLine();
		return choice;
	}

	
	/**
	 * this method prints the main menu inside the main function
	 */
	private static void printMainMenu() {
		System.out.print(
				"### Main Menu ###\n"
						+ " 1. admin\n"
						+ " 2. student\n"
						+ " 3. save all change \n"
						+ "~~~~~~~~~~~~~~~~~\n"); 

	}

}
