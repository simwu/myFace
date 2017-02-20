package myface;

public class City {

	/*
	 * Class Instance Variables
	 */
	
	private int		cityId;
	private int		stateId;
	private String	name;
	
	/*
	 * Constructor
	 */
	
	public City() {
		cityId 	= 0;
		stateId = 0;
		name 	= "";
	}

	/*
	 * Getters and Setters
	 */
	
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

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
