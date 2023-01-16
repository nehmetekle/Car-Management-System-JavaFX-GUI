package application;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class LogIn extends BorderPane{ 
	private Button btnLogin = new Button("Log In");
	private TextField userName = new TextField();
	private PasswordField  password = new PasswordField();
	private String uName = userName.getText();
	private String personName = null;
	
	Stop[] stops = new Stop[] {
	         new Stop(0, Color.AQUA),
	         new Stop(1, Color.RED)
	      };
	LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	
	public LogIn() {
		createLogin();
	}
	
	private void createLogin() {
		this.setMinWidth(750);
		this.setMinHeight(750);
		this.setBackground(Background.fill(Color.web("#404040")));
		setHead();
		setForm();
	} 
	
	private void setHead() {
		VBox head = new VBox();
		head.setPadding(new Insets(50));
		Image logo = new Image("C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\image\\logo.png");
		ImageView logoImg = new ImageView(logo);
		Text txt = new Text("Welcome");
		txt.setFont(Font.font("Arial Black", FontWeight.BOLD ,30));
		txt.setFill(gradient);
		head.getChildren().addAll(logoImg, txt);
		head.setAlignment(Pos.TOP_CENTER);
		this.setTop(head);
	}
	
	private void setForm() {
		changeScene sceneHandler = new changeScene();
		GridPane form = new GridPane();
		form.setAlignment(Pos.TOP_CENTER);
		form.setVgap(15);
		
		form.add(userName, 0, 1);
		userName.setPromptText("Username");
		userName.setMinHeight(35);
		userName.setMinWidth(200);
		userName.setMaxWidth(200);
		userName.setStyle(" -fx-background-color: #404040; -fx-border-color: #FFF ; -fx-font-weight: bold; -fx-text-fill: white;");
		
		form.add(password, 0, 2);
		password.setPromptText("Password");
		password.setMinHeight(35);
		password.setMinWidth(200);
		password.setMaxWidth(200);
		password.setStyle(" -fx-background-color: #404040;-fx-border-color: #FFF ; -fx-font-weight: bold; -fx-text-fill: white;");
			
		form.add(btnLogin, 0, 3);
		btnLogin.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-text-fill:#FFF;  -fx-font-size: 1.5em; ");
		form.setHalignment(btnLogin, HPos.CENTER);
            
		VBox vbox = new VBox(10);
		Label error = new Label("Username or password are wrong");
		error.setStyle("-fx-font-size: 20px; -fx-text-fill: red;  ");
		vbox.getChildren().add(error);
		vbox.setAlignment(Pos.CENTER);
		
		btnLogin.setOnAction(e-> {
			sceneHandler.handle(e, form,vbox);
			VBox errorForm = new VBox(15);
			errorForm.getChildren().addAll(form,vbox);
			this.setCenter(errorForm);
		});
			
		this.setCenter(form);
	}
	
	class changeScene implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event, GridPane form,VBox vbox) {
			User user = new User();
			String uName = userName.getText();
			String pWord = password.getText();
			user.setUsername(uName);
			String UserName = null;
			String PassWord = null;
			String Type = null;
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String select ="Select * from employee Where user_name='" + uName + "' and password='" + pWord + "'";
				ResultSet rs = stmt.executeQuery(select);
				while(rs.next()) {
					String FirstName = rs.getString("first_name");
					UserName = rs.getString("user_name");
					PassWord = rs.getString("password");
					Type = rs.getString("type");
				}
				if (uName.equals(UserName) && pWord.equals(PassWord) && Type.equals("Owner") ) { 
						personName = uName;
						HomePage home = new HomePage(user);
						Scene scene = new Scene(home, 1400, 800); 
					
						Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();   
						primaryStage.setScene(scene);
						primaryStage.setResizable(false);
						primaryStage.centerOnScreen();
					    primaryStage.setTitle("Home Page Owner");
					    primaryStage.show();
					    
				} else if(uName.equals(UserName) && pWord.equals(PassWord) && Type.equals("Employee")) {
					personName = uName;
					HomePage home = new HomePage(user);
					Scene scene = new Scene(home, 1400, 800); 
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();   
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.centerOnScreen();
				    primaryStage.setTitle("Home Page Employee");
				    primaryStage.show();
				} else {
					VBox errorForm = new VBox(15);
					errorForm.getChildren().addAll(form,vbox);
					
				}
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
}	
