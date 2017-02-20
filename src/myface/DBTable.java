package myface;

/*
 * DBTable Version 1
 */

public class DBTable {

	private String	databaseName;
	private String	tableName;
	
	/*
	 * Constructor
	 */
	
	public DBTable(String databaseName, String tableName) {
		this.databaseName	= databaseName;
		this.tableName		= tableName;
	}

	/*
	 * Getters
	 */
	
	public String getDatabaseName() {
		return databaseName;
	}

	public String getTableName() {
		return tableName;
	}
}
