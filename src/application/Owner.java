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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class Owner extends BorderPane {
	private Label CompanyName = new Label("961NEPH");
	private Label CompanyAddress = new Label("Beirut");
	private Label CompanyOwnerFullName = new Label("Joseph Tekle");
	private Label CompanyTelephone = new Label("+961 71/517 611");
	private Label userName = new Label("User Name");
	private TextField fName = new TextField();
	private TextField lName = new TextField();
	private TextField tUserName = new TextField();
	private TextField tPassWord = new TextField();
	private DatePicker d = new DatePicker();
	private TextField tSalary = new TextField();
	private String emp_gender[] = { "Male", "Female"};
	private ComboBox<String> combo_box_gendar = new ComboBox<String>(FXCollections.observableArrayList(emp_gender));
	private String emp_type[] = { "Employee", "Owner"};
	private ComboBox<String> combo_box_type = new ComboBox<String>(FXCollections.observableArrayList(emp_type));
	private TextField tPhoneNumber = new TextField();
	private TextField tAddress = new TextField();
	private TextField deleteUserName = new TextField();
	private TextField upadteUserNamePass = new TextField();
	private TextField updatePassWord = new TextField();
	private TextField upadteUserNameSalary = new TextField();
	private TextField updateSalary = new TextField();
	private TextField upadteUserNameAddress = new TextField();
	private TextField upadteAddress = new TextField();
	private GridPane subNavPane = new GridPane();
	
	Stop[] stops = new Stop[] {
	         new Stop(0, Color.AQUA),
	         new Stop(1, Color.RED)
	      };
	LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	
	public User tmpUser = null;
	
	public Owner(User user){
		this.tmpUser = user;
		createOwnerPage();
	}
	
	public void createOwnerPage() {
		this.setBackground(Background.fill(gradient)); 
		setNavBar();
	}
	
	String[][] boxViews = {
			 {"0", "Display Company Informations", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\owner_images\\company_info.png", "100"},
			 {"1", "Add Employee", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\owner_images\\add.png", "100"},
			 {"2", "Delete Employee", "C:\\\\Users\\\\nehme\\\\eclipse-workspace-2022\\\\Final961NEPH\\src\\owner_images\\delete.png", "100"},
			 {"3", "Edit Employee", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\edit.png", "100"},
			 {"4", "Show Employees", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\employees.png", "100"},
			 {"5", "Car Info", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\car_info.png", "100"},
			 {"6", "Exit", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\exit.png", "100"},
			 };
	
	String[][] subBoxViews = {
			 {"0", "Change Employe Password", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\src\\owner_images\\age.png", "120"},
			 {"1", "Change Employe Salary", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\\\src\\\\owner_images\\\\salary.png", "100"},
			 {"2", "Change Employe Addres", "C:\\Users\\nehme\\eclipse-workspace-2022\\Final961NEPH\\\\src\\\\owner_images\\\\emp_address.png", "100"},
			 };
	
	public void setNavBar() {
		Rectangle[] rec = new Rectangle[7];
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
				DisplayCompanyInformation();
			}); 
		} else if(i == 1) {
			rec.setOnMouseClicked( e-> {
				addEmployeeDisplay() ;
			});
		} else if(i == 2) {
			rec.setOnMouseClicked(e->{
				deleteEmployee();
			});
		} else if(i == 3) {
			rec.setOnMouseClicked(e->{
				setSubNavBar() ;
			});
		} else if(i == 4) {
			rec.setOnMouseClicked(e->{
				displayTableViewShowEmployee();
			});
		} else if(i == 5) {
			rec.setOnMouseClicked(e->{
				displayTableViewCar();
			});
		} else if (i == 6) {
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
		}
		return pane;
	}
	
	public void setSubNavBar() {
		Rectangle[] Subrec = new Rectangle[5];
		HBox stackEditMenu = new HBox();
		subNavPane.setAlignment(Pos.TOP_CENTER);	
		GridPane tmpPane = null;
		subNavPane.getChildren().clear();
		for(int i = 0; i < subBoxViews.length; i++) {
			Subrec[i] = new Rectangle();
			Subrec[i].setHeight(120);
			Subrec[i].setWidth(130);
			Subrec[i].setOpacity(0.15);
			Subrec[i].setStroke(Color.TRANSPARENT);
			tmpPane = this.createSubNavBar(subNavPane, Subrec[i], stackEditMenu, i, subBoxViews[i]);
		}
		subNavPane.getChildren().add(stackEditMenu);
		this.setCenter(subNavPane);
	}
	
	public GridPane createSubNavBar(GridPane pane, Rectangle Subrec, HBox hbox, int i, String subBoxViews[]) {
		pane.add(Subrec, i, 0);
		StackPane stack0 = new StackPane();
		Image compInfoImg = new Image(subBoxViews[2]);
		ImageView compInfoView = new ImageView(compInfoImg);
		compInfoView.setFitWidth(50);
		compInfoView.setFitHeight(50);
		stack0.getChildren().addAll(compInfoView, Subrec);
		Text dCI = new Text(subBoxViews[1]);
		dCI.setFont(Font.font("Arial Black", 10));
		dCI.setFill(Color.LIGHTGRAY);
		int wrappingWidth = Integer.parseInt(subBoxViews[3]);
		dCI.setWrappingWidth(wrappingWidth);
		dCI.setTextAlignment(TextAlignment.CENTER);
		stack0.getChildren().add(dCI);
		stack0.setAlignment(dCI, Pos.BOTTOM_CENTER);
		
		hbox.setSpacing(20);
		hbox.getChildren().add(stack0);
		if ( i == 0) {
			Subrec.setOnMouseClicked( e-> {
				changeEmpPassWord();
			});
		} else if ( i == 1) {
			Subrec.setOnMouseClicked( e-> {
				changeEmpSalary();
			});
		} else if ( i == 2) {
			Subrec.setOnMouseClicked( e-> {
				changeEmpAddress();
			});
		}
		return pane;
	}

	private void DisplayCompanyInformation() {
		GridPane form = new GridPane();
		form.setAlignment(Pos.BASELINE_CENTER);
		
		form.setVgap(20);
		form.setHgap(15);
		Text title = new Text("Company Information");
		title.setFont(Font.font("Arial Black", FontWeight.BOLD ,35));
		form.add(title, 0, 0, 2, 1);
		form.setAlignment(Pos.TOP_CENTER);
		
		Label Label0 = new Label("Company Name: ");
		form.add(Label0, 0, 1);
		Label0.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		CompanyName.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		form.add(CompanyName, 1, 1);
		
		Label Label1 = new Label("Company Address: ");
		Label1.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		CompanyAddress.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		form.add(Label1, 0, 2);
		form.add(CompanyAddress, 1, 2);
		
		Label Label2 = new Label("Company Phone Number: ");
		Label2.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		CompanyTelephone.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		form.add(Label2, 0, 3);
		form.add(CompanyTelephone,1, 3);
		
		Label Label3 = new Label("Owner Full Name: ");
		Label3.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		CompanyOwnerFullName.setFont(Font.font("Arial Black", FontWeight.THIN ,20));
		form.add(Label3, 0, 4);
		form.add(CompanyOwnerFullName, 1, 4);
		
		form.setPadding(new Insets(100, 0, 0, 75));
		this.setCenter(form);
	}
	
	private void addEmployeeDisplay() {
		GridPane addForm = new GridPane();
		addForm.setAlignment(Pos.TOP_CENTER);
		addForm.setHgap(20);
		addForm.setVgap(10);
		
		Label Tittle = new Label("Add New Employee");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		addForm.setMargin(Tittle,new Insets(50,0,0,0));
		addForm.setHalignment(Tittle, HPos.CENTER);
		addForm.add(Tittle, 0, 0,2,1);
		
		Label FirstName = new Label("First Name");
		FirstName.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(FirstName, 0, 1);
		fName.setPromptText("Joseph");
		fName.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(fName, 1, 1);
		
		Label LastName = new Label("Last Name");
		LastName.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(LastName, 0, 2);
		lName.setPromptText("Tekle");
		lName.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; -fx-prompt-font-size: 20px;");
		addForm.add(lName, 1, 2);
		
		userName.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(userName, 0, 3);
		tUserName.setPromptText("jTek13");
		tUserName.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addForm.add(tUserName, 1, 3);
		
		Label password = new Label("Password");
		password.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(password, 0, 4);
		tPassWord.setPromptText("jTek13");
		tPassWord.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addForm.add(tPassWord, 1, 4);
		
		Label type = new Label("Employee Type");
		type.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(type, 0, 5);
		combo_box_type.setPromptText("......");
		combo_box_type.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(combo_box_type, 1, 5);
		
		Label dob = new Label("Date of Birth");
		dob.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(dob, 0, 6);
		d.setPromptText("13/11/2000");
		d.setStyle(" -fx-background-color: transparent;");
		addForm.add(d, 1, 6);
		
		Label salary = new Label("Salary");
		salary.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(salary, 0, 7);
		
		tSalary.setPromptText("2000");
		tSalary.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(tSalary, 1, 7);
		
		Label gendar = new Label("Gendar");
		gendar.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(gendar, 0, 8);
		combo_box_gendar.setPromptText(".....");
		combo_box_gendar.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(combo_box_gendar, 1, 8);
		
		Label phoneNumber = new Label("Phone Number");
		phoneNumber.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(phoneNumber, 0, 9);
		tPhoneNumber.setPromptText("03123456");
		tPhoneNumber.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(tPhoneNumber, 1, 9);
		
		Label address = new Label("Address");
		address.setStyle(" -fx-font-size: 1.5em; ");
		addForm.add(address, 0, 10);
		tAddress.setPromptText("Beirut");
		tAddress.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold;");
		addForm.add(tAddress, 1, 10);
		
		Button add = new Button("Add Employee");
		add.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		addForm.setHalignment(add, HPos.RIGHT);
		addForm.add(add, 1, 11, 2,1);
		
		addEmp addEmp = new addEmp();
		add.setOnAction( e-> { 
			LocalDate date = d.getValue();
		if ( fName.getText().isEmpty() || lName.getText().isEmpty() || tPassWord.getText().isEmpty() 
				|| tUserName.getText().isEmpty() || combo_box_type.getValue().isEmpty() 
				|| tPhoneNumber.getText().isEmpty() || date == null
				|| tAddress.getText().isEmpty() || tSalary.getText().isEmpty() 
				|| combo_box_gendar.getValue().isEmpty() ) {
			Label error = new Label("All field are required");
			error.setStyle(" -fx-font-size: 1.5em; ");
			addForm.setHalignment(error, HPos.CENTER);
			addForm.add(error, 0, 12, 2,1);
			} 
		else {
			addEmp.handle(e);
			fName.clear();
			lName.clear();
			tUserName.clear();
			tPassWord.clear();
			combo_box_type.getEditor().clear();
			d.getEditor().clear();
			tSalary.clear();
			combo_box_gendar.getEditor().clear();
			tPhoneNumber.clear();
			tAddress.clear();
			}
		});
		this.setCenter(addForm);
	}
	
	class addEmp implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String first_name = fName.getText();
			String last_name = lName.getText();
			String user_name = tUserName.getText();
			String passWord = tPassWord.getText();
			String pattern = "yyyy-MM-dd";
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
			String dateOfBirth = d.getValue().format(dateFormatter);
			String salary = tSalary.getText();
			String gender = combo_box_gendar.getValue();
			String type = combo_box_type.getValue();
			String phone_number =tPhoneNumber.getText();
			String address =tAddress.getText();
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String insert ="INSERT INTO employee VALUES('" + user_name + "', '" + first_name + "','" + last_name + "','" + passWord + "','" + type + "', '" + dateOfBirth + "','" + salary + "', '" + gender + "','" + phone_number + "','" + address + "')";
				stmt.execute(insert);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	private void deleteEmployee() {
		GridPane DeleteForm = new GridPane();
		DeleteForm.setAlignment(Pos.TOP_CENTER);
		DeleteForm.setHgap(20);
		DeleteForm.setVgap(10);
		
		Label Tittle = new Label("Delete Employee");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		DeleteForm.setMargin(Tittle,new Insets(50,0,0,0));
		DeleteForm.setHalignment(Tittle, HPos.CENTER);
		DeleteForm.add(Tittle, 0, 0,2,1);
		
		userName.setStyle(" -fx-font-size: 1.5em; ");
		DeleteForm.add(userName, 0, 1);
		deleteUserName.setPromptText("jTek13");
		deleteUserName.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		DeleteForm.add(deleteUserName, 1, 1);
		
		Button delete = new Button("Delete");
		delete.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		DeleteForm.setHalignment(delete, HPos.RIGHT);
		DeleteForm.add(delete, 1, 2);
		
		deleteEmp delEmp =new deleteEmp();
		delete.setOnAction( e-> {
			delEmp.handle(e);
			deleteUserName.clear();
			
		});
		
		VBox displayDeleteMenu = new VBox();
		displayDeleteMenu.getChildren().add(DeleteForm);
		
		this.setCenter(displayDeleteMenu);
	}
	
	class deleteEmp implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			String userName = deleteUserName.getText();
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String delte = "DELETE FROM employee WHERE USER_NAME = '" + userName + "'";
				stmt.execute(delte);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	private void changeEmpPassWord() {
		GridPane passWordForm = new GridPane();
		passWordForm.setAlignment(Pos.TOP_CENTER);
		passWordForm.setHgap(20);
		passWordForm.setVgap(10);
		
		Label Tittle = new Label("Change Employee Password");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		passWordForm.setMargin(Tittle,new Insets(50,0,0,0));
		passWordForm.setHalignment(Tittle, HPos.CENTER);
		passWordForm.add(Tittle, 0, 0,2,1);
		
		userName.setStyle(" -fx-font-size: 1.5em; ");
		passWordForm.add(userName, 0, 1);
		upadteUserNamePass.setPromptText("jTek13");
		upadteUserNamePass.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		passWordForm.add(upadteUserNamePass, 1, 1);
		
		Label pass = new Label("Password");
		pass.setStyle(" -fx-font-size: 1.5em; ");
		passWordForm.add(pass, 0, 2);
		updatePassWord.setPromptText("jTek13");
		updatePassWord.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		passWordForm.add(updatePassWord, 1, 2);
		
		Button updatePass = new Button("Update");
		updatePass.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		passWordForm.setHalignment(updatePass, HPos.RIGHT);
		passWordForm.add(updatePass, 1, 3);
		
		changePassWord changePass =new changePassWord();
		updatePass.setOnAction( e -> {
			changePass.handle(e);
			upadteUserNamePass.clear();
			updatePassWord.clear();
		});
		
		VBox displayPasswordMenu = new VBox();
		displayPasswordMenu.getChildren().addAll(subNavPane,passWordForm);
		
		this.setCenter(displayPasswordMenu);
	}
	
	class changePassWord implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String passsword = updatePassWord.getText();
			String userName = upadteUserNamePass.getText();
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String updatePass = "UPDATE employee SET password ='" + passsword + "' WHERE user_name ='" + userName + "'";
				stmt.execute(updatePass);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	private void changeEmpSalary() {
		GridPane salaryForm = new GridPane();
		salaryForm.setAlignment(Pos.TOP_CENTER);
		salaryForm.setHgap(20);
		salaryForm.setVgap(10);
		
		Label Tittle = new Label("Change Employee Salary");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		salaryForm.setMargin(Tittle,new Insets(50,0,0,0));
		salaryForm.setHalignment(Tittle, HPos.CENTER);
		salaryForm.add(Tittle, 0, 0,2,1);
		
		userName.setStyle(" -fx-font-size: 1.5em; ");
		salaryForm.add(userName, 0, 1);
		upadteUserNameSalary.setPromptText("anthony");
		upadteUserNameSalary.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		salaryForm.add(upadteUserNameSalary, 1, 1);
		
		Label salary = new Label("Salary");
		salary.setStyle(" -fx-font-size: 1.5em; ");
		salaryForm.add(salary, 0, 2);
		updateSalary.setPromptText("2500");
		updateSalary.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		salaryForm.add(updateSalary, 1, 2);
		
		Button updateSal = new Button("Update");
		updateSal.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		salaryForm.setHalignment(updateSal, HPos.RIGHT);
		salaryForm.add(updateSal, 1, 3);
		
		changeSalary changeSal =new changeSalary();
		updateSal.setOnAction( e-> {
			changeSal.handle(e);
			upadteUserNameSalary.clear();
			updateSalary.clear();	
		});
		
		VBox displaySalaryMenu = new VBox();
		displaySalaryMenu.getChildren().addAll(subNavPane,salaryForm);
		
		this.setCenter(displaySalaryMenu);
	}
	
	class changeSalary implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String salary = updateSalary.getText();
			String userName = upadteUserNameSalary.getText();
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String update = "UPDATE employee SET salary ='" + salary + "' WHERE user_name ='" + userName + "'";
				stmt.execute(update);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	private void changeEmpAddress() {
		GridPane addressForm = new GridPane();
		addressForm.setAlignment(Pos.TOP_CENTER);
		addressForm.setHgap(20);
		addressForm.setVgap(10);
		
		Label Tittle = new Label("Change Employee Salary");
		Tittle.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; ");
		addressForm.setMargin(Tittle,new Insets(50,0,0,0));
		addressForm.setHalignment(Tittle, HPos.CENTER);
		addressForm.add(Tittle, 0, 0,2,1);
		
		userName.setStyle(" -fx-font-size: 1.5em; ");
		addressForm.add(userName, 0, 1);
		upadteUserNameAddress.setPromptText("anthony");
		upadteUserNameAddress.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addressForm.add(upadteUserNameAddress, 1, 1);
		
		Label address = new Label("Address");
		address.setStyle(" -fx-font-size: 1.5em; ");
		addressForm.add(address, 0, 2);
		upadteAddress.setPromptText("Daabda");
		upadteAddress.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-font-weight: bold; ");
		addressForm.add(upadteAddress, 1, 2);
		
		Button updateAddress = new Button("Update");
		updateAddress.setStyle(" -fx-background-color: transparent; -fx-border-color: white; -fx-cursor: hand; -fx-border-radius: 30; ");
		addressForm.setHalignment(updateAddress, HPos.RIGHT);
		addressForm.add(updateAddress, 1, 3);
		
		changeEmpAddress changeAddress =new changeEmpAddress();
		updateAddress.setOnAction(changeAddress);
		updateAddress.setOnAction(e->{
			changeAddress.handle(e);
			upadteUserNameAddress.clear();
			upadteAddress.clear();
		});
		
		VBox displaySalaryMenu = new VBox();
		displaySalaryMenu.getChildren().addAll(subNavPane,addressForm);
		
		this.setCenter(displaySalaryMenu);
	}
	
	class changeEmpAddress implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String address = upadteAddress.getText();
			String userName = upadteUserNameAddress.getText();
			Connection con;
			try {
				con = DBConnect.connect();
				Statement stmt = con.createStatement();
				String update = "UPDATE employee SET address ='" + address + "' WHERE user_name ='" + userName + "'";
				stmt.execute(update);
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	private TableView table;
	private ObservableList<ObservableList> data;
	
	public void tableViewShowEmployee() {
		Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = DBConnect.connect();
            String select = "SELECT * FROM employee WHERE type ='Employee'";
            ResultSet rs = c.createStatement().executeQuery(select);
            
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setMinWidth(100);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
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
        table.setEditable(false);
	}
	
    public void displayTableViewShowEmployee(){
    	Label txt = new Label("All Employees");
    	txt.setStyle(" -fx-text-fill: black; -fx-font-size: 2.5em; -fx-font-weight: bold; ");
    	
        table = new TableView();
        table.setMaxHeight(600);
        table.setMaxWidth(1000);
        tableViewShowEmployee();
        
        VBox scene = new VBox(10);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        scene.getChildren().addAll(txt,table);
        scene.setAlignment(Pos.CENTER);
        
        this.setCenter(scene);
    }
    
    public void tableViewCar() {
		Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = DBConnect.connect();
            String select = "SELECT * FROM car ";
            ResultSet rs = c.createStatement().executeQuery(select);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) { 
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setMinWidth(100);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
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
        table.setEditable(false);
	}
	
    public void displayTableViewCar(){
    	Label txt = new Label("All Car");
    	txt.setStyle(" -fx-text-fill: black; -fx-font-size: 2.5em; -fx-font-weight: bold; ");
    	
        table = new TableView();
        table.setMaxHeight(600);
        table.setMaxWidth(700);
        tableViewCar();
        
        VBox scene = new VBox(10);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        scene.getChildren().addAll(txt,table);
        scene.setAlignment(Pos.CENTER);
        
        this.setCenter(scene);
    }
}