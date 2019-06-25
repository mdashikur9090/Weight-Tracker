package model;

public class User {
	private int user_id;
    private String name, gender, activity_level, email, password;
    private int age;
    
	public User(int user_id, String name, String gender, String activity_level, String email, String password,
			int age) {
		super();
		this.user_id = user_id;
		this.name = name;
		this.gender = gender;
		this.activity_level = activity_level;
		this.email = email;
		this.password = password;
		this.age = age;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getActivity_level() {
		return activity_level;
	}
	public void setActivity_level(String activity_level) {
		this.activity_level = activity_level;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
    
    

}
