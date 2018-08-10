package paliy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TmpImportBooks {

    private IntegerProperty id;
    private StringProperty bookName;
    private StringProperty age;
    private StringProperty price;
    private StringProperty rest;
    private StringProperty weight;
    private StringProperty description;



   /* private TmpImportBooks(String fName, String lName, Integer age) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.age = new SimpleIntegerProperty(age);
    }*/

   public void createPerson(String label, String data){

        if(label.equals("№"))
            this.setId((int)Double.parseDouble(data));
        else if(label.equalsIgnoreCase("назва книги"))
            this.setBookName(data);
        else if(label.equalsIgnoreCase("вік"))
            this.setAge(data);
        else if(label.equalsIgnoreCase("ціна видавн."))
            this.setPrice(data);
        else if(label.equalsIgnoreCase("кількість"))
            this.setRest(data);
        else if(label.equalsIgnoreCase("вага, кг"))
            this.setRest(data);
        else if(label.equalsIgnoreCase("опис"))
            this.setDescription(data);
    }

    public TmpImportBooks getTmpImportBook(){
        if(!this.isEmpty())
            return this;
        return null;
    }

    public TmpImportBooks() {
        this.id = new SimpleIntegerProperty();
        this.bookName = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.price = new SimpleStringProperty();
        this.rest = new SimpleStringProperty();
        this.weight = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBookName() {
        return bookName.get();
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getAge() {
        return age.get();
    }

    public StringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getRest() {
        return rest.get();
    }

    public StringProperty restProperty() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest.set(rest);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean isEmpty(){
        return (this.getId() == 0 && this.getBookName() == null && this.getAge() == null && this.getRest() == null && this.getDescription() == null);
    }

}
