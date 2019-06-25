package model;

public class Weight {
	private int id;
	private String m_date;
	private float mor_weight, eve_weight, average_weight;
	
	
	public Weight(int id, String m_date, float mor_weight, float eve_weight, float average_weight) {
		super();
		this.id = id;
		this.m_date = m_date;
		this.mor_weight = mor_weight;
		this.eve_weight = eve_weight;
		this.average_weight = average_weight;
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


	public float getMor_weight() {
		return mor_weight;
	}


	public void setMor_weight(float mor_weight) {
		this.mor_weight = mor_weight;
	}


	public float getEve_weight() {
		return eve_weight;
	}


	public void setEve_weight(float eve_weight) {
		this.eve_weight = eve_weight;
	}


	public float getAverage_weight() {
		return average_weight;
	}


	public void setAverage_weight(float average_weight) {
		this.average_weight = average_weight;
	}
	
	

}
