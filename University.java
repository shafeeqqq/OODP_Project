import java.util.ArrayList;

public class University {
	private String name;
	private ArrayList<Faculty> facultyList;
	private ArrayList<Semester> semesterList;
	
	private ArrayList<String> matricNoList;
	private ArrayList<String> staffIDList;
	
	University(String universityName) {
		this.name = universityName;
	}
	
	
	public void addFaculty(Faculty faculty) {
		
		facultyList.add(faculty);
	}
	
	public ArrayList<Faculty> getFacultyList() {
		return facultyList;
	}
	
	public ArrayList<String> getFacultyNameList() {
		ArrayList<String> facultyNameList = new ArrayList<>();
		
		for (Faculty faculty: facultyList) 
			facultyNameList.add(faculty.getFacultyName());
		return facultyNameList;
	}
	
	
	public ArrayList<Semester> getSemesterList() {
		return semesterList;
	}
	
	
	public void addStaffToFaculty(String facultyName, String staffName) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStaff(staffName, generateStaffID());
	}
	
	public void addStudentToFaculty(String facultyName, String studentName) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStudent(studentName, generateMatricNo());
	}
	
	
	private Faculty getFacultyByName(String facultyName) {
		for (Faculty faculty: facultyList) {
			if (faculty.getFacultyName().equals(facultyName))
				return faculty;
		}
			return null;
	}


	public String generateMatricNo() {
		int base = 100000;
		int num;
		
		if (matricNoList.isEmpty())
			num = base;
		else 
			num = getLast(0);	// type=0 for student
		
		String newMatricNo = "S" + num;
		
		if (!matricNoList.contains(newMatricNo))
			return newMatricNo;
		else
			return "Error generating Matric No";
	}
	

	public String generateStaffID() {
		int base = 100000;
		int num;
		
		if (staffIDList.isEmpty())
			num = base;
		else 
			num = getLast(1);	// type=1 for staff
		
		String newStaffID = "F" + num;
		if (!staffIDList.contains(newStaffID))
			return newStaffID;
		else
			return "Error generating staffID";
	}
	
	private int getLast(int type) {
		int num = -100;
		if (type == 0) {
			int lastIndex = matricNoList.size()-1;
			num = Integer.parseInt(matricNoList.get(lastIndex).substring(1, lastIndex +1));	
		}
		
		else if (type ==1) {
			int lastIndex = staffIDList.size()-1;
			num = Integer.parseInt(staffIDList.get(lastIndex).substring(1, lastIndex +1));	
		}
		return num + 1;
	}
	
	public Faculty getFacultyOfStudent(String matricNo) {
		Student currentStudent;
		for (Faculty faculty: facultyList) {
			currentStudent = faculty.getStudent(matricNo);
			if ( currentStudent != null)
				return faculty;
		}
		return null;
	}
	
	public boolean isValidMatricNo(String matricNo) {
		if (matricNoList.contains(matricNo))
			return true;
		
		else 
			return false;
	}
	


	public Semester getCurrentSemester() {
		if (semesterList.size() != 0)
			return semesterList.get(semesterList.size()-1);
		else 
			return null;
	}

	
	
}
