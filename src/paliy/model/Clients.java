package paliy.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Clients {

    private IntegerProperty id;
    private StringProperty fio;
    private StringProperty email;
    private StringProperty tel;
    private StringProperty address;
    private StringProperty orders;
    private StringProperty notes;
    private StringProperty from;
    private SimpleObjectProperty<Date> added_date;


    public Clients(){
        this.id = new SimpleIntegerProperty();
        this.fio = new SimpleStringProperty();
        this.email= new SimpleStringProperty();
        this.tel = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.orders = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.from = new SimpleStringProperty();
        this.added_date = new SimpleObjectProperty<>();
    }

    public int getClientId() {
        return id.get();
    }

    public IntegerProperty clientIdProperty() {
        return id;
    }

    public void setClientId(int id) {
        this.id.set(id);
    }

    public String getClientFio() {
        return fio.get();
    }

    public StringProperty clientFioProperty() {
        return fio;
    }

    public void setClientFio(String fio) {
        this.fio.set(fio);
    }

    public String getClientEmail() {
        return email.get();
    }

    public StringProperty clientEmailProperty() {
        return email;
    }

    public void setClientEmail(String email) {
        this.email.set(email);
    }

    public String getClientTel() {
        return tel.get();
    }

    public StringProperty clientTelProperty() {
        return tel;
    }

    public void setClientTel(String tel) {
        this.tel.set(tel);
    }

    public String getClientAddress() {
        return address.get();
    }

    public StringProperty clientAddressProperty() {
        return address;
    }

    public void setClientAddress(String address) {
        this.address.set(address);
    }

    public String getClientOrders() {
        return orders.get();
    }

    public StringProperty clientOrdersProperty() {
        return orders;
    }

    public void setClientOrders(String orders) {
        this.orders.set(orders);
    }

    public String getClientFrom() {
        return from.get();
    }

    public StringProperty clientFromProperty() {
        return from;
    }

    public void setClientFrom(String from) {
        this.from.set(from);
    }

    public String getClientNotes() {
        return notes.get();
    }

    public StringProperty clientNotesProperty() {
        return notes;
    }

    public void setClientNotes(String notes) {
        this.notes.set(notes);
    }

    public Date getAddedDate() {
        return added_date.get();
    }

    public SimpleObjectProperty<Date> addedDateProperty() {
        return added_date;
    }

    public void setAddedDate(Date added_date) {
        this.added_date.set(added_date);
    }



}
