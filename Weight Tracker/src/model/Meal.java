package model;

public class Meal {
	private int id;
	private String m_date, meal_name;
	private float gram, calories_PGr, total_calories;
	public Meal(int id, String m_date, String meal_name, float gram, float calories_PGr, float total_calories) {
		super();
		this.id = id;
		this.m_date = m_date;
		this.meal_name = meal_name;
		this.gram = gram;
		this.calories_PGr = calories_PGr;
		this.total_calories = total_calories;
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
	public String getMeal_name() {
		return meal_name;
	}
	public void setMeal_name(String meal_name) {
		this.meal_name = meal_name;
	}
	public float getGram() {
		return gram;
	}
	public void setGram(float gram) {
		this.gram = gram;
	}
	public float getCalories_PGr() {
		return calories_PGr;
	}
	public void setCalories_PGr(float calories_PGr) {
		this.calories_PGr = calories_PGr;
	}
	public float getTotal_calories() {
		return total_calories;
	}
	public void setTotal_calories(float total_calories) {
		this.total_calories = total_calories;
	}
	
	

}
