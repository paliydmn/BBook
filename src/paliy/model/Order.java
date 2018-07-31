package paliy.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Order {
    private IntegerProperty id;
    private StringProperty client_name;
    private StringProperty books;
    private StringProperty ordered_via;
    private StringProperty send_via;
    private IntegerProperty price;
    private StringProperty status;
    private SimpleObjectProperty<Date> date;


    public Order() {
        this.id = new SimpleIntegerProperty();
        this.client_name = new SimpleStringProperty();
        this.books =  new SimpleStringProperty();
        this.ordered_via =  new SimpleStringProperty();
        this.send_via =  new SimpleStringProperty();
        this.price = new SimpleIntegerProperty();
        this.status =  new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
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

    public String getClient_name() {
        return client_name.get();
    }

    public StringProperty client_nameProperty() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name.set(client_name);
    }

    public String getBooks() {
        return books.get();
    }

    public StringProperty booksProperty() {
        return books;
    }

    public void setBooks(String books) {
        this.books.set(books);
    }

    public String getOrdered_via() {
        return ordered_via.get();
    }

    public StringProperty ordered_viaProperty() {
        return ordered_via;
    }

    public void setOrdered_via(String ordered_via) {
        this.ordered_via.set(ordered_via);
    }

    public String getSend_via() {
        return send_via.get();
    }

    public StringProperty send_viaProperty() {
        return send_via;
    }

    public void setSend_via(String send_via) {
        this.send_via.set(send_via);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }
}
