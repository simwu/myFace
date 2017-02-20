package myface;

public class State {

	/*
	 * Class Instance Variables
	 */

	private int		stateId;
	private String	abbreviation;
	private String	name;
	
	/*
	 * Constructor
	 */
	
	public State() {
		stateId 	= 0;
		abbreviation = "";
		name 		= "";
	}
	
	/*
	 * Getters and Setters
	 */
	
	public int getStateId() {
		return stateId;
	}
	
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
