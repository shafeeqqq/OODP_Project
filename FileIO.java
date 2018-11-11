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

	private static final String SEPARATOR1 = "|";
	private static final String SEPERATOR2 = ",";
	private static final String STUDENT_FILE = "students.txt";
	private static final String FACULTYSTAFF_FILE = "facultystaff.txt";
	private static final String COURSE_FILE = "courses.txt";
	private static final String STUDENT_INFO_FILE = "studinfo";
	private University university;

	FileIO (University university) {
		this.university = university;
	}

	public void populateData() {
		try {
			readStudents(STUDENT_FILE);
			readFacultyStaff(FACULTYSTAFF_FILE);
			readCourses(COURSE_FILE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void readCourses (String filename) throws IOException {
		// read String from text file
		ArrayList<String> courseList = readFromFile(filename);

		for (int i = 0 ; i < courseList.size() ; i++) {
			String line = courseList.get(i);
			String[] strArr = line.split("\\|");

			String courseCode = strArr[0];// first token
			String courseName = strArr[1];// second token
			String facultyName = strArr[2];
			int year = Integer.parseInt(strArr[3]);
			int number = Integer.parseInt(strArr[4]);
			Semester sem = university.getSemester(new Semester(year,number));
			String coordinator = strArr[5];
		
			ArrayList<Component> assessment = new ArrayList<>() ;
			LessonType lessonType;

			if (strArr[6].equals("TYPE_A")) {
				lessonType = LessonType.TYPE_A; 
			}
			else if (strArr[6].equals("TYPE_B")) {
				lessonType = LessonType.TYPE_B; 
			}
			else{
				lessonType = LessonType.TYPE_C; 
			}
			
			String components = strArr[7];
			String[] titleOrWeightage = components.split("\\,");
			
			for(int j=0; j < titleOrWeightage.length ;j=j+2) {
				String title = titleOrWeightage[j];
				int weightage = Integer.parseInt(titleOrWeightage[j+1]);
				Component component = new Component(title, weightage);
				assessment.add(component);
			}
			ArrayList<StudentInfo> studentInfoList  = new ArrayList<>();
			
			for (i=8; i<strArr.length; i++) {
				String[] currentStudentInfo = strArr[i].split(",");
				String currentMatric = currentStudentInfo[0];
				String currentTutorialGroup = currentStudentInfo[1];
				HashMap<String, Double> marks = new HashMap<>();
				for (int j = 2;j<currentStudentInfo.length;j++) {
					marks.put(assessment.get(j-2).getTitle(), Double.parseDouble(currentStudentInfo[j]));
				}
				StudentInfo current = new StudentInfo(currentMatric, currentTutorialGroup, marks);
				studentInfoList.add(current);
				
			}
			university.addCourseToFaculty(facultyName, courseCode, courseName, coordinator, lessonType, assessment, sem,studentInfoList);
		}
	}


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


	public void readFacultyStaff(String filename) throws IOException {
		// read String from text file
		ArrayList<String> facultyStaffList = readFromFile(filename);

		for (int i = 0 ; i < facultyStaffList.size() ; i++) {
			String line = facultyStaffList.get(i);
			String[] strArr = line.split("\\|");

			String staffName = strArr[0];// first token
			String staffID = strArr[1];// second token
			String facultyName = strArr[2];

			/*for(int j=2; j < strArr.length ;j=j+3) {                         //to add in if we need workloadbysemester
					//year,number
					int year = Integer.parseInt(strArr[j]);
					int number = Integer.parseInt(strArr[j+1]);
					String coursecode= strArr[j+2];
					String[] str = coursecode.split("\\,");
					ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(str));
				}*/

			university.addStaffToFaculty(facultyName, staffName, staffID);

		}
	}

	public static void saveStudents(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store Students data

		for (int i = 0 ; i < al.size() ; i++) {
			Student stud = (Student)al.get(i);
			StringBuilder st =  new StringBuilder() ;
			st.append(stud.getStudentName().trim());
			st.append(SEPARATOR1);
			st.append(stud.getMatricNo());
			st.append(SEPARATOR1);
			HashMap<Semester, ArrayList<String>> candidature = stud.getCandidature();
			for(Semester sem : candidature.keySet()) {
				st.append(sem.getYear());
				st.append(SEPARATOR1);
				st.append(sem.getNumber());
				st.append(SEPARATOR1);
				for(String course: candidature.get(sem)) {
					st.append((course).trim());
					st.append(SEPERATOR2);
				}
				st.append(SEPARATOR1);
			}

			alw.add(st.toString()) ;
		}
		writeToFile(filename,alw);
	}


	public static void writeToFile(String fileName, List data) throws IOException  {
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
}
