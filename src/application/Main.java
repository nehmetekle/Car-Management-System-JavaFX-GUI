package application; 

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage primaryStage) {
		LogIn Login = new LogIn();
		Scene scene = new Scene(Login,750,750);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Log IN");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}