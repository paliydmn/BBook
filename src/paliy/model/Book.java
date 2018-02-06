package paliy.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Book {
    private IntegerProperty book_id;
    private StringProperty name;
    private StringProperty age;
    private SimpleObjectProperty<Date> added_date;
    private IntegerProperty rest;
    private IntegerProperty price;
    private IntegerProperty sale_price;
    private StringProperty description;
    private StringProperty tag;
    private IntegerProperty weight;
    //private FileInputStream imageInStream;

    /*
    // Moved to Description field
    private StringProperty cover;
    private IntegerProperty pages;
    private StringProperty dimensions;
    private StringProperty author;
    private StringProperty illustration;
    private StringProperty series;
    private IntegerProperty year;
    private StringProperty language;*/

    public Book() {
        this.book_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.rest = new SimpleIntegerProperty();
        this.price = new SimpleIntegerProperty();
        this.sale_price = new SimpleIntegerProperty();
        this.weight = new SimpleIntegerProperty();
        this.tag = new SimpleStringProperty();
        this.added_date = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty();
       // this.imageInStream = new FileInputStream()


       /*
        // Moved to Description field
        this.dimensions = new SimpleStringProperty();
        this.pages = new SimpleIntegerProperty();
        this.language = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.illustration = new SimpleStringProperty();
        this.series = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.cover = new SimpleStringProperty();*/
    }

    public int getBookId() {
        return book_id.get();
    }

    public IntegerProperty bookIdProperty() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id.set(book_id);
    }

    public String getBookName() {
        return name.get();
    }

    public StringProperty bookNameProperty() {
        return name;
    }

    public void setBookName(String book_name) {
        this.name.set(book_name);
    }

    public int getRest() {
        return rest.get();
    }

    public IntegerProperty restProperty() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest.set(rest);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice (int price) {
        this.price.set(price);
    }

    public int getSalePrice() {
        return sale_price.get();
    }

    public IntegerProperty salePriceProperty() {
        return sale_price;
    }

    public void setSalePrice(int salePrice) {
        this.sale_price.set(salePrice);
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
    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }



    public String getTag() {
        return tag.get();
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag.set(tag);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }



/*
    // Moved to Description field
    public String getLanguage() {
        return language.get();
    }

    public StringProperty languageProperty() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getIllustration() {
        return illustration.get();
    }

    public StringProperty illustrationProperty() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration.set(illustration);
    }

    public String getSeries() {
        return series.get();
    }

    public StringProperty seriesProperty() {
        return series;
    }

    public void setSeries(String series) {
        this.series.set(series);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getCover() {
        return cover.get();
    }

    public StringProperty coverProperty() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover.set(cover);
    }

    public int getPages() {
        return pages.get();
    }

    public IntegerProperty pagesProperty() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.set(pages);
    }
    public String getDimensions() {
        return dimensions.get();
    }

    public StringProperty dimensionsProperty() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions.set(dimensions);
    }*/
}
