import java.util.ArrayList;

public class University {
	private ArrayList<Faculty> facultyList;
	private ArrayList<Semester> semesterList;
	
	private ArrayList<String> matricNoList;
	private ArrayList<String> staffIDList;
	
	University() {}
	
	
	public void addFaculty() {
		//TODO: add faculty
	}
	
	public ArrayList<Faculty> getFacultyList() {
		return facultyList;
	}
	
	public ArrayList<String> getFacultyNameList() {
	}
	
	
	public ArrayList<Semester> getSemesterList() {
		return semesterList;
	}
	
	
	public void addStaffToFaculty() {
		
		
	}
	
	public void addStudentToFaculty(Faculty faculty, String studentName) {
		
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


	public String generateStaffNo() {
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
	
	
}
