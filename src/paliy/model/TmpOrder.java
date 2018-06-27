package paliy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TmpOrder {

    private final SimpleIntegerProperty _num;
    private final SimpleIntegerProperty _book_id;
    private final SimpleStringProperty _book_name;
    private final SimpleIntegerProperty _weight;
    private final SimpleIntegerProperty _quantity;
    private final SimpleIntegerProperty _price;

    public TmpOrder(Integer _num, Integer _book_id, String _book_name, Integer _weight, Integer _quantity, Integer _price) {
        this._num = new SimpleIntegerProperty(_num);
        this._book_id= new SimpleIntegerProperty(_book_id);
        this._book_name = new SimpleStringProperty(_book_name);
        this._weight = new SimpleIntegerProperty(_weight);
        this._quantity = new SimpleIntegerProperty(_quantity);
        this._price = new SimpleIntegerProperty(_price);

    }

    public Integer getNum() {
        return _num.get();
    }
    public IntegerProperty tmpOrderNumProperty() {
        return _num;
    }
    public void setNum(Integer num) {
        _num.set(num);
    }

    public Integer getBookId() {
        return _book_id.get();
    }
    public IntegerProperty tmpOrderBookIdProperty() {
        return _book_id;
    }
    public void setBookId(Integer book_id) {
        _book_id.set(book_id);
    }

    public Integer getWeight() {
        return _weight.get();
    }
    public IntegerProperty tmpOrderWeightProperty() {
        return _weight;
    }
    public void setWeight(Integer weight) {
        _weight.set(weight);
    }

    public Integer getQuantity() {
        return _quantity.get();
    }
    public SimpleIntegerProperty tmpOrderQuantityProperty() {
        return _quantity;
    }
    public void setQuantity(Integer quantity) {
        _quantity.set(quantity);
    }

    public Integer getPrice() {
        return _price.get();
    }
    public IntegerProperty tmpOrderPriceProperty() {
        return _price;
    }
    public void setPrice(Integer price) {
        _price.set(price);
    }

    public String getBook_name() {
        return _book_name.get();
    }
    public StringProperty tmpOrderNameProperty() {
        return _book_name;
    }
    public void set_book_name(String book_name) {
        _book_name.set(book_name);
    }

}
