package model;

public class Course {
	private int course_id;
    public String course_name, start_date, end_date;
    
	public Course(int course_id, String course_name, String start_date, String end_date) {
		super();
		this.course_id = course_id;
		this.course_name = course_name;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	

}
