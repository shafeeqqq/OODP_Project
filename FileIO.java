import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileIO {
	/**
	 * stores the name of StudentFile
	 */
	private static final String STUDENT_FILE = "students.txt";
	/**
	 * stores the name of FacultyFile
	 */
	private static final String FACULTYSTAFF_FILE = "facultystaff.txt";
	/**
	 * stores the name of CourseFile
	 */
	private static final String COURSE_FILE = "courses.txt";
	/*
	 * instantiate a university object
	 */
	private University university;
	/**
	 * this constructor instantiate the university variable to be used to save and read files
	 * @param university
	 */
	FileIO (University university) {
		this.university = university;
	}
	
	/**
	 * this method is called at the start of the program and adds data to the university
	 * @throws FileNotFoundException 
	 */
	public void populateData() {
		try {
			readStudents(STUDENT_FILE);
			readFacultyStaff(FACULTYSTAFF_FILE);
			readCourses(COURSE_FILE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method reads courses from the file by splitting the line into different component
	 * by the delimiters to construct courses object 
	 * @param filename 		name of course file
	 * @throws IOException
	 */
	private void readCourses (String filename) throws IOException {
		// read String from text file
		ArrayList<String> courseList = readFromFile(filename);
		for (int i = 0 ; i < courseList.size() ; i++) {
			String line = courseList.get(i);
			String[] strArr = line.split("\\/");
			//first part is all course detail (courseCode, courseName, facultyName)
			String[] courseDetail = strArr[0].split("\\|");
			//second part is all the semestral data (each element contains one semester's data
			String[] semestralData = strArr[1].split("\\-");
			
			String courseCode = courseDetail[0]; 			//extract courseCode
			String courseName = courseDetail[1];			//extract courseName
			String facultyName = courseDetail[2];			//extract facultyName
			
			//checking through each portion of semestralData and creating the course for that semester
			for (String currentData: semestralData) {
				String[] currentSem = currentData.split("\\|");
				int year = Integer.parseInt(currentSem[0]);
				int number = Integer.parseInt(currentSem[1]);
				Semester sem = university.getSemester(new Semester(year,number));
				String coordinator = currentSem[2];
				
				ArrayList<Component> assessment = new ArrayList<>() ;
				LessonType lessonType;
				if (currentSem[3].equals("TYPE_A")) {
					lessonType = LessonType.TYPE_A; 
				}
				else if (currentSem[3].equals("TYPE_B")) {
					lessonType = LessonType.TYPE_B; 
				}
				else{
					lessonType = LessonType.TYPE_C; 
				}
				
				String components = currentSem[4];
				String[] titleOrWeightage = components.split("\\,");
				
				for(int j=0; j < titleOrWeightage.length ;j=j+2) {
					String title = titleOrWeightage[j].toLowerCase();
					int weightage = Integer.parseInt(titleOrWeightage[j+1]);
					Component component = new Component(title, weightage);
					assessment.add(component);
				}
				
				ArrayList<StudentInfo> studentInfoList  = new ArrayList<>();
				for (int x = 5; x < currentSem.length; x++) {
					String[] currentStudentInfo = currentSem[x].split(",");
					String currentMatric = currentStudentInfo[0];
					String currentTutorialGroup = currentStudentInfo[1];
					HashMap<String, Double> marks = new HashMap<>();
					for (int y = 2;y <currentStudentInfo.length;y++) {
						String mark = currentStudentInfo[y];
						if (mark.equals("null"))
							marks.put(assessment.get(y-2).getTitle(), null);
						else 
							marks.put(assessment.get(y-2).getTitle(), Double.parseDouble(mark));
					}

					StudentInfo current = new StudentInfo(currentMatric, currentTutorialGroup, marks);
					studentInfoList.add(current);
				}
				university.addCourseToFaculty(facultyName, courseCode, courseName, coordinator, lessonType, assessment, sem, studentInfoList);
			}
		}
	}

	/**
	 * this method reads students from the file by splitting the line into different component
	 * by the delimiters to construct students object 
	 * @param filename		name of student file
	 * @throws IOException
	 */
	private void readStudents(String filename) throws IOException {
		ArrayList<String> stringArray = readFromFile(filename);

		for (int i = 0 ; i < stringArray.size() ; i++) {
			String st = (String)stringArray.get(i);
			String[] strArr = st.split("\\|");
			// get individual 'fields' of the string separated by SEPARATOR

			String  name = strArr[0];// first token
			String  matricNo = strArr[1];// second token
			String facultyName = strArr[2];
			HashMap<Semester, ArrayList<String>> candidature = new HashMap<>();

			for(int j=3; j < strArr.length ;j=j+3) {
				//year,number
				int year = Integer.parseInt(strArr[j]);
				int number = Integer.parseInt(strArr[j+1]);
				Semester sem = university.getSemester(new Semester(year,number));

				String courseCodeList= strArr[j+2];
				String[] str = courseCodeList.split("\\,");
				ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(str));
				candidature.put(sem, arrayList);

			}
			university.addStudentToFaculty(facultyName, name, matricNo, candidature);

		}
	}

	/**
	 * this method reads facultyStaff from the file by splitting the line into different component
	 * by the delimiters to construct FacultyStaff object 
	 * @param filename		name of facultystaff file
	 * @throws IOException
	 */
	public void readFacultyStaff(String filename) throws IOException {
		// read String from text file
		ArrayList<String> facultyStaffList = readFromFile(filename);
		for (int i = 0; i < facultyStaffList.size(); i++) {
			String line = facultyStaffList.get(i);
			String[] strArr = line.split("\\/");
			String[] staffInfo = strArr[0].split("\\|");
			String[] semestralData = strArr[1].split("-");
			String staffName = staffInfo[0];
			String staffID = staffInfo[1];
			String facultyName = staffInfo[2];
			String coordinatorOf = staffInfo[3];
			HashMap<Semester,ArrayList<String>> workLoadBySemester = new HashMap<>();
			//HashMap<Semester, ArrayList<String>> workLoadBySemester = new HashMap<>();
			for(String currentData : semestralData) {
				String[] currentSem = currentData.split("\\|");
				int year = Integer.parseInt(currentSem[0]);
				int number = Integer.parseInt(currentSem[1]);
				Semester sem = university.getSemester(new Semester(year,number));
				ArrayList<String> currentSemData = new ArrayList<>();
				for (int y = 2;y<currentSem.length;y++) 
					currentSemData.add(currentSem[y]);
				workLoadBySemester.put(sem, currentSemData);
			}
			university.addStaffToFaculty(facultyName, staffName, staffID, coordinatorOf,workLoadBySemester);
		}
		

	}
	/**
	 * this method saves the current students in the database back to the file to be read later
	 * @param filename		name of file we are saving to
	 * @param al			arraylist of all the students in the university
	 * @throws IOException
	 */
	public void saveStudents(String filename, ArrayList<Student> al) throws IOException {
		ArrayList<String> result = new ArrayList<>() ;// to store Students data
		for (Student stud: al) {
			String std = "";
			std += stud.getStudentName();
			std += "|";
			std += stud.getMatricNo();
			std += "|";
			std += stud.getFacultyName();
			std += "|";
			HashMap<Semester, ArrayList<String>> candidature = stud.getCandidature();
			for(Semester sem : candidature.keySet()) {
				std += sem.getYear();
				std += "|";
				std += sem.getNumber();
				std += "|";
				for(String course: candidature.get(sem)) {
					std += course.trim();
					std += ",";
				}
				std += "|";		
			}
			std = std.substring(0, std.length() - 2)+"|";
			result.add(std) ;
		}
		writeToFile(filename,result);
	}

	/**
	 * this method write to the fileName given the arraylist of data
	 * @param fileName		filename we want to save to
	 * @param data			arraylist of relevant data (student/course/facstaff)
	 * @throws IOException
	 */
	public static void writeToFile(String fileName, ArrayList<String> data) throws IOException  {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i =0; i < data.size() ; i++) {
				out.println((String)data.get(i));
			}
		}
		finally {
			out.close();
		}
	}
	/**
	 * this method reads from the file everything into arraylist of string
	 * each element of arraylist read one line from the file until a newline character is read in. 
	 * @param fileName	file name we are reading from
	 * @return everything written in the file into arraylist of string
	 * @throws IOException
	 */
	public static ArrayList<String> readFromFile(String fileName) throws IOException {
		ArrayList<String> data = new ArrayList<>();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()){
				data.add(scanner.nextLine());
			}
		}
		finally{
			scanner.close();
		}
		return data;
	}


	/**
	 * this method saves facultyStaff from the file to be read later 
	 * @param string		name of file we are saving to
	 * @param allStaffs		arraylist of all fac staff in the university
	 * @throws IOException
	 */
	public void saveStaffs(String string, ArrayList<FacultyStaff> allStaffs) throws IOException {
		ArrayList<String> result = new ArrayList<>() ;// to store Students data
		for (FacultyStaff staff: allStaffs) {
		//	System.out.println(stud.getStudentName());
		//	StringBuilder st =  new StringBuilder();
			String staf = "";
			staf += staff.getStaffName();
			staf += "|";
			staf += staff.getStaffID();
			staf += "|";
			staf += staff.getFacultyName();
			staf += "|";
			if (staff.getCoordinatorOf() == null) staf += "null";
			else staf += staff.getCoordinatorOf();
			staf += "|";
			staf += "/";
			HashMap<Semester, ArrayList<String>> workLoadBySemester = staff.getWorkLoadBySemester();
			for(Semester sem : workLoadBySemester.keySet()) {
				staf += sem.getYear();
				staf += "|";
				staf += sem.getNumber();
				staf += "|";
				for(String course: workLoadBySemester.get(sem)) {
					staf += course.trim();
					staf += ",";
				}
				staf += "|";		
			}
			staf = staf.substring(0, staf.length()-2)+ "|";
			result.add(staf) ;
		}
		writeToFile(string,result);
	}
	/**
	 * this method saves course into the file to be read later
	 * @param string		name of course file we are saving to
	 * @param allFaculty	list of all courses in the university to save
	 * @throws IOException
	 */
	public void saveCourses(String string, ArrayList<Faculty> allFaculty) throws IOException {
		ArrayList<String> result = new ArrayList<>() ;// to store Students data
		for (Faculty currentFaculty : allFaculty) {
			HashMap<Semester, ArrayList<Course>> currentCourseList = new HashMap<>();
			currentCourseList = currentFaculty.getCourseListBySem();
			for (Semester sem: currentCourseList.keySet()) {
				ArrayList<Course> currentCourseBySem = new ArrayList<>();
				currentCourseBySem = currentCourseList.get(sem);
				String course = "";
				for (Course currentCourse : currentCourseBySem) {
					course += currentCourse.getCourseCode();
					course += "|";
					course += currentCourse.getCourseName();
					course += "|";
					course += currentCourse.getFacultyName();
					course += "/";
					course += sem.getYear();
					course += "|";
					course += sem.getNumber();
					course += "|";					
					course += currentCourse.getCoordinator();
					course += "|";
					course += currentCourse.getLessonType();
					course += "|";
					for (Component component: currentCourse.getAssessment()) {
						course += component.getTitle();
						course += ",";
						course += component.getWeightage();
						course += ",";
					}
					course = course.substring(0, course.length()-1) + "|";
					
					for (StudentInfo studentinfo: currentCourse.getStudentInfoList()) {
						course += studentinfo.getMatricNo();
						course += ",";
						course += studentinfo.getTutorialGroup();
						course += ",";
						HashMap<String, Double> marks = studentinfo.getMarks();
						for (String componentTitle: marks.keySet()) {
							course += marks.get(componentTitle);
							course += ",";
						}
						course = course.substring(0, course.length()-1) + "|";
					}
			//		course = course.substring(0, course.length() -1);
					result.add(course);	
					course = "";
				}
			}
		}
		writeToFile(string,result);	
	}
}
