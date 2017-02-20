package myface;

public class Gender {

	/*
	 * Class Instance Variables
	 */
	
	private int		genderId;
	private String 	name;
	
	/*
	 * Constructor
	 */
	
	public Gender() {
		genderId = 0;
		name 	 = "";
	}
	
	/*
	 * Getters and Setters
	 */
	
	public int getGenderId() {
		return genderId;
	}
	
	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
