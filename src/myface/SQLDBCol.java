package myface;

public class SQLDBCol {

	/*
	 * Class Instance Variables
	 */
	
	private String	sqlDBColName;
	private Object	sqlDBColValue;
	
	/*
	 * Constructor
	 */
	
	public SQLDBCol(String name, Object object) {
		sqlDBColName 	= name;
		sqlDBColValue 	= object;
	}


	/*
	 * Getters and Setters
	 * 
	 */
	
	public String getSqlDBColName() {
		return sqlDBColName;
	}

	public void setSqlDBColName(String sqlDBColName) {
		this.sqlDBColName = sqlDBColName;
	}

	public Object getSqlDBColValue() {
		return sqlDBColValue;
	}

	public void setSqlDBColValue(Object sqlDBColValue) {
		this.sqlDBColValue = sqlDBColValue;
	}
}
