package myface;

import java.sql.Date;

public class UserProfile {

	/*
	 * Class Instance Variables
	 */
	
	private int		userProfileId;
	private String	firstName;
	private String	lastName;
	private int		cityId;
	private int		stateId;
	private int		genderId;
	private String	email;
	private String	password;
	private Date	dob;
	private boolean	active;
	
	/*
	 * Getters and Setters
	 */
	
	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int profileId) {
		this.userProfileId = profileId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
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

	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/*
	 * Constructor
	 */
	
	public UserProfile() {
		userProfileId 	= 0;
		firstName 		= "";
		lastName		= "";
		cityId			= 0;
		stateId			= 0;
		genderId		= 0;
		email			= "";
		password		= "";
		dob				= null;
		active			= false;
	}
}
