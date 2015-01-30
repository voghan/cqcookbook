package com.cookbook.cq.domain;

public class Profile extends Base {

	private static final long serialVersionUID = 6752879673162619635L;
    public static final String  SESSION_KEY = "SESSION_KEY";

	private String gender;
	private String age;
	private String preferredGenre;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPreferredGenre() {
		return preferredGenre;
	}
	public void setPreferredGenre(String preferredGenre) {
		this.preferredGenre = preferredGenre;
	}



}
