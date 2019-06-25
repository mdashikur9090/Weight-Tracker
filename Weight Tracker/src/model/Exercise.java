package model;

public class Exercise {
	private int id;
	private String m_date, exercise_name;
    private float minit,cal_burn_pm, total_cal_burn;
	public Exercise(int id, String m_date, String exercise_name, float minit, float cal_burn_pm, float total_cal_burn) {
		super();
		this.id = id;
		this.m_date = m_date;
		this.exercise_name = exercise_name;
		this.minit = minit;
		this.cal_burn_pm = cal_burn_pm;
		this.total_cal_burn = total_cal_burn;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getM_date() {
		return m_date;
	}
	public void setM_date(String m_date) {
		this.m_date = m_date;
	}
	public String getExercise_name() {
		return exercise_name;
	}
	public void setExercise_name(String exercise_name) {
		this.exercise_name = exercise_name;
	}
	public float getMinit() {
		return minit;
	}
	public void setMinit(float minit) {
		this.minit = minit;
	}
	public float getCal_burn_pm() {
		return cal_burn_pm;
	}
	public void setCal_burn_pm(float cal_burn_pm) {
		this.cal_burn_pm = cal_burn_pm;
	}
	public float getTotal_cal_burn() {
		return total_cal_burn;
	}
	public void setTotal_cal_burn(float total_cal_burn) {
		this.total_cal_burn = total_cal_burn;
	}
    
    

}
