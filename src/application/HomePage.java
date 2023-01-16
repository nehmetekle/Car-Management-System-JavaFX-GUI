jospackage application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HomePage extends LogIn  {
	private User tmpUser = null;
	
	public HomePage(User user) {
		this.tmpUser = user;
		createHomePage();
	}
	
	public void createHomePage() {
		this.setBackground(Background.fill(gradient));
		setHeader();
		String Type = null;
		Connection conn;
		try {
			conn = DBConnect.connect();
			Statement stmt = conn.createStatement();
			String select ="SELECT type from employee WHERE user_name ='" + this.tmpUser.getUsername() + "'" ;
			ResultSet rs = stmt.executeQuery(select);
			while(rs.next()) {
				Type = rs.getString("type");
			}
			if (Type.equals("Owner")) { 
				display(true);
			 } 
			else if(Type.equals("Employee")) {
				display(false);;
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setHeader() {
		Text txt = new Text("WELCOME " + this.tmpUser.getUsername() );
		txt.setFont(Font.font("Arial Black",30));
		this.setMargin(txt, new Insets(70));
		this.setAlignment(txt, Pos.CENTER);

	    this.setTop(txt);
	}
	
	private void display(boolean _owner) {
		Rectangle[] rec = new Rectangle[3];
		for(int i =0; i < 3; i++) {
			rec[i] = new Rectangle();
			rec[i].setHeight(380);
			rec[i].setWidth(250);
			rec[i].setOpacity(0.15);
			rec[i].setStroke(Color.TRANSPARENT);
		}
		GridPane menu = new GridPane();
		menu.setHgap(50);
		menu.setAlignment(Pos.CENTER);
		
		menu.add(rec[0], 0, 0);
		StackPane stack1 = new StackPane();
		Image Owner = new Image("C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\image\\owner.png");
		ImageView ow = new ImageView(Owner);
		ow.setFitWidth(225);
		ow.setFitHeight(225);
		stack1.getChildren().addAll(ow,rec[0]);
		Text owner = new Text("Owner");
		owner.setFont(Font.font("Arial Black",20));
		owner.setFill(Color.LIGHTGRAY);
		stack1.getChildren().add(owner);
		stack1.setAlignment(owner, Pos.BOTTOM_CENTER);
		
		menu.add(rec[1], 1, 0);
		StackPane stack2 = new StackPane();
		Image Manager = new Image("C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\image\\manager.png");
		ImageView Man = new ImageView(Manager);
		Man.setFitWidth(225);
		Man.setFitHeight(225);
		stack2.getChildren().addAll(Man,rec[1]);
		Text employee = new Text("Employee");
		employee.setFont(Font.font("Arial Black",20));
		employee.setFill(Color.LIGHTGRAY);
		stack2.getChildren().add(employee);
		stack2.setAlignment(employee, Pos.BOTTOM_CENTER);
		
		menu.add(rec[2], 2, 0);
		StackPane stack3 = new StackPane();
		Image CustServ = new Image("C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\image\\pie-chart.png");
		ImageView CS = new ImageView(CustServ);
		CS.setFitWidth(225);
		CS.setFitHeight(225);
		stack3.getChildren().addAll(CS,rec[2]);
		Text cs = new Text("Statistics");
		cs.setFont(Font.font("Arial Black",20));
		cs.setFill(Color.LIGHTGRAY);
		stack3.getChildren().add(cs);
		stack3.setAlignment(cs, Pos.BOTTOM_CENTER);
		
		HBox alignmentMenu = new HBox();
		alignmentMenu.setSpacing(100);
		alignmentMenu.getChildren().addAll(stack1,stack2,stack3);
		
		menu.getChildren().add(alignmentMenu);
		
		VBox logOut = new VBox();
		Button button = new Button();
		changeScene sceneHandler = new changeScene();
		Image LogOut = new Image("C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\image\\power.png");
		ImageView exit = new ImageView(LogOut);
		exit.setFitWidth(70);
		exit.setFitHeight(70);
	    button.setPrefSize(80, 80);
	    button.setGraphic(exit);
	    button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;  ");
	    button.setOnAction(sceneHandler);
		
	    logOut.getChildren().add(button);
	    logOut.setAlignment(Pos.CENTER);
		
		VBox displayMenu = new VBox(50);
		displayMenu.getChildren().addAll(menu,logOut);
		if (_owner) {
			rec[0].setOnMouseClicked(e->{
				ImageView loading = new ImageView(new Image("C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\image\\loading.gif"));
			    loading.setFitHeight(100);
			    loading.setFitWidth(100);
			    this.setBottom(loading);
			    this.setAlignment(loading, Pos.BOTTOM_CENTER);
			
			    Owner ownerGUI = new Owner(this.tmpUser);
				Scene scene = new Scene(ownerGUI, 1400, 800); 
				Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();   
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.centerOnScreen();
			    primaryStage.setTitle("Owner Page");
			    primaryStage.show();
			});
		} else {
			Text message = new Text("Employee can't open owner page");
			message.setFont(Font.font("Arial Black",13));
			message.setFill(Color.LIGHTGRAY);
			stack1.getChildren().add(message);
			stack1.setAlignment(message, Pos.TOP_CENTER);
			stack1.setMargin(message, new Insets(10,0,0,0));
		}
		
		rec[1].setOnMouseClicked(e->{
			ImageView loading = new ImageView(new Image("C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\image\\loading.gif"));
		    loading.setFitHeight(100);
		    loading.setFitWidth(100);
		    this.setBottom(loading);
		    this.setAlignment(loading, Pos.BOTTOM_CENTER);
		    
		    Employee employeePage = new Employee(this.tmpUser);
			Scene scene = new Scene(employeePage, 1400, 800); 
			Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();   
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
		    primaryStage.setTitle("Employeer Page");
		    primaryStage.show();
		});
		
		rec[2].setOnMouseClicked(e->{
			ImageView loading = new ImageView(new Image("C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\image\\loading.gif"));
		    loading.setFitHeight(100);
		    loading.setFitWidth(100);
		    this.setBottom(loading);
		    this.setAlignment(loading, Pos.BOTTOM_CENTER); 
		    
		    Statistics statistics = new Statistics(this.tmpUser);
			Scene scene = new Scene(statistics, 1400, 800); 
			Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();   
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
		    primaryStage.setTitle("Statistics Page");
		    primaryStage.show();
		});
		this.setCenter(displayMenu);
	}
	
	class changeScene implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			LogIn logIn = new LogIn();
			Scene scene = new Scene(logIn,750,750); 
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();     
			primaryStage.centerOnScreen();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Log In");
			primaryStage.show();
		}	
	}
}