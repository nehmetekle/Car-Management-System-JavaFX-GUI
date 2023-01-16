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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Employee extends BorderPane {
	private TextField tBrand = new TextField();
	private TextField tColor = new TextField();
	private TextField tPrice = new TextField();
	private TextField tModel = new TextField();
	private TextField tYear = new TextField();
	private TextField tSearch = new TextField();
	
	Stop[] stops = new Stop[] {
	         new Stop(0, Color.AQUA),
	         new Stop(1, Color.RED)
	      };
	LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	
	public User tmpUser = null;
	
	public Employee(User user){
		this.tmpUser = user;
		createOwnerPage();
	}
	public void createOwnerPage() {
		this.setMinWidth(1300);
		this.setBackground(Background.fill(gradient)); 
		setNavBar();
	}
	
	String[][] boxViews = {
			 {"0", "Add Car", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\employe_images\\add.png", "100"},
			 {"1", "Sell Car", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\employe_images\\remove.png", "100"},
			 {"2", "Available cars", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\employe_images\\available.png", "100"},
			 {"3", "Sold Cars", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\employe_images\\sold.png", "100"},
			 {"4", "Exit", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\owner_images\\exit.png", "100"},
			 };
	
	public void setNavBar() {
		Rectangle[] rec = new Rectangle[6];
		HBox hbox = new HBox();
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		GridPane tmpPane = null;
		
		for(int i = 0; i < boxViews.length; i++) {
			rec[i] = new Rectangle();
			rec[i].setHeight(120);
			rec[i].setWidth(130);
			rec[i].setOpacity(0.15);
			rec[i].setStroke(Color.TRANSPARENT);
			tmpPane = this.createNavBar(pane, rec[i], hbox, i, boxViews[i]);
		}
		pane.getChildren().add(hbox);
		this.setTop(tmpPane);
	}
	
	public GridPane createNavBar(GridPane pane, Rectangle rec, HBox hbox, int i, String boxView[]) {
		pane.add(rec, i, 0);
		StackPane stack0 = new StackPane();
		Image compInfoImg = new Image(boxView[2]);
		ImageView compInfoView = new ImageView(compInfoImg);
		compInfoView.setFitWidth(50);
		compInfoView.setFitHeight(50);
		stack0.getChildren().addAll(compInfoView, rec);
		Text dCI = new Text(boxView[1]);
		dCI.setFont(Font.font("Arial Black", 10));
		dCI.setFill(Color.LIGHTGRAY);
		int wrappingWidth = Integer.parseInt(boxView[3]);
		dCI.setWrappingWidth(wrappingWidth);
		dCI.setTextAlignment(TextAlignment.CENTER);
		stack0.getChildren().add(dCI);
		stack0.setAlignment(dCI, Pos.BOTTOM_CENTER);
		
		hbox.setSpacing(20);
		hbox.getChildren().add(stack0);
		
		if(i == 0) {
			rec.setOnMouseClicked(e -> {
				addCarDisplay();
			}); 
		} else if(i == 1) {
			rec.setOnMouseClicked( e-> {
				sellCar();
			});
		} else if(i == 2) {
			rec.setOnMouseClicked(e->{
				availableCar();
			});
		} else if(i == 3) {
			rec.setOnMouseClicked(e->{
				soldCar() ;
			});	
		} else if (i == 4) {
			rec.setOnMouseClicked(e -> {
				HomePage home = new HomePage(tmpUser);
				Scene scene = new Scene(home, 1400, 800);
				Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.setTitle("Home Page");
				primaryStage.centerOnScreen();
				primaryStage.show();
			});
		}
		return pane;
	}
	
	private void addCarDisplay() {
		GridPane addForm = new GridPane();
		addForm.setAlignment(Pos.TOP_CENTER);
		addForm.setHgap(20);
		addForm.setVgap(10);
		
		Label Tittle = new Label("Add New Car");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		addForm.setMargin(Tittle,new Insets(50,0,0,0));
		addForm.setHalignment(Tittle, HPos.CENTER);
		addForm.add(Tittle, 0, 0,2,1);
		
		Label Brand = new Label("Car Brand");
		Brand.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(Brand, 0, 1);
		tBrand.setPromptText("BMW");
		tBrand.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(tBrand, 1, 1);
		
		Label Color = new Label("Color");
		Color.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(Color, 0, 2);
		tColor.setPromptText("Black");
		tColor.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; -fx-prompt-font-size: 20px;");
		addForm.add(tColor, 1, 2);
		
		Label price = new Label("Price");
		price.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(price, 0, 3);
		tPrice.setPromptText("5000");
		tPrice.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addForm.add(tPrice, 1, 3);
		
		Label model = new Label("Model");
		model.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(model, 0, 4);
		tModel.setPromptText("x5");
		tModel.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addForm.add(tModel, 1, 4);
		
		Label year = new Label("Manufacture Year");
		year.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(year, 0, 5);
		tYear.setPromptText("2018");
		tYear.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addForm.add(tYear, 1, 5);
		
		Button add = new Button("Add Car");
		add.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		addForm.setHalignment(add, HPos.RIGHT);
		addForm.add(add, 1, 6, 2,1);
		
		addCar addCar = new addCar();
		add.setOnAction( e-> { 
		if ( tBrand.getText().isEmpty() || tColor.getText().isEmpty() || tPrice.getText().isEmpty() || tModel.getText().isEmpty() || tYear.getText().isEmpty()) {
			Label error = new Label("All field are required");
			error.setStyle(" -fx-font-size: 1.5em; ");
			addForm.setHalignment(error, HPos.CENTER);
			addForm.add(error, 0, 12, 2,1);
			} 
		else {
			addCar.handle(e);
			tBrand.clear();
			tColor.clear();
			tPrice.clear();
			tModel.clear();
			tYear.clear();
			}
		});
		this.setCenter(addForm);
	}
	class addCar implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String carBrand = tBrand.getText();
			String carColor = tColor.getText();
			int carPrice = Integer.parseInt(tPrice.getText());
			String carModel = tModel.getText();
			String carYear = tYear.getText();
			int count = 0;
			Connection con;
			try {
				con = DBConnect.connect();
				String select = "select count(*)  from car";
				ResultSet rs = con.createStatement().executeQuery(select);
				while(rs.next())
			        count = rs.getInt(1);
				
				String insert ="INSERT INTO car VALUES('" + count + "', '" + carBrand + "', '" + carColor + "','" + carPrice + "','" + carModel + "','" + carYear + "', 'available')";
				Statement stmt = con.createStatement();
				stmt.execute(insert);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	private TableView table; 
	private ObservableList<ObservableList> data;
	
	class searchTableCar implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String search = tSearch.getText();
			Connection c;
	        data = FXCollections.observableArrayList();
	        ResultSet rs;
	        try {
	            c = DBConnect.connect();
	            if (search.isEmpty()) {
	            	String select = "SELECT * FROM car WHERE status ='available' " ;
	            	rs = c.createStatement().executeQuery(select);
	            } else {
	            	String select = "SELECT * FROM car WHERE brand = '" + search + "' and  status ='available'" ;
	 	            rs = c.createStatement().executeQuery(select);
	            }
	            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) { 
	                final int j = i;
	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
	                col.setMinWidth(100);
	                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	                        return new SimpleStringProperty(param.getValue().get(j).toString());
	                    }
	                });
	                table.getColumns().add(col);
	            }
	            while (rs.next()) {
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	                    row.add(rs.getString(i));
	                }
	                data.add(row);
	            }
	            table.setItems(data);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}	
	}
	
	class removeFromAvailable implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			Object item = table.getItems().get(row);
			TableColumn col = pos.getTableColumn();
			String data = (String) col.getCellObservableValue(item).getValue();
			Object selectedItemInseret = table.getSelectionModel().getSelectedItem();			
		    table.getItems().remove(selectedItemInseret);
//		    table.getItems().remove(selectedItemInseret);
        	 
		    Connection c;
	        try {
	        	 c = DBConnect.connect();
	        	 String changeToSold =" UPDATE car SET status = 'sold' WHERE id_car =" + data ;
	        	 Statement stmtDel = c.createStatement();
	        	 stmtDel.execute(changeToSold);
	        }catch (Exception e) {
            e.printStackTrace();
	        }
		}
	}
	
	private void sellCar() {
		GridPane sellForm = new GridPane();
		sellForm.setAlignment(Pos.TOP_CENTER);
		sellForm.setHgap(20);
		sellForm.setVgap(10);
		
		Label Tittle = new Label("Search for a car");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		sellForm.setMargin(Tittle,new Insets(50,0,0,0));
		sellForm.setHalignment(Tittle, HPos.CENTER);
		sellForm.add(Tittle, 0, 0,2,1);
		
		tSearch.setPromptText("Search for a car");
		tSearch.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		sellForm.add(tSearch, 1, 1);
		
		Button btnSearch = new Button("Go");
		btnSearch.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		sellForm.setHalignment(btnSearch, HPos.CENTER);
		sellForm.add(btnSearch, 0, 2,2,1);
		
		Label txt = new Label("Cars");
		txt.setStyle(" -fx-text-fill: black; -fx-font-size: 2.5em; -fx-font-weight: bold; ");
		
		table = new TableView();
	    table.setMaxHeight(600);
	    table.setMaxWidth(700);
	    
	    Button btnSell = new Button("Sell Car");
	    btnSell.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
	   
	    removeFromAvailable soldCar = new removeFromAvailable();
	    btnSell.setOnAction(soldCar);
	    
	    searchTableCar sellCar = new searchTableCar();
		btnSearch.setOnAction(e-> {
			sellCar.handle(e);
			tSearch.clear();
		});
		
		VBox displayDeleteMenu = new VBox(10);
		displayDeleteMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		displayDeleteMenu.getChildren().addAll(sellForm, txt, table, btnSell);
		displayDeleteMenu.setAlignment(Pos.CENTER);
		
		this.setCenter(displayDeleteMenu);
	}
	
	public void availableCarTable() {
		Connection c;
	    data = FXCollections.observableArrayList();
	    ResultSet rs;
	        try {
	            c = DBConnect.connect();
	            String select = "SELECT * FROM car WHERE status = 'available' " ;
	            rs = c.createStatement().executeQuery(select);
	            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) { 
	                final int j = i;
	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
	                col.setMinWidth(100);
	                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	                        return new SimpleStringProperty(param.getValue().get(j).toString());
	                    }
	                });
	                table.getColumns().add(col);
	            }
	            while (rs.next()) {
	                //Iterate Row
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	                    row.add(rs.getString(i));
	                }
	                data.add(row);
	            }
	            table.setItems(data);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}	
	
	private void availableCar() {
		Label txt = new Label("Available Cars");
		txt.setStyle(" -fx-text-fill: black; -fx-font-size: 2.5em; -fx-font-weight: bold; ");
		table = new TableView();
	    table.setMaxHeight(600);
	    table.setMaxWidth(700);
	    availableCarTable();
	    
		VBox displayDeleteMenu = new VBox(10);
		displayDeleteMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		displayDeleteMenu.getChildren().addAll(txt, table);
		displayDeleteMenu.setAlignment(Pos.CENTER);
		this.setCenter(displayDeleteMenu);
	}
	
	public void soldCarTable() {
		Connection c;
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            c = DBConnect.connect();
            String select = "SELECT * FROM car WHERE status = 'sold'" ;
            rs = c.createStatement().executeQuery(select);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setMinWidth(100);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().add(col);
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
	
	private void soldCar() {
		Label txt = new Label("Sold Cars");
		txt.setStyle(" -fx-text-fill: black; -fx-font-size: 2.5em; -fx-font-weight: bold; ");
		table = new TableView();
	    table.setMaxHeight(600);
	    table.setMaxWidth(700);
	    soldCarTable();
		VBox displayDeleteMenu = new VBox(10);
		displayDeleteMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		displayDeleteMenu.getChildren().addAll(txt, table);
		displayDeleteMenu.setAlignment(Pos.CENTER);
		this.setCenter(displayDeleteMenu);
	}
}