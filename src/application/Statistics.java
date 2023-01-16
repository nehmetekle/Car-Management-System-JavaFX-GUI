package application;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class Statistics extends BorderPane{ 
	Stop[] stops = new Stop[] {
	         new Stop(0, Color.AQUA),
	         new Stop(1, Color.RED)
	      };
	LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	public User tmpUser = null;
	public Statistics(User user) {
		this.tmpUser = user;
		createStatictics();
	}
	
	private void createStatictics() {
		this.setBackground(Background.fill(gradient));
		setNavBar();
		setPieChart();
		setBarChart();
	} 
	public void setNavBar() {
		Pane hboxRec = new HBox();
		Rectangle rec = new Rectangle();
		rec.setHeight(120);
		rec.setWidth(130);
		rec.setOpacity(0.15);
		rec.setStroke(Color.TRANSPARENT);
		hboxRec.getChildren().add(rec);
		StackPane stack0 = new StackPane();
		Image exit = new Image("C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\exit.png");
		ImageView exitView = new ImageView(exit);
		exitView.setFitWidth(50);
		exitView.setFitHeight(50);
		Text txtExit = new Text("Exit");
		txtExit.setFont(Font.font("Arial Black", 10));
		txtExit.setFill(Color.LIGHTGRAY);
		txtExit.setTextAlignment(TextAlignment.CENTER);
		stack0.setAlignment(txtExit, Pos.BOTTOM_CENTER);
		stack0.getChildren().addAll(txtExit,exitView, rec);
		HBox txt = new HBox(20);
		txt.setAlignment(Pos.CENTER_RIGHT);
		txt.getChildren().addAll(stack0);
		
		Text text = new Text("Statistics");
		text.setStyle("-fx-font-size:35px; -fx-font-weight: bold"); 
		HBox hboxText = new HBox();
		hboxText.getChildren().add(text);
		hboxText.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox ();
		vbox.getChildren().addAll(txt,hboxText);
		
		rec.setOnMouseClicked(e -> {
			HomePage home = new HomePage(tmpUser);
			Scene scene = new Scene(home, 1400, 800);
			Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.setTitle("Home Page");
			primaryStage.show();
		});
		
		this.setTop(vbox);
	}
	
	private void setPieChart() {
		Connection c;
		int countSold = 0, countAvailable = 0;
        try {
			c = DBConnect.connect();
	        Statement stmtSold = c.createStatement();
	        String selectSold = "select count(*) from car where status ='sold'";
	        ResultSet rsSold = stmtSold.executeQuery(selectSold);
	        rsSold.next();
	        countSold = rsSold.getInt(1);
	        
	        String selectAvailable = "select count(*) from car where status ='available'";
	        Statement stmtAvailable = c.createStatement();
	        ResultSet rsAvailable = stmtAvailable.executeQuery(selectAvailable);
	        rsAvailable.next();
	        countAvailable = rsAvailable.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Sold", countSold),
                new PieChart.Data("Available", countAvailable));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Cars");
        chart.setStyle("-fx-font-weight: bold; -fx-title-font-size: 20px; ");
       
        HBox pie = new HBox();
        pie.getChildren().add(chart);
        pie.setMargin(chart, new Insets(30,100,30,0));
        
        
       this.setRight(pie);
	}

	
	private void setBarChart() {
		BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		barChart.getData().add(series);
		barChart.setTitle("Car Brand");
		series.setName("Number Of Car");
		barChart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Connection c ;
		try {
			c = DBConnect.connect();
			Statement statement = c.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT brand, COUNT(*) AS count FROM car GROUP BY brand");
			while (resultSet.next()) {
			String brand = resultSet.getString("brand");
			int count = resultSet.getInt("count");
			series.getData().add(new XYChart.Data<>(brand,count));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			HBox pie = new HBox();
			pie.getChildren().add(barChart);
			pie.setMargin(barChart, new Insets(30,0,30,100));
		this.setLeft(pie);
	}
}	
