package myface;

import java.util.LinkedList;
import java.util.List;

public class SQLDBRow {

	/*
	 * Class Instance Variables
	 */
	
	private List<SQLDBCol> sqlTableRow;
	
	/*
	 * Constructor
	 */
	
	public SQLDBRow() {
		sqlTableRow = new LinkedList<SQLDBCol>();
	}
	
	/*
	 * Getters and Setters
	 * 
	 */
	
	public List<SQLDBCol> getRow() {
		return sqlTableRow;
	}
	
	public void setRow(String name, Object object) {

		SQLDBCol sqlDBColModel = new SQLDBCol(name, object);
		sqlTableRow.add(sqlDBColModel);
	}
}
