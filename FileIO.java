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
	private University university;

	FileIO (University university) {
		this.university = university;
	}
	
	public void populateStudentData() {
		try {
			readStudents(STUDENT_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void readStudents(String filename) throws IOException {
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
				Semester sem = new Semester(year,number);
				
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
				String[] strArr = st.split("\\|");
				
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

	public void populateFacultyData() {
		// TODO Auto-generated method stub
		
	}

}
