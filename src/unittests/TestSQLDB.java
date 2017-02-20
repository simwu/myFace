package unittests;

import java.sql.SQLException;
import java.util.List;

import myface.DBRequest;
import myface.SQLDB;
import myface.SQLDBRow;
import myface.SQLDBCol;

public class TestSQLDB {

	private SQLDB sqlDb;
	
	public TestSQLDB() {
		sqlDb = new SQLDB();
	}
	
	public boolean connect(String databaseName) {
		return sqlDb.connect(databaseName);
	}

	public int add(DBRequest request, boolean returnGeneratedKey) throws SQLException {
		return sqlDb.add(request, returnGeneratedKey);
	}
	
	public List<Object> get(DBRequest request) throws SQLException {
		return sqlDb.get(request);
	}
	
	public boolean put(DBRequest request) throws SQLException {
		return sqlDb.put(request);
	}
	
	public static void main(String[] args) throws SQLException {
		TestSQLDB testSqlDB = new TestSQLDB();
		boolean connected = testSqlDB.connect("myface");
		
		/*
		 *  Test the connection
		 */
		
		System.out.println("Test the connection");
		
		if (connected) {
			System.out.println("Connected to myface database.");
		}
		else {
			System.out.println("Unable to connect to myface database.");
		}
		
		/*
		 *  Get() Test: Specific row select from Gender table with single-column-value selection criteria
		 */

		System.out.println("\n" + "Get Test: Specific row select with single-column-value selection criteria");
		
		DBRequest request = new DBRequest();
		request.addTable("myface", "gender");
		
		// Add search criteria
		request.addSelection("GENDER_ID", DBRequest.GREATER, "-3");		// Run it with EQUAL, LESS, GREATER, etc.
		
		List<Object> list = testSqlDB.get(request);
		
		if (list.size() > 0) {
			System.out.println("Got back a single Gender table row:");
			
			for (int row = 0; row < list.size(); row++) {
				// Extract the current SQL table row
				Object sqlTableRow = list.get(row);				
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
				
				for (SQLDBCol col : sqlDBColList) {
					System.out.print("Col Name: " + col.getSqlDBColName() + ", Col Value: " + col.getSqlDBColValue() + "\t");
				}
				
				System.out.println();
			}
		}
		else {
			System.out.println("Did not get back any Gender table rows.");
		}
		
		/*
		 *  Get() Test: Specific row select from User Profiles table with multi-column-value selection criteria
		 */

		System.out.println("\n" + "Get Test: Specific row select with multi-column-value selection criteria");
		
		request = new DBRequest();
		request.addTable("myface", "user_profiles");
		
		// Add search multi-column search criteria
		request.addSelection("CITY_ID", DBRequest.EQUAL, "5");		// Run it with EQUAL, LESS, GREATER, etc.
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("STATE_ID", DBRequest.EQUAL, "5");		// Run it with EQUAL, LESS, GREATER, etc.
		
		list = testSqlDB.get(request);
		
		if (list.size() > 0) {
			System.out.println("Got back a single User Profiles table row:");
			
			for (int row = 0; row < list.size(); row++) {
				// Extract the current SQL table row
				Object sqlTableRow = list.get(row);				
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
				
				for (SQLDBCol col : sqlDBColList) {
					System.out.print("Col Name: " + col.getSqlDBColName() + ", Col Value: " + col.getSqlDBColValue() + "\t");
				}
				
				System.out.println();
			}
		}
		else {
			System.out.println("Did not get back any User Profiles table rows.");
		}
		
		/*
		 *  Get() Test: Mass row select from Gender table
		 */
		
		System.out.println("\n" + "Get Test: Mass row select");
		
		request = new DBRequest();
		request.addTable("myface", "gender");
		
		list = testSqlDB.get(request);
		
		if (list.size() > 0) {
			System.out.println("Got back a list of Gender table rows:");
			
			for (int row = 0; row < list.size(); row++) {
				// Extract the current SQL table row
				Object sqlTableRow = list.get(row);				
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
				
				for (SQLDBCol col : sqlDBColList) {
					System.out.print("Col Name: " + col.getSqlDBColName() + ", Col Value: " + col.getSqlDBColValue() + "\t");
				}
				
				System.out.println();
			}
		}
		else {
			System.out.println("Did not get back a list of Gender table rows.");
		}
		
		/*
		 * Get() Test: Multi-table DBRequest (long) with multi-column-name selection criteria
		 */
		
		System.out.println("\n" + "Get Test: Multi-table DBRequest (long) with multi-column-name selection criteria");

		request = new DBRequest();

		request.addTable("myface", "user_profiles");
		request.addTable("myface", "cities");
		request.addTable("myface", "states");
		request.addTable("myface", "gender");
		
		request.addSelection("myface", "user_profiles", "CITY_ID", DBRequest.EQUAL, "myface", "cities", "CITY_ID");
		request.addLogicalOperator(DBRequest.AND);		
		request.addSelection("myface", "user_profiles", "STATE_ID", DBRequest.EQUAL, "myface", "states", "STATE_ID");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("myface", "user_profiles", "GENDER_ID", DBRequest.EQUAL, "myface", "gender", "GENDER_ID");
		
		request.addResult("myface", "user_profiles", "FIRST_NAME");
		request.addResult("myface", "user_profiles", "LAST_NAME");
		request.addResult("myface", "cities", "NAME");
		request.addResult("myface", "states", "NAME");
		request.addResult("myface", "gender", "NAME");
		request.addResult("myface", "user_profiles", "EMAIL");

		list = testSqlDB.get(request);
		
		if (list.size() > 0) {
			System.out.println("Got back a single User Profile table row:");
			
			for (int row = 0; row < list.size(); row++) {
				// Extract the current SQL table row
				Object sqlTableRow = list.get(row);				
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
				
				for (SQLDBCol col : sqlDBColList) {
					System.out.println("Col Name: " + col.getSqlDBColName() + ", Col Value: " + col.getSqlDBColValue());
				}
				
				System.out.println();
			}
		}
		else {
			System.out.println("Did not get back any User Profile table rows.");
		}
		
		/*
		 * Get() Test: Multi-table DBRequest (long) with multi-column-name selection criteria
		 */
		
		System.out.println("Get Test: Multi-table DBRequest (short) with multi-column-name selection criteria");
		
		request = new DBRequest();

		// Add the Tables to the request
		request.addTable("myface", "user_profiles");
		request.addTable("myface", "cities");
		request.addTable("myface", "states");
		request.addTable("myface", "gender");

		// Add the selections to the request
		request.addSelection("user_profiles", "CITY_ID", DBRequest.EQUAL, "cities", "CITY_ID");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("user_profiles", "STATE_ID", DBRequest.EQUAL, "states", "STATE_ID");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("user_profiles", "GENDER_ID", DBRequest.EQUAL, "gender", "GENDER_ID");

		// Add the results to the request
		request.addResult("FIRST_NAME");
		request.addResult("LAST_NAME");
		request.addResult("cities", "NAME");
		request.addResult("states", "NAME");
		request.addResult("gender", "NAME");
		request.addResult("EMAIL");

		list = testSqlDB.get(request);
		
		if (list.size() > 0) {
			System.out.println("Got back a single User Profile table row:");
			
			for (int row = 0; row < list.size(); row++) {
				// Extract the current SQL table row
				Object sqlTableRow = list.get(row);				
				List<SQLDBCol> sqlDBColList = ((SQLDBRow) sqlTableRow).getRow();
				
				for (SQLDBCol col : sqlDBColList) {
					System.out.println("Col Name: " + col.getSqlDBColName() + ", Col Value: " + col.getSqlDBColValue());
				}
				
				System.out.println();
			}
		}
		else {
			System.out.println("Did not get back any User Profile table rows.");
		}
		
		/*
		 *  Put() Test: Specific row update to User Profiles table with single-column-value selection criteria
		 */

		System.out.println("Put Test: Specific row update with single-column-value selection criteria");
		
		request = new DBRequest();
		request.addTable("myface", "user_profiles");

		// Add search criteria
		request.addSelection("EMAIL", DBRequest.EQUAL, "javier@javierandre.com");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("GENDER_ID", DBRequest.EQUAL, 1);

		// Add update
		request.addUpdate("GENDER_ID", "3");
		
		if (testSqlDB.put(request)) {
			System.out.println("myface.user_profiles updated");
		}
		else {
			System.out.println("myface.user_profiles could not be updated");
		}
				
		/*
		 *  Put() Test: Specific row update to User Profiles table with multi-column-value selection criteria
		 */

		System.out.println("\n" + "Put Test: Specific row update with multi-column-value selection criteria");

		request = new DBRequest();
		request.addTable("myface", "user_profiles");

		// Add search multi-column search criteria
		request.addSelection("EMAIL", DBRequest.EQUAL, "javier@javierandre.com");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("FIRST_NAME", DBRequest.EQUAL, "Javier");
		request.addLogicalOperator(DBRequest.AND);
		request.addSelection("GENDER_ID", DBRequest.EQUAL, 3);
		
		// Add update
		request.addUpdate("GENDER_ID", "1");

		if (testSqlDB.put(request)) {
			System.out.println("myface.user_profiles updated");
		}
		else {
			System.out.println("myface.user_profiles could not be updated");
		}
		
		/*
		 *  Put() Test: Mass row update to Gender table with massUpdate flag set
		 */
		
		System.out.println("\n" + "Put Test: Mass row update with massUpdate flag set");
		
		request = new DBRequest();
		request.addTable("myface", "gender");

		request.addUpdate("NAME", "Who the hell knows!");
		request.setMassUpdate(true);		// If ommited it will cause the update to fail
		
		if (testSqlDB.put(request)) {
			System.out.println("myface.gender mass updated");
		}
		else {
			System.out.println("myface.gender mass could not be mass updated");
		}
		
		/*
		 *  Add() Test: single row insert to User Profiles table
		 */

		System.out.println("\n" + "Add Test: Single row insert");

		request = new DBRequest();
		request.addTable("myface", "user_profiles");

		// Add update		
		request.addUpdate("FIRST_NAME", "Jude");
		request.addUpdate("LAST_NAME", "Andre");
		request.addUpdate("CITY_ID", 5);
		request.addUpdate("STATE_ID", 5);
		request.addUpdate("GENDER_ID", 1);
		request.addUpdate("EMAIL", "jude@javierandre.com");
		request.addUpdate("DOB", "2007/01/01");
		request.addUpdate("PASSWORD", "bosscat");

		boolean returnGeneratedKey = true;
		
		int userProfileId = testSqlDB.add(request, returnGeneratedKey);
		
		if (userProfileId > 0) {
			System.out.println("myface.user_profiles profile Id " + userProfileId + " added");
		}
		else {
			System.out.println("myface.user_profiles profile could not be added");
		}
	}
}
