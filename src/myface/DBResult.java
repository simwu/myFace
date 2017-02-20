package myface;

/*
 * DBResult Version 1
 */

public class DBResult {

	private String	databaseName;
	private String	tableName;
	private String	columnName;

	/*
	 * Overloaded Constructors
	 */
	
	public DBResult(String columnName) {
		this.databaseName	= "";
		this.tableName		= "";
		this.columnName		= columnName;
	}
	
	public DBResult(String tableName, String columnName) {
		this.databaseName	= "";
		this.tableName		= tableName;
		this.columnName		= columnName;
	}
	
	public DBResult(String databaseName, String tableName, String columnName) {
		this.databaseName	= databaseName;
		this.tableName		= tableName;
		this.columnName		= columnName;
	}

	/*
	 * Getters and Setters
	 */
	
	public String getDatabaseName() {
		return databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumnName() {
		return columnName;
	}
}
