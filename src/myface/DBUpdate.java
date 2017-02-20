package myface;

/*
 * DBUpdate Version 1
 */

public class DBUpdate {

	private String	databaseName;
	private String	tableName;
	private String	columnName;				// This column will be compared to either a value or another column
	private Object	columnValue;
	private String	toDatabaseName;
	private String	toTableName;
	private String	toColumnName;
	private boolean	setToColumnValue;
	private boolean setToColumnName;

	/*
	 * Overloaded Constructors
	 */
	
	public DBUpdate(String columnName, Object columnValue) {
		this.databaseName		= "";
		this.tableName			= "";
		this.columnName 		= columnName;
		this.columnValue 		= columnValue;
		
		this.toDatabaseName		= "";
		this.toTableName		= "";
		this.toColumnName		= "";
		
		this.setToColumnValue	= true;
		this.setToColumnName	= false;
	}
	
	public DBUpdate(String tableName, String columnName, Object columnValue) {
		this.tableName			= tableName;
		this.columnName 		= columnName;
		this.columnValue 		= columnValue;
		
		this.toDatabaseName		= "";
		this.toTableName		= "";
		this.toColumnName		= "";
		
		this.setToColumnValue	= true;
		this.setToColumnName	= false;
	}
	
	public DBUpdate(String databaseName, String tableName, String columnName, Object columnValue) {
		this.databaseName		= databaseName;
		this.tableName			= tableName;
		this.columnName 		= columnName;
		this.columnValue 		= columnValue;
		
		this.toDatabaseName		= "";
		this.toTableName		= "";
		this.toColumnName		= "";
		
		this.setToColumnValue	= true;
		this.setToColumnName	= false;
	}
	
	public DBUpdate(String tableName, String columnName, String toTableName, String toColumnName) {
		this.databaseName		= "";
		this.tableName			= tableName;
		this.columnName 		= columnName;
		
		this.toDatabaseName		= "";
		this.toTableName		= toTableName;
		this.toColumnName 		= toColumnName;
		
		this.setToColumnValue	= false;
		this.setToColumnName	= true;		
	}
	
	public DBUpdate(String databaseName, String tableName, String columnName, String toDatabaseName, String toTableName, String toColumnName) {
		this.databaseName		= databaseName;
		this.tableName			= tableName;
		this.columnName 		= columnName;
		
		this.toDatabaseName		= toDatabaseName;
		this.toTableName		= toTableName;
		this.toColumnName 		= toColumnName;
		
		this.setToColumnValue	= false;
		this.setToColumnName	= true;
		
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

	public String getColumnName() {
		return columnName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public String getToDatabaseName() {
		return toDatabaseName;
	}

	public String getToTableName() {
		return toTableName;
	}

	public String getToColumnName() {
		return toColumnName;
	}

	public boolean isCompareToColumnValue() {
		return setToColumnValue;
	}
	
	public boolean isCompareToColumnName() {
		return setToColumnName;
	}
}
