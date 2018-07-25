package paliy.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Order {
    private IntegerProperty id;
    private StringProperty clientName;
    private StringProperty books;
    private StringProperty orderedFrom;
    private StringProperty sendVia;
    private IntegerProperty price;
    private StringProperty status;
    private SimpleObjectProperty<Date> date;


    public Order() {
        this.id = new SimpleIntegerProperty();
        this.clientName = new SimpleStringProperty();
        this.books =  new SimpleStringProperty();
        this.orderedFrom =  new SimpleStringProperty();
        this.sendVia =  new SimpleStringProperty();
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

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
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

    public String getOrderedFrom() {
        return orderedFrom.get();
    }

    public StringProperty orderedFromProperty() {
        return orderedFrom;
    }

    public void setOrderedFrom(String orderedFrom) {
        this.orderedFrom.set(orderedFrom);
    }

    public String getSendVia() {
        return sendVia.get();
    }

    public StringProperty sendViaProperty() {
        return sendVia;
    }

    public void setSendVia(String sendVia) {
        this.sendVia.set(sendVia);
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
