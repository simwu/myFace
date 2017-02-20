package unittests;

import java.sql.SQLException;
import java.util.List;

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
	
	public List<Object> get(String databaseName, String tableName) throws SQLException {
		return sqlDb.get(databaseName, tableName);
	}
	
	public static void main(String[] args) throws SQLException {
		TestSQLDB testSqlDB = new TestSQLDB();
		boolean connected = testSqlDB.connect("myface");
		
		// Test connection
		if (connected) {
			System.out.println("Connected to myface database.");
		}
		else {
			System.out.println("Unable to connect to myface database.");
		}
		
		// Test select from Gender table
		List<Object> list = testSqlDB.get("myface", "gender");
		
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
	}
}
