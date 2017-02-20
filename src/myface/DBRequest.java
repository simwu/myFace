package myface;

import java.util.LinkedList;
import java.util.List;

/*
 * DBRequest Version 2
 */

public class DBRequest {
	
	private List<DBTable>		tableList;
	private List<DBSelection>	selectionList;
	private List<String>		logicalOperatorList;
	private List<DBUpdate>		updateList;
	private List<DBResult>		resultList;
	private boolean				massUpdate;

	// Parameters
	public static final String 	NONE 			= "";
	public static final String 	ALL_COLUMNS 	= "*";
	
	// Relational operators
	public static final String	EQUAL			= " = ";
	public static final String	NOT_EQUAL		= " != ";
	public static final String	LESS			= " < ";
	public static final String	LESS_EQUAL		= " <= ";
	public static final String	GREATER			= " > ";
	public static final String	GREATER_EQUAL	= " >= ";
	
	// Logical operators
	public static final String	NOT				= " NOT ";
	public static final String	AND				= " AND ";
	public static final String	OR				= " OR ";
	
	/*
	 * Constructor
	 */
	
	public DBRequest() {
		tableList 			= new LinkedList<DBTable>();
		selectionList 		= new LinkedList<DBSelection>();
		logicalOperatorList	= new LinkedList<String>();
		updateList 			= new LinkedList<DBUpdate>();
		resultList 			= new LinkedList<DBResult>();
		massUpdate			= false;
	}
	
	/*
	 * Getters
	 */
	
	public List<DBTable> getTableList() {
		return tableList;
	}
	
	public List<DBSelection> getSelectionList() {
		return selectionList;
	}

	public List<String> getLogicalOperatorList() {
		return logicalOperatorList;
	}

	public List<DBUpdate> getUpdateList() {
		return updateList;
	}
	
	public List<DBResult> getResultList() {
		return resultList;
	}
	
	public boolean isMassUpdate() {
		return massUpdate;
	}
	
	/*
	 * Setters
	 */
	
	public void addTable(String databaseName, String tableName) {
		tableList.add(new DBTable(databaseName, tableName));
	}
	
	public void addSelection(String columnName, String columnOperator, Object columnValue) {
		selectionList.add(new DBSelection(columnName, columnOperator, columnValue));
	}
	
	public void addSelection(String tableName, String columnName, String columnOperator, Object columnValue) {
		selectionList.add(new DBSelection(tableName, columnName, columnOperator, columnValue));		
	}
	
	public void addSelection(String databaseName, String tableName, String columnName, String columnOperator, Object columnValue) {
		selectionList.add(new DBSelection(databaseName, tableName, columnName, columnOperator, columnValue));				
	}
	
	public void addSelection(String tableName, String columnName, String columnOperator, String toTableName, String toColumnName) {
		selectionList.add(new DBSelection(tableName, columnName, columnOperator, toTableName, toColumnName));			
	}
	
	public void addSelection(String databaseName, String tableName, String columnName, String columnOperator, String toDatabaseName, String toTableName, String toColumnName) {
		selectionList.add(new DBSelection(databaseName, tableName, columnName, columnOperator, toDatabaseName, toTableName, toColumnName));					
	}

	public void addLogicalOperator(String operator) {
		logicalOperatorList.add(operator);
	}
	
	public void addUpdate(String columnName, Object columnValue) {
		updateList.add(new DBUpdate(columnName, columnValue));
	}
	
	public void addUpdate(String tableName, String columnName, Object columnValue) {
		updateList.add(new DBUpdate(tableName, columnName, columnValue));		
	}
	
	public void addUpdate(String databaseName, String tableName, String columnName, Object columnValue) {
		updateList.add(new DBUpdate(databaseName, tableName, columnName, columnValue));				
	}
	
	public void addUpdate(String columnName, String columnOperator, String toColumnName) {
		updateList.add(new DBUpdate(columnName, columnOperator, toColumnName));						
	}
	
	public void addUpdate(String tableName, String columnName, String toTableName, String toColumnName) {
		updateList.add(new DBUpdate(tableName, columnName, toTableName, toColumnName));								
	}
	
	public void addUpdate(String databaseName, String tableName, String columnName, String toDatabaseName, String toTableName, String toColumnName) {
		updateList.add(new DBUpdate(databaseName, tableName, columnName, toDatabaseName, toTableName, toColumnName));		
	}
	
	public void addResult(String columnName) {
		resultList.add(new DBResult(columnName));
	}
	
	public void addResult(String tableName, String columnName) {
		resultList.add(new DBResult(tableName, columnName));		
	}
	
	public void addResult(String databaseName, String tableName, String columnName) {
		resultList.add(new DBResult(databaseName, tableName, columnName));				
	}

	public void setMassUpdate(boolean massUpdate) {
		this.massUpdate = massUpdate;
	}
}
