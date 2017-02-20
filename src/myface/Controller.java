package myface;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import myface.SQLDB.HashGenerationException;

public class Controller {

	private SQLDB 					sqlDb;
	private List<City>				cityList;
	private List<State>				stateList;
	private List<Gender>			genderList;
	
	public static final String		DATABASE					= "myface";
	public static final String		USER_PROFILES_TABLE			= "user_profiles";
	public static final String		MESSAGES_TABLE				= "messages";
	public static final String		CITIES_TABLE				= "cities";
	public static final String		STATES_TABLE				= "states";
	public static final String		GENDER_TABLE				= "gender";

	public static final String		USER_NAME_COLUMN			= "email";
	public static final String		PASSWORD_COLUMN				= "password";
	
	/*
	 * Constructor
	 */
	
	public Controller() throws SQLException, HashGenerationException {
		sqlDb 		= new SQLDB();

		getCities();
		getStates();
		getGenders();
	}
	
	/*
	 * City, State and Gender
	 */
	
	public List<City> getCityList() {
		return cityList;
	}
	
	public void getCities() throws SQLException, HashGenerationException {
		cityList = new LinkedList<City>();
		
		DBRequest request = new DBRequest();
		request.addTable(DATABASE, CITIES_TABLE);		
		request.addResult(DBRequest.ALL_COLUMNS);
		
		List<Object> sqlDBRowList = sqlDb.get(request);
		
		if (sqlDBRowList.size() > 0) {
			
			for (int row = 0; row < sqlDBRowList.size(); row++) {
				
				// Extract the current SQL table row (there should be only one row for a user's profile)
				Object sqlTableRow = sqlDBRowList.get(row);
				
				City city = new City();
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
	
				// For each SQLDBCol
				for (int col = 0; col < sqlDBColList.size(); col++) {
					
					// Match the current SQLDBCol with its UserProfile class instance variable
					switch (sqlDBColList.get(col).getSqlDBColName()) {
						case "CITY_ID": {
							city.setCityId((int)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
						case "STATE_ID": {
							city.setStateId((int)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
						case "NAME": {
							city.setName((String)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
					}
				}
				
				cityList.add(city);
			}
		}
	}

	public List<State> getStateList() {
		return stateList;
	}
	
	public void getStates() throws SQLException, HashGenerationException {
		stateList = new LinkedList<State>();
		
		DBRequest request = new DBRequest();
		request.addTable(DATABASE, STATES_TABLE);
		request.addResult(DBRequest.ALL_COLUMNS);
		
		List<Object> sqlDBRowList = sqlDb.get(request);
		
		if (sqlDBRowList.size() > 0) {
			
			for (int row = 0; row < sqlDBRowList.size(); row++) {
				
				// Extract the current SQL table row (there should be only one row for a user's profile)
				Object sqlTableRow = sqlDBRowList.get(row);
				
				State state = new State();
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
	
				// For each SQLDBCol
				for (int col = 0; col < sqlDBColList.size(); col++) {
					
					// Match the current SQLDBCol with its UserProfile class instance variable
					switch (sqlDBColList.get(col).getSqlDBColName()) {
						case "STATE_ID": {
							state.setStateId((int)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
						case "ABBREVIATION": {
							state.setAbbreviation((String)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
						case "NAME": {
							state.setName((String)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
					}
				}
				
				stateList.add(state);
			}
		}
	}
	
	public List<Gender> getGenderList() {
		return genderList;
	}
	
	public void getGenders() throws SQLException, HashGenerationException {
		genderList = new LinkedList<Gender>();
		
		DBRequest request = new DBRequest();
		request.addTable(DATABASE, GENDER_TABLE);
		request.addResult(DBRequest.ALL_COLUMNS);
		
		List<Object> sqlDBRowList = sqlDb.get(request);
		
		if (sqlDBRowList.size() > 0) {
			
			for (int row = 0; row < sqlDBRowList.size(); row++) {
				
				// Extract the current SQL table row (there should be only one row for a user's profile)
				Object sqlTableRow = sqlDBRowList.get(row);
				
				Gender gender = new Gender();
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
	
				// For each SQLDBCol
				for (int col = 0; col < sqlDBColList.size(); col++) {
					
					// Match the current SQLDBCol with its UserProfile class instance variable
					switch (sqlDBColList.get(col).getSqlDBColName()) {
						case "GENDER_ID": {
							gender.setGenderId((int)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
						case "NAME": {
							gender.setName((String)sqlDBColList.get(col).getSqlDBColValue());
							break;
						}
					}
				}
				
				genderList.add(gender);
			}
		}
	}
	
	/*
	 * Sign Up, Login and Logoff
	 */
	
	public UserProfile signup(UserProfile userProfile) throws SQLException, HashGenerationException {
		DBRequest request = new DBRequest();
		request.addTable(DATABASE, USER_PROFILES_TABLE);
	
		request.addUpdate("FIRST_NAME", userProfile.getFirstName());
		request.addUpdate("LAST_NAME", userProfile.getLastName());
		request.addUpdate("CITY_ID", userProfile.getCityId());
		request.addUpdate("STATE_ID", userProfile.getStateId());
		request.addUpdate("GENDER_ID", userProfile.getGenderId());
		request.addUpdate("EMAIL", userProfile.getEmail());
		request.addUpdate("DOB", userProfile.getDob());
		request.addUpdate("PASSWORD", userProfile.getPassword());
		request.addResult(DBRequest.ALL_COLUMNS);
		
		boolean returnGeneratedKey = true;
		
		userProfile.setUserProfileId(sqlDb.add(request, returnGeneratedKey));
		
		return userProfile;
	}
	
	public UserProfile login(String userName, String password) throws SQLException, HashGenerationException {
		UserProfile userProfile = new UserProfile();
		
		DBRequest request = new DBRequest();
		request.addTable(DATABASE, USER_PROFILES_TABLE);
		
		request.addSelection(USER_NAME_COLUMN, DBRequest.EQUAL, userName);
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection(PASSWORD_COLUMN, DBRequest.EQUAL, sqlDb.passwordHash(password));

		request.addResult(DBRequest.ALL_COLUMNS);
		
		List<Object> sqlDBRowList = sqlDb.get(request);
		
		if (sqlDBRowList.size() > 0) {
			// Extract the current SQL table row (there should be only one row for a user's profile)
			Object sqlTableRow = sqlDBRowList.get(0);
			
			List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
			
			// FOR STUDENTS 3/10/16
			// For each SQLDBCol
			for (int col = 0; col < sqlDBColList.size(); col++) {
				
				// Match the current SQLDBCol with its UserProfile class instance variable
				switch (sqlDBColList.get(col).getSqlDBColName()) {
					case "PROFILE_ID": {
						userProfile.setUserProfileId((int)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "FIRST_NAME": {
						userProfile.setFirstName((String)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "LAST_NAME": {
						userProfile.setLastName((String)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "CITY_ID": {
						userProfile.setCityId((int)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "STATE_ID": {
						userProfile.setStateId((int)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "GENDER_ID": {
						userProfile.setGenderId((int)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "EMAIL": {
						userProfile.setEmail((String)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
					case "DOB": {
						userProfile.setDob((Date)sqlDBColList.get(col).getSqlDBColValue());
						break;
					}
				}
			}
		}
		
		return userProfile;
	}
	
	/*
	 * Gender
	 */
	
	public List<Gender> loadGender() {
		List<Gender> list = new LinkedList<Gender>();

		
		
		return list;
	}
}
