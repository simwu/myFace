package myface;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/*
 * Version 2.0
 */

public class SQLDB {
	
	private Connection 				connection;
	
	private final String			SQL_SERVER 					= "localhost:3306";
	private final String			CONNECTION_STRING_PREFIX	= "jdbc:mysql://" + SQL_SERVER + "/";
	private final String			CONNECTION_STRING_SUFFIX	= "?";
	private final String			DEFAULT_USER_NAME			= "root";
	private final String			DEFAULT_PASSWORD			= "root";
	private final int				OPERATION_FAILED			= -1;
	
	/*
	 * Constructor
	 */
	
	public SQLDB() {
		connection = null;
	}
	
	/*
	 * Connect
	 */
	
	public boolean connect(String databaseName) {
		boolean connected = false;
		connection 		  = null;
		
		// Locate the JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Connect to the MySQL Server
			try {
				connection = DriverManager.getConnection(CONNECTION_STRING_PREFIX + databaseName + CONNECTION_STRING_SUFFIX, DEFAULT_USER_NAME, DEFAULT_PASSWORD);
				System.out.println("SQLDB::connect(): MySQL Server connected.");
				connected = true;
			}
			catch (SQLException e) {
				System.out.println("SQLDB::connect(): MySQL Server not connected.");
			}
		}
		catch (ClassNotFoundException e) {
			System.out.println("SQLDB::connect(): MySQL JDBC Driver not found.");
		}
		
		return connected;
	}
	
	/*
	 * Disconnect
	 */
	
	public void disconnect() throws SQLException {
		
		try{
			// Ignore requests to disconnect if not currently connected. 
			// Can happen if a search done in persistent mode failed to find anything.
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
				System.out.println("SQLDB::disconnect(): SQL Server disconnected.");
			}
		}
		catch (SQLException e) {
			System.out.println("SQLDB::disconnect(): Unable to disconnect from SQL Server.");
		}
	}

	/*
	 * CRUD: Create(Add)
	 */
	
	public int add(DBRequest request, boolean returnGeneratedKey) throws SQLException {
		int result = OPERATION_FAILED;
		
		List<DBTable> tableList = request.getTableList();
		
		if (tableList.size() > 0) {
		
			if (connect(tableList.get(0).getDatabaseName())) {
				String query = "INSERT INTO " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " (";
				
				List<DBUpdate> updateList = request.getUpdateList();

				if (updateList.size() > 0) {

					for (int index = 0; index < updateList.size(); index++) {
						query = query + "`" + updateList.get(index).getColumnName() + "`, ";
					}
					
					// Remove last comma and space, and add close parenthesis and VALUES clause
					query = query.substring(0, query.length() - 2) + " ) VALUES (";
					
					for (int index = 0; index < updateList.size(); index++) {
						query = query + "'" + updateList.get(index).getColumnValue() + "', ";
					}
					
					// Remove last comma and space, and add close parenthesis
					query = query.substring(0, query.length() - 2) + ")";
					
					try {
						Statement statement = connection.createStatement();
						
						// Execute the query. Statement.RETURN_GENERATED_KEYS returns the auto. increment key generated.
						if (returnGeneratedKey) {
							result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
							
							ResultSet resultSet = statement.getGeneratedKeys();
				            
							while (resultSet.next()) {
				                result = resultSet.getInt(1);
				            } 
							
				            resultSet.close();
						}
						else {
							result = statement.executeUpdate(query);
						}
						
						if (result <= 0) {
							System.out.println("SQLDB::add(): SQL INSERT into table " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " failed. Error Code: " + result);
						}
					}
					catch (SQLException e) {
						System.out.println("SQLDB::add(): SQL INSERT into table " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " failed. Error Code: " + e.getErrorCode() + ", Error: " + e.getMessage());
					}
				}
				else {
					System.out.println("SQLDB::add(): SQL INSERT " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " cannot be executed. No update column names found.");
				}
				
				disconnect();
			}
			else {
				System.out.println("SQLDB::add(): SQL INSERT failed. Unable to connect to the database server.");
			}
		}
		else {
			System.out.println("SQLDB::add(): SQL INSERT failed. The database name is missing.");
		}
		
		return result;
	}
	
	/*
	 * CRUD: Read(Get)
	 */

	public List<Object> get(DBRequest request) throws SQLException {
		List<Object> list = new LinkedList<Object>();
		
		List<DBTable> tableList = request.getTableList();
		
		if (tableList.size() > 0) {
		
			if (connect(tableList.get(0).getDatabaseName())) {
				String query = "SELECT ";
				
				/*
				 *  Add result columns to the query. If list is empty all columns will be returned in resultset.
				 */
				
				List<DBResult> resultList = request.getResultList();
				
				if (resultList.size() > 0) {
			
					for (int index = 0; index < resultList.size(); index++) {
						DBResult result = resultList.get(index);
						
						if (result.getDatabaseName().length() > 0) {
							query = query + result.getDatabaseName() + ".";
						}
						if (result.getTableName().length() > 0) {
							query = query + result.getTableName() + ".";
						}
						query = query + result.getColumnName() + ", ";
					}
					
					// Remove the last comma and space
					query = query.substring(0, query.length() - 2); 
				}
				else {
					query = query + DBRequest.ALL_COLUMNS;
				}
				
				/*
				 *  Add tables to the query
				 */

				query = query + " FROM ";
				
				for (int index = 0; index < tableList.size(); index++) {
					DBTable table = tableList.get(index);
					query = query + table.getDatabaseName() + "." + table.getTableName() + ", ";
				}
				
				// Remove the last comma and space
				query = query.substring(0, query.length() - 2);

				/*
				 *  Add selection criteria to the query if applicable
				 */

				List<DBSelection> selectionList  = request.getSelectionList();
				List<String> logicalOperatorList = request.getLogicalOperatorList();
				
				if (selectionList.size() > 0) {

					query = query + " WHERE ";
					
					// Add the search column names to the update query
					for (int index = 0; index < selectionList.size(); index++) {
						DBSelection selection = selectionList.get(index);

						if (selection.getDatabaseName().length() > 0) {
							query = query + selection.getDatabaseName() + ".";
						}
						if (selection.getTableName().length() > 0) {
							query = query + selection.getTableName() + ".";
						}
						query = query + selection.getColumnName() + selection.getColumnOperator();
												
						if (selection.isCompareToColumnValue()) {
							query = query + " '" + selection.getColumnValue() + "' ";
						}
						else if (selection.isCompareToColumnName()) {
							
							if (selection.getToDatabaseName().length() > 0) {
								query = query + selection.getToDatabaseName() + ".";
							}
							if (selection.getToTableName().length() > 0) {
								query = query + selection.getToTableName() + ".";
							}
							query = query + selection.getToColumnName();
						}
											
						if (logicalOperatorList.size() > index) {
							query = query + logicalOperatorList.get(index);
						}
					}
				}
				
				/*
				 * Execute the query
				 */
				
				try {
					Statement statement = connection.createStatement();	
					ResultSet resultSet = statement.executeQuery(query);
					
					// Get the table column names
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int numberOfCols  = resultSetMetaData.getColumnCount();
					
					// For each ResultSet SQL table row
					while (resultSet.next()) {
					
						SQLDBRow sqlTableRow = new SQLDBRow();
						
						// Retrieve each of the row's columns and build a list of SQL table column names and objects
						for (int col = 1; col <= numberOfCols; col++) {
							
							// Do not use getArray(), exception occurs, not implemented in MySQL JDBC
							String sqlColumnName	= resultSetMetaData.getColumnName(col);
							Object sqlColumnValue 	= resultSet.getObject(col);
							
							// Add the current column to the list of SQL table columns for the current SQL table row
							sqlTableRow.setRow(sqlColumnName, sqlColumnValue);
						}
						
						// Add the current SQL table row to the list of SQL table rows
						list.add(sqlTableRow);
					}
				}
				catch (SQLException e) {
					System.out.println("SQLDB::get(): SQL SELECT from " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " table failed.");
				}
				catch (Exception e) {
					// Only when running in Debug mode a null pointer exception is thrown on the connection.createStatement().
					// The connection is null.
					System.out.println("SQLDB::get(): SQL SELECT from " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " table failed. Connection is null.");
				}
	
				// Disconnect from SQL Server
				disconnect();
			}
		}
		else {
			System.out.println("SQLDB::get(): SQL SELECT cannot be executed. The database name is missing.");
		}
			
		return list;
	}
	
	/*
	 * CRUD: Update(Put)
	 * 
	 * Can handle individual table row updates or mass table updates
	 */

	public boolean put(DBRequest request) throws SQLException {
		int result 		= 0;
		boolean updated = false;
		
		List<DBTable> tableList = request.getTableList();
		
		if (tableList.size() > 0) {
		
			if (connect(tableList.get(0).getDatabaseName())) {
				try {
					Statement statement = connection.createStatement();
					
					// Add the database and table to be updated
					String query = "UPDATE " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName();				
					
					List<DBUpdate> updateList = request.getUpdateList();
					
					if (updateList.size() > 0) {
						query = query + " SET ";
						
						// Add the update column names to the update query
						for (int index = 0; index < updateList.size(); index++) {
							String columnName = updateList.get(index).getColumnName();						
							query = query + columnName + " = '" + updateList.get(index).getColumnValue() + "', ";
						}
						
						// Remove last comma and space, and add the WHERE clause
						query = query.substring(0, query.length() - 2);
						
						// Add search criteria if applicable
						List<DBSelection> selectionList  = request.getSelectionList();
						List<String> logicalOperatorList = request.getLogicalOperatorList();
						
						if (selectionList.size() > 0) {
							query = query + " WHERE ";
							
							// Add the search column names to the update query
							for (int index = 0; index < selectionList.size(); index++) {
								String columnName = selectionList.get(index).getColumnName();
								
								query = query + columnName + " " + selectionList.get(index).getColumnOperator()
											  + " '"
											  + selectionList.get(index).getColumnValue()
											  + "' ";

								if (logicalOperatorList.size() > index) {
									query = query + logicalOperatorList.get(index) + " ";
								}
							}
						}
						
						if (selectionList.size() > 0 || (selectionList.size() == 0 && request.isMassUpdate())) {
							result = statement.executeUpdate(query);
							
							if (result > 0) {
								updated = true;
							}
						}
						else {
							System.out.println("SQLDB::put(): SQL UPDATE " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " cannot be executed. No search columns found and not a mass update.");
						}
					}
					else {
						System.out.println("SQLDB::put(): SQL UPDATE " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() + " cannot be executed. No update column names found.");
					}
				}
				catch (SQLException e) {
					System.out.println("SQLDB::put(): SQL UPDATE " + tableList.get(0).getDatabaseName() + "." + tableList.get(0).getTableName() +
									   " failed.");
					System.out.println("Error Code: " + e.getErrorCode() + ", Error: " + e.getMessage());
				}
				
				// Disconnect
				disconnect();
			}
			else {
				System.out.println("SQLDBModel::put(): MySQL UPDATE failed. Unable to connect to the database server.");
			}
		}
		else {
			System.out.println("SQLDB::put(): SQL UPDATE failed. The database name is missing.");
		}
		
		return updated;
	}
	
	/*
	 * Password Hash
	 */
	
	public String passwordHash(String message) throws HashGenerationException {	 
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-512");
	        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	 
	        return convertByteArrayToHexString(hashedBytes);
	    }
	    catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
	        throw new HashGenerationException("Could not generate hash from String", ex);
	    }
	}
	
	private String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();

	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    
	    return stringBuffer.toString();
	}
	
	public class HashGenerationException extends Exception {

		public HashGenerationException() {
			super();
		}
		
		public HashGenerationException(String message, Throwable throwable) {
			super(message, throwable);
		}

		public HashGenerationException(String message) {
			super(message);
		}

		public HashGenerationException(Throwable throwable) {
			super(throwable);
		}
	}
}
