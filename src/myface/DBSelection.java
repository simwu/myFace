package myface;

/*
 * DBSelection Version 1
 * Used in SQL in the WHERE clause. If ommitted, a mass SELECT is assumed.
 */

public class DBSelection {

	private String	databaseName;
	private String	tableName;
	private String	columnName;				// This column will be compared to either a value or another column
	private String	columnOperator;			// Default: EQUAL
	private Object	columnValue;			
	private boolean	compareToColumnValue;
	private String	toDatabaseName;
	private String	toTableName;
	private String	toColumnName;		
	private boolean compareToColumnName;

	/*
	 * Overloaded Constructors
	 */
	
	public DBSelection(String columnName, String columnOperator, Object columnValue) {
		this.databaseName	= "";
		this.tableName		= "";
		this.columnName 	= columnName;
		
		if (columnOperator == DBRequest.NONE) {
			this.columnOperator = DBRequest.EQUAL;
		}
		else {
			this.columnOperator = columnOperator;
		}
		
		this.columnValue 			= columnValue;
		this.compareToColumnValue	= true;
		
		this.toDatabaseName			= "";
		this.toTableName			= "";
		this.toColumnName 			= "";
		this.compareToColumnName	= false;
	}
	
	public DBSelection(String tableName, String columnName, String columnOperator, Object columnValue) {
		this.databaseName	= "";
		this.tableName		= tableName;
		this.columnName 	= columnName;
		
		if (columnOperator == DBRequest.NONE) {
			this.columnOperator = DBRequest.EQUAL;
		}
		else {
			this.columnOperator = columnOperator;
		}
		
		this.columnValue 			= columnValue;
		this.compareToColumnValue	= true;
		
		this.toDatabaseName			= "";
		this.toTableName			= "";
		this.toColumnName 			= "";
		this.compareToColumnName	= false;
	}
	
	public DBSelection(String databaseName, String tableName, String columnName, String columnOperator, Object columnValue) {
		this.databaseName	= databaseName;
		this.tableName		= tableName;
		this.columnName 	= columnName;
		
		if (columnOperator == DBRequest.NONE) {
			this.columnOperator = DBRequest.EQUAL;
		}
		else {
			this.columnOperator = columnOperator;
		}
		
		this.columnValue 			= columnValue;
		this.compareToColumnValue	= true;
		
		this.toDatabaseName			= "";
		this.toTableName			= "";
		this.toColumnName 			= "";
		this.compareToColumnName	= false;
	}
	
	public DBSelection(String tableName, String columnName, String columnOperator, String toTableName, String toColumnName) {
		this.databaseName	= "";
		this.tableName		= tableName;
		this.columnName 	= columnName;
		
		if (columnOperator == DBRequest.NONE) {
			this.columnOperator = DBRequest.EQUAL;
		}
		else {
			this.columnOperator = columnOperator;
		}

		this.toDatabaseName			= "";
		this.toTableName			= toTableName;
		this.toColumnName 			= toColumnName;
		this.compareToColumnName	= true;
		
		this.columnValue			= null;
		this.compareToColumnValue	= false;
	}
	
	public DBSelection(String databaseName, String tableName, String columnName, String columnOperator, String toDatabaseName, String toTableName, String toColumnName) {
		this.databaseName	= databaseName;
		this.tableName		= tableName;
		this.columnName 	= columnName;
		
		if (columnOperator == DBRequest.NONE) {
			this.columnOperator = DBRequest.EQUAL;
		}
		else {
			this.columnOperator = columnOperator;
		}
		
		this.toDatabaseName			= toDatabaseName;
		this.toTableName			= toTableName;
		this.toColumnName 			= toColumnName;
		this.compareToColumnName	= true;
		
		this.columnValue			= null;
		this.compareToColumnValue	= false;
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

	public String getColumnOperator() {
		return columnOperator;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public boolean isCompareToColumnValue() {
		return compareToColumnValue;
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

	public boolean isCompareToColumnName() {
		return compareToColumnName;
	}	
}
