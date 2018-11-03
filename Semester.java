
public class Semester {
	private int year;
	private int number;
	
	Semester(int year, int number) {
		this.year = year;
		this.number = number;
	}

	public int getYear() {
		return year;
	}

	public int getNumber() {
		return number;
	}
	
	public String toString() {
		return (year + " Semester " + number);
	}
	
	public boolean equals(Semester semester) {
		if (semester.getYear() == this.year && semester.getNumber() == this.number)
			return true;
		else
			return false;
	}
	
	
}
