package fa.training.entities;

import java.sql.Date;

public class Student {
	private String studentId;
	private String fullName;
	private Date dob;
	private String major;
	private double gpa;
	private String className;

	public Student() {
		
	}
	
	public Student(String studentId, String fullName, Date dob, String major, double gpa, String className) {
	this.setStudentId(studentId);
	this.setFullName(fullName);
	this.setDob(dob);
	this.setMajor(major);
	this.setGpa(gpa);
	this.setClassName(className);
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
    public String toString() {
        return "Student{" + "studentId='" + studentId + '\'' + ", fullName='" + fullName + '\'' + ", dob=" + dob + ", major='" + major + '\'' +
                ", gpa=" + gpa + ", className='" + className + '\'' + '}';
    }
}
