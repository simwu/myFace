package myface;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myface.SQLDB.HashGenerationException;

public class LoginView extends Stage {

	private Stage				primaryStage;
	private Controller 			controller;
	private TextField			userNameTextField;
	private PasswordField		passwordField;
	private PasswordField		newPasswordField;
	private Label				messageLabel;
	private TextField			firstNameTextField;
	private TextField			lastNameTextField;
	private ComboBox<String>	cityComboBox;
	private ComboBox<String>	stateComboBox;
	private ComboBox<String>	genderComboBox;
	private TextField			emailTextField;
	private TextField			dobTextField;
	private UserProfile			userProfile;
	
	private final int			LOGIN_PANE_WIDTH	= 750;
	private final int			LOGIN_PANE_HEIGHT	= 320;
	private final int			TITLE_WIDTH			= 240;
	private final int			TITLE_HEIGHT		= 20;
	private final int			FIELD_WIDTH			= 240;
	private final int			FIELD_HEIGHT		= 20;
	private final int			BUTTON_WIDTH		= 240;
	private final int			BUTTON_HEIGHT		= 20;
	private final String		LOGIN_IMAGE			= "img/login.png";	
	
	/*
	 * Constructor
	 */
	
	public LoginView(Stage primaryStage, Controller controller) {
		this.primaryStage	= primaryStage;
		this.controller 	= controller;
		
		VBox contentPane = new VBox();
		
		HBox loginHBox = new HBox();
		contentPane.getChildren().add(loginHBox);

		// Login Pane
		GridPane loginGridPane = new GridPane();
		loginGridPane.setPrefSize(LOGIN_PANE_WIDTH, LOGIN_PANE_HEIGHT);
		loginGridPane.setAlignment(Pos.TOP_CENTER);
		loginGridPane.setHgap(10);
		loginGridPane.setVgap(10);
		loginGridPane.setPadding(new Insets(10, 10, 20, 10));

		ColumnConstraints loginGridPaneCol1 = new ColumnConstraints();
		loginGridPaneCol1.setPercentWidth(20);
		ColumnConstraints loginGridPaneCol2 = new ColumnConstraints();
		loginGridPaneCol2.setPercentWidth(20);
		ColumnConstraints loginGridPaneCol3 = new ColumnConstraints();
		loginGridPaneCol3.setPercentWidth(20);
		ColumnConstraints loginGridPaneCol4 = new ColumnConstraints();
		loginGridPaneCol4.setPercentWidth(20);
		ColumnConstraints loginGridPaneCol5 = new ColumnConstraints();
		loginGridPaneCol5.setPercentWidth(20);
		
		loginGridPane.getColumnConstraints().addAll(loginGridPaneCol1, loginGridPaneCol2,
				loginGridPaneCol3, loginGridPaneCol4, 
				loginGridPaneCol5);
		
		loginHBox.getChildren().add(loginGridPane);
		
		/*
		 * Login
		 */
		
		Label loginLabel = new Label("Login");
		loginLabel.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT);
		loginLabel.setAlignment(Pos.CENTER);
		
		Label userNameLabel = new Label("User Name");
		userNameLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		userNameLabel.setAlignment(Pos.CENTER_RIGHT);
		
		userNameTextField = new TextField();
		userNameTextField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		userNameTextField.setAlignment(Pos.CENTER_LEFT);
		userNameTextField.setTooltip(new Tooltip("Enter your User Name here"));
		userNameTextField.setPromptText("User Name");
		
		// Col, Row
		loginGridPane.add(loginLabel, 0, 0);
		GridPane.setColumnSpan(loginLabel, 2);
		
		loginGridPane.add(userNameLabel, 0, 2);
		loginGridPane.add(userNameTextField, 1, 2);

		Label passwordLabel = new Label("Password");
		passwordLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		passwordLabel.setAlignment(Pos.CENTER_RIGHT);
		
		passwordField = new PasswordField();
		passwordField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		passwordField.setAlignment(Pos.CENTER_LEFT);
		passwordField.setPromptText("Password");
		
		loginGridPane.add(passwordLabel, 0, 3);
		loginGridPane.add(passwordField, 1, 3);
		
		/*
		 * Sign Up
		 */
		
		Label signupLabel = new Label("Sign Up");
		signupLabel.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT);
		signupLabel.setAlignment(Pos.CENTER);
		
		Label firstNameLabel = new Label("First Name");
		firstNameLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		firstNameLabel.setAlignment(Pos.CENTER_RIGHT);
		
		firstNameTextField = new TextField();
		firstNameTextField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		firstNameTextField.setAlignment(Pos.CENTER_LEFT);
		firstNameTextField.setTooltip(new Tooltip("Enter your First Name here"));
		firstNameTextField.setPromptText("First Name");
		
		loginGridPane.add(signupLabel, 3, 0);
		GridPane.setColumnSpan(signupLabel, 2);
		
		loginGridPane.add(firstNameLabel, 3, 2);
		loginGridPane.add(firstNameTextField, 4, 2);
		
		Label lastNameLabel = new Label("Last Name");
		lastNameLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		lastNameLabel.setAlignment(Pos.CENTER_RIGHT);
		
		lastNameTextField = new TextField();
		lastNameTextField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		lastNameTextField.setAlignment(Pos.CENTER_LEFT);
		lastNameTextField.setTooltip(new Tooltip("Enter your Last Name here"));
		lastNameTextField.setPromptText("Last Name");
		
		loginGridPane.add(lastNameLabel, 3, 3);
		loginGridPane.add(lastNameTextField, 4, 3);
		
		Label cityLabel = new Label("City");
		cityLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		cityLabel.setAlignment(Pos.CENTER_RIGHT);
		
		cityComboBox = new ComboBox<String>();
		cityComboBox.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		cityComboBox.setVisibleRowCount(10);
		cityComboBox.setEffect(new DropShadow());

		List<City> cityList = controller.getCityList();

		if (cityList.size() > 0) {
			cityComboBox.getItems().add("Select a City");
			
			for (City city : cityList) {
				cityComboBox.getItems().add(city.getName());
			}
			
			cityComboBox.getSelectionModel().selectFirst();
		}
		
		loginGridPane.add(cityLabel, 3, 4);
		loginGridPane.add(cityComboBox, 4, 4);
		
		Label stateLabel = new Label("State");
		stateLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		stateLabel.setAlignment(Pos.CENTER_RIGHT);
		
		stateComboBox = new ComboBox<String>();
		stateComboBox.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		stateComboBox.setVisibleRowCount(10);
		stateComboBox.setEffect(new DropShadow());

		List<State> stateList = controller.getStateList();

		if (stateList.size() > 0) {
			stateComboBox.getItems().add("Select a State");
			
			for (State state : stateList) {
				stateComboBox.getItems().add(state.getAbbreviation());
			}
			
			stateComboBox.getSelectionModel().selectFirst();
		}
		
		loginGridPane.add(stateLabel, 3, 5);
		loginGridPane.add(stateComboBox, 4, 5);
		
		Label genderLabel = new Label("Gender");
		genderLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		genderLabel.setAlignment(Pos.CENTER_RIGHT);
		
		genderComboBox = new ComboBox<String>();
		genderComboBox.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		genderComboBox.setVisibleRowCount(10);
		genderComboBox.setEffect(new DropShadow());

		List<Gender> genderList = controller.getGenderList();

		if (genderList.size() > 0) {
			genderComboBox.getItems().add("Select a Gender");
			
			for (Gender gender : genderList) {
				genderComboBox.getItems().add(gender.getName());
			}
			
			genderComboBox.getSelectionModel().selectFirst();
		}
		
		loginGridPane.add(genderLabel, 3, 6);
		loginGridPane.add(genderComboBox, 4, 6);
		
		Label emailLabel = new Label("Email");
		emailLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		emailLabel.setAlignment(Pos.CENTER_RIGHT);
		
		emailTextField = new TextField();
		emailTextField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		emailTextField.setAlignment(Pos.CENTER_LEFT);
		emailTextField.setTooltip(new Tooltip("Enter your Email here"));
		emailTextField.setPromptText("Email");
		
		loginGridPane.add(emailLabel, 3, 7);
		loginGridPane.add(emailTextField, 4, 7);
		
		Label newPasswordLabel = new Label("Password");
		newPasswordLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		newPasswordLabel.setAlignment(Pos.CENTER_RIGHT);
		
		newPasswordField = new PasswordField();
		newPasswordField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		newPasswordField.setAlignment(Pos.CENTER_LEFT);
		newPasswordField.setPromptText("Password");
		
		loginGridPane.add(newPasswordLabel, 3, 8);
		loginGridPane.add(newPasswordField, 4, 8);
		
		Label dobLabel = new Label("DOB");
		dobLabel.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
		dobLabel.setAlignment(Pos.CENTER_RIGHT);
		
		dobTextField = new TextField();
		dobTextField.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
		dobTextField.setAlignment(Pos.CENTER_LEFT);
		dobTextField.setTooltip(new Tooltip("Enter your DOB here"));
		dobTextField.setPromptText("mm/dd/yy");
		
		loginGridPane.add(dobLabel, 3, 9);
		loginGridPane.add(dobTextField, 4, 9);
		
		messageLabel = new Label();
		messageLabel.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT);
		messageLabel.setAlignment(Pos.CENTER);
		
		loginGridPane.add(messageLabel, 0, 10);
		GridPane.setColumnSpan(messageLabel, 5);
		
		/*
		 * Buttons
		 */
		
		Button loginButton = new Button();
		loginButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// Scale the image to 20x20 without preserving its original aspect ratio
		Image loginImage = new Image(LOGIN_IMAGE, 20, 20, false, false);
		ImageView loginImageView = new ImageView(loginImage);
		loginButton.setGraphic(loginImageView);
		// Display image to the right of the text
		loginButton.setContentDisplay(ContentDisplay.RIGHT);
		loginButton.setText("Login");
		loginButton.setEffect(new DropShadow());
		loginButton.setOnAction(new LoginButtonEventHandler());
		
		loginGridPane.add(loginButton, 1, 11);
		
		Button resetButton = new Button();
		resetButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// Scale the image to 20x20 without preserving its original aspect ratio
		loginImage = new Image(LOGIN_IMAGE, 20, 20, false, false);
		loginImageView = new ImageView(loginImage);
		resetButton.setGraphic(loginImageView);
		// Display image to the right of the text
		resetButton.setContentDisplay(ContentDisplay.RIGHT);
		resetButton.setText("Reset");
		resetButton.setEffect(new DropShadow());
		resetButton.setOnAction(new ResetButtonEventHandler());
		
		loginGridPane.add(resetButton, 2, 11);
		
		Button signupButton = new Button();
		signupButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// Scale the image to 20x20 without preserving its original aspect ratio
		loginImage = new Image(LOGIN_IMAGE, 20, 20, false, false);
		loginImageView = new ImageView(loginImage);
		signupButton.setGraphic(loginImageView);
		// Display image to the right of the text
		signupButton.setContentDisplay(ContentDisplay.RIGHT);
		signupButton.setText("Sign Up");
		signupButton.setEffect(new DropShadow());
		signupButton.setOnAction(new SignupButtonEventHandler());
		
		loginGridPane.add(signupButton, 4, 11);
		
		// Scene
		Scene scene = new Scene(contentPane);
		setScene(scene);
		setTitle("MyFace");
		centerOnScreen();
		//setResizable(false);
	}
	
	/*
	 * Login Button Event Handler
	 */
	
	private class LoginButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (userNameTextField.getText().length() > 0 && passwordField.getText().length() > 0) {
				
				try {
					userProfile = controller.login(userNameTextField.getText(), passwordField.getText());

					if (userProfile.getUserProfileId() > 0) {
						messageLabel.setText("Welcome to MyFace!");
					}
					else {
						messageLabel.setText("Invalid user name or password");
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				catch (HashGenerationException e) {
					e.printStackTrace();
				}
			}
			else {
				messageLabel.setText("Enter your user name and password");
			}
		}
	}
	
	/*
	 * Reset Button Event Handler
	 */
	
	private class ResetButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			userNameTextField.setText("");
			passwordField.setText("");
			
			firstNameTextField.setText("");
			lastNameTextField.setText("");
			cityComboBox.getSelectionModel().selectFirst();
			stateComboBox.getSelectionModel().selectFirst();
			genderComboBox.getSelectionModel().selectFirst();
			emailTextField.setText("");
			newPasswordField.setText("");
			dobTextField.setText("");
			
			messageLabel.setText("");
		}
	}
	
	/*
	 * Sign Up Button Event Handler
	 */
	
	private class SignupButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (firstNameTextField.getText().length() > 0 &&
				lastNameTextField.getText().length() > 0 &&
				cityComboBox.getSelectionModel().getSelectedIndex() > 1 &&
				stateComboBox.getSelectionModel().getSelectedIndex() > 1 &&
				genderComboBox.getSelectionModel().getSelectedIndex() > 1 &&
				emailTextField.getText().length() > 0 &&
				newPasswordField.getText().length() > 0 &&
				dobTextField.getText().length() > 0 && dobTextField.getText().matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}")) {
				
				UserProfile userProfile = new UserProfile();
				userProfile.setFirstName(firstNameTextField.getText());
				userProfile.setLastName(lastNameTextField.getText());
				// Adjust city id to account for first entry (undetermined -1)
				userProfile.setCityId(cityComboBox.getSelectionModel().getSelectedIndex() - 1);
				// Adjust state id to account for first entry (undetermined -1)
				userProfile.setStateId(stateComboBox.getSelectionModel().getSelectedIndex() - 1);
				// Adjust gender id to account for first entry (undetermined -1)
				userProfile.setGenderId(genderComboBox.getSelectionModel().getSelectedIndex() - 1);
				userProfile.setEmail(emailTextField.getText());
				userProfile.setPassword(newPasswordField.getText());

				// Convert to SQL date format
				SimpleDateFormat sqlDateformat = new SimpleDateFormat("yyyy/MM/dd");

				try {
					java.util.Date utilDate = sqlDateformat.parse(dobTextField.getText());
			        Date sqlDate = new Date(utilDate.getTime());
					userProfile.setDob(sqlDate);
					
					userProfile = controller.signup(userProfile);

					if (userProfile.getUserProfileId() > 0) {
						messageLabel.setText("Welcome to MyFace!");
						
						userNameTextField.setText("");
						passwordField.setText("");
						
						firstNameTextField.setText("");
						lastNameTextField.setText("");
						cityComboBox.getSelectionModel().selectFirst();
						stateComboBox.getSelectionModel().selectFirst();
						genderComboBox.getSelectionModel().selectFirst();
						emailTextField.setText("");
						newPasswordField.setText("");
						dobTextField.setText("");
					}
					else {
						messageLabel.setText("Unable to sign-up");
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				catch (HashGenerationException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else {
				messageLabel.setText("Enter all your information correctly");
			}
		}
	}
}
