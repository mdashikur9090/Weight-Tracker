package model;

public class Waist {
	private int id;
	private String m_date;
	private float mor_waist, eve_waist, average_wasit;
	public Waist(int id, String m_date, float mor_waist, float eve_waist, float average_wasit) {
		super();
		this.id = id;
		this.m_date = m_date;
		this.mor_waist = mor_waist;
		this.eve_waist = eve_waist;
		this.average_wasit = average_wasit;
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
	public float getMor_waist() {
		return mor_waist;
	}
	public void setMor_waist(float mor_waist) {
		this.mor_waist = mor_waist;
	}
	public float getEve_waist() {
		return eve_waist;
	}
	public void setEve_waist(float eve_waist) {
		this.eve_waist = eve_waist;
	}
	public float getAverage_wasit() {
		return average_wasit;
	}
	public void setAverage_wasit(float average_wasit) {
		this.average_wasit = average_wasit;
	}
	
	

}
