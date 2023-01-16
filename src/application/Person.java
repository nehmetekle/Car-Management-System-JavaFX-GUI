package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	 
	private StringProperty fName;
    private StringProperty lName;
    private StringProperty password;

    public void ShowData(String fName, String lName, String password) {
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
        this.password = new SimpleStringProperty(password);
    }

    public String getId() {
        return fName.get();
    }

    public String getuname() {
        return lName.get();
    }

    public String getpass() {
        return lName.get();
    }

    public void setId(String value) {
    	fName.setValue(value);
    }

    public void setUname(String value) {
    	lName.setValue(value);
    }

    public void setPass(String value) {
    	password.setValue(value);
    }

    public StringProperty idproper(){
        return fName;
    }

    public StringProperty unameproper(){
        return lName;
    }

    public StringProperty passproper(){
        return password;
    }

//    public String getFirstName() {
//        return firstName.get();
//    }
//
//    public void setFirstName(String fName) {
//        firstName.set(fName);
//    }
//
//    public String getLastName() {
//        return lastName.get();
//    }
//
//    public void setLastName(String fName) {
//        lastName.set(fName);
//    }
//
//    public String getPassword() {
//        return password.get();
//    }
//
//    public void setPassword(String fName) {
//    	password.set(fName);
//    }
}
