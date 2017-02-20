package myface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class View {

	/*
	 * Class Instance Variables
	 */
	
	private Stage		primaryStage;
	private Controller 	controller;
	private LoginView	loginView;
	
	/*
	 * Class Constants
	 */
	
	
	/*
	 * Constructor
	 */
	
	public View(Stage primaryStage, Controller controller) {
		this.primaryStage 	= primaryStage;
		this.controller 	= controller;
				
		// Create and show the LoginView first
		loginView = new LoginView(primaryStage, controller);
		loginView.show();
	}
}



