package paliy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TmpImportBooks {

    private StringProperty firstName;
    private  StringProperty lastName;
    private  StringProperty sex;
    private IntegerProperty age;

   /* private TmpImportBooks(String fName, String lName, Integer age) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.age = new SimpleIntegerProperty(age);
    }*/

   public void createPerson(String label, String data){

        if(label.equals("firstName"))
            this.setFirstName(data);
        else if(label.equals("lastName"))
            this.setLastName(data);
        else if(label.equals("sex"))
            this.setSex(data);
        else if(label.equals("age"))
            this.setAge(((int)Double.parseDouble(data)));
    }

    public TmpImportBooks getPerson(){
        if(!this.isEmpty())
            return this;
        return null;
    }

    public TmpImportBooks(){
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.sex = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
    }

    public boolean isEmpty(){
        return (this.getAge() == 0 && this.getFirstName() == null && this.getLastName() == null);
    }

    public  StringProperty firstNameProperty() {
        return this.firstName;
    }

    public  String getFirstName() {
        return this.firstNameProperty().get();
    }

    public  void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public  StringProperty lastNameProperty() {
        return this.lastName;
    }

    public  String getLastName() {
        return this.lastNameProperty().get();
    }

    public  void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public  IntegerProperty ageProperty() {
        return this.age;
    }

    public  int getAge() {
        return this.ageProperty().get();
    }

    public  void setAge( int age) {
        this.age.set(age);
    }

}
