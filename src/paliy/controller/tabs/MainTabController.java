package paliy.controller.tabs;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import paliy.EditCell;
import paliy.MyIntegerStringConverter;
import paliy.controller.MainController;
import paliy.controller.dialogs.AddBookController;
import paliy.model.Book;
import paliy.model.BookDAO;
import paliy.model.Clients;
import paliy.model.ClientsDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class MainTabController {


    private MainController main;

    @FXML
    public TableView<Book> tableMainBook;

    @FXML
    public TableColumn<Book, Date> bookAddedDateClmn;
    @FXML
    public TableColumn<Book, Integer> bookWeightClmn;
    @FXML
    public TableColumn<Book, String> bookAgeClmn;
    @FXML
    public TableColumn<Book, Integer> bookIdClmn;
    @FXML
    public TableColumn<Book, String> bookNameClmn;
    @FXML
    public TableColumn<Book, String> bookTagClmn;
    @FXML
    public TableColumn<Book, Integer> bookRestClmn;
    @FXML
    public TableColumn<Book, Integer> bookPriceClmn;
    @FXML
    public TableColumn<Book, Integer> bookSalePriceClmn;
    @FXML
    public TableColumn<Book, String> bookDescripClmn;

    /*
   // Moved to Description field
   @FXML
    public TableColumn<Book, String> bookLangClmn;
    @FXML
    public TableColumn<Book, String> bookSeriesClmn;
    @FXML
    public TableColumn<Book, Integer> bookPagesClmn;
    @FXML
    public TableColumn<Book, String> bookCoverClmn;
    @FXML
    public TableColumn<Book, String> bookAuthorClmn;
    @FXML
    public TableColumn<Book, String> bookIllustClmn;
    @FXML
    public TableColumn<Book, String> bookDemensClmn;
    @FXML
    public TableColumn<Book, Integer> bookYearClmn;
*/
    @FXML
    public TableView tableMainOrder;
    @FXML
    public TableColumn orderIdClmn;
    @FXML
    public TableColumn orderBookNameClmn;
    @FXML
    public TableColumn orderBookWeightClmn;
    @FXML
    public TableColumn orderBookQuantityClmn;
    @FXML
    public TableColumn orderBookPriceClmn;
    @FXML
    public TableColumn orderCleanItem;

    @FXML
    public Label lblMainAllBooks;
    @FXML
    public Label lblMainUniqueBooks;
    @FXML
    public Label lblMainOrderedPrice;
    @FXML
    public Label lblMainOrderedWithDiscPrice;
    @FXML
    public Label lblMainSoldPrice;
    @FXML
    public TextField txtFieadMainBookSearch;
    @FXML
    public Button btnMainSearch;
    @FXML
    public Button btnMainBookAdd;
    @FXML
    public Button btnBookArchive;
    @FXML
    public Button btnMainBookRemove;
    @FXML
    public Button btnBookEdit;
    @FXML
    public Button btnMainAddToOrder;
    @FXML
    public Label lblMainOrderNumber;
    @FXML
    public TextField txtFieadMainClientFIO;
    @FXML
    public TextField txtFieadMainClientAddress;
    @FXML
    public TextField txtFieadMainClientTel;
    @FXML
    public Label lblMainOrderDate;
    @FXML
    public ChoiceBox dropDMainOrderedFrom;
    @FXML
    public ChoiceBox dropDMainSendVia;
    @FXML
    public Label lbaMainParcelWeight;
    @FXML
    public Label lbaMainParcelPrice;
    @FXML
    public Button btnMainCreateOrder;
    @FXML
    public Label lblMainBookName;
    @FXML
    public Label lblDropImgTabMainBook;
    @FXML
    public Button btnMainBookRemove2;
    @FXML
    public Button btnMainEditBookDescrip;
    @FXML
    public ImageView imgMainBookImage;
    @FXML
    public TextArea txtAreaMainBookDescrip;
    @FXML
    public ImageView imageBtnMainRefresh;
    @FXML
    public ImageView imgMainBookRemoveImg;

    public int latestBookId;

    ObservableList<Book> bookObservableList;

    AutoCompletionBinding<String> autoCompletionBinding;

    public void init(MainController mainController) {
        System.out.println("tabMain");
        main = mainController;

        Image refresh = new Image(MainTabController.class.getResourceAsStream("/images/refresh_24.png"));
        imageBtnMainRefresh.setImage(refresh);

        Image deleteImg = new Image(MainTabController.class.getResourceAsStream("/images/deleteImg.png"));
        imgMainBookRemoveImg.setImage(deleteImg);

        System.out.println(main.getClass().getCanonicalName());

        bookIdClmn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        bookNameClmn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        bookAddedDateClmn.setCellValueFactory(cellData -> cellData.getValue().addedDateProperty());
        bookTagClmn.setCellValueFactory(cellData -> cellData.getValue().tagProperty());
        bookAgeClmn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        bookWeightClmn.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
        bookDescripClmn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        bookPriceClmn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        bookSalePriceClmn.setCellValueFactory(cellData -> cellData.getValue().salePriceProperty().asObject());
        bookRestClmn.setCellValueFactory(cellData -> cellData.getValue().restProperty().asObject());


        fillMainTabTable();
        BookDAO.getObsBooksList().addListener((ListChangeListener<Book>) c -> {
            updateUniqueBookCount();
            updateAllBooksCount();
          //  updateOrderedPrice();
            updateSellPrice();
        });

        setTableEditable();
        setupInlineEdit();

        // Show first book on Preview
        updatePreview(tableMainBook.getItems().get(0));
        // Show book on Preview
        tableMainBook.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    updatePreview(row.getItem());
                    System.out.println("Book found in table and added to PreView side!");
                }
            });
            return row ;
        });

        //set current date for orders
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        lblMainOrderDate.setText(sdf.format(Calendar.getInstance().getTime()));

        // Autocompletion for FIO textField
        refreshAutoComplet();
        //Auto completed listener
        autoCompletionBinding.setOnAutoCompleted(autoCompleted -> {
            Clients client = ClientsDAO.getClientByFio(autoCompleted.getCompletion());
            if(client != null){
                txtFieadMainClientFIO.setText(client.getClientFio());
                txtFieadMainClientAddress.setText(client.getClientAddress());
                txtFieadMainClientTel.setText(client.getClientTel());
            }
            lblMainOrderDate.setText(sdf.format(Calendar.getInstance().getTime()));
            System.out.println(autoCompleted.getCompletion());
        });

        System.out.println("Main Tab Init completed!");
    }

    private void updatePreview(Book book){
        lblMainBookName.setText(book.getBookName());
        txtAreaMainBookDescrip.setText(book.getDescription());
        imageRetrievalService.restart();
        System.out.println(imageRetrievalService.getState().toString());
        try {
            Image img = BookDAO.getImageByName(book.getBookName());
            if(img != null){
                lblDropImgTabMainBook.setVisible(false);
                imgMainBookImage.setImage(img);
                imgMainBookRemoveImg.setVisible(true);
            }else {
                imgMainBookImage.imageProperty().set(null);
                lblDropImgTabMainBook.setVisible(true);
                imgMainBookRemoveImg.setVisible(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ToDo service is added but not used yet
    private final Service<Image> imageRetrievalService = new Service<Image>() {
        @Override
        protected Task<Image> createTask() {
            final String bookName = lblMainBookName.getText();

            return new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return BookDAO.getImageByName(bookName);
                }
            } ;
        }
    };

    private void refreshAutoComplet() {
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        try {
            autoCompletionBinding = TextFields.bindAutoCompletion(txtFieadMainClientFIO, ClientsDAO.getClientsFioList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupInlineEdit() {
        bookNameClmn.setCellFactory(EditCell.forTableColumn());
        bookNameClmn.setOnEditCommit(event -> {
        String oldValue = event.getOldValue();

        final String value = (event.getNewValue() != null && !event.getNewValue().equals("")) ? event.getNewValue() : oldValue;
            if(!oldValue.equals(value)) {
                Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
                book.setBookName(value);
                book.getBookId();
                try {

                    BookDAO.updateBookName(String.valueOf(book.getBookId()), value);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                tableMainBook.refresh();
            }
        });

        bookDescripClmn.setCellFactory(EditCell.forTableColumn());
        bookDescripClmn.setOnEditCommit(event -> {
        String oldValue = event.getOldValue() == null ? "" : event.getOldValue();

        final String value = (event.getNewValue() != null && !event.getNewValue().equals("")) ? event.getNewValue() : oldValue;
            if(!oldValue.equals(value)) {
                Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
                book.setDescription(value);
                book.getBookId();
                try {
                    BookDAO.updateBookDescript(String.valueOf(book.getBookId()), value);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                tableMainBook.refresh();
            }
        });


        bookRestClmn.setCellFactory(EditCell.forTableColumn(new MyIntegerStringConverter()));
        bookRestClmn.setOnEditCommit(event -> {
            Integer oldValue = event.getOldValue();

            final Integer value = (event.getNewValue() != null && event.getNewValue()!= 0 ) ? event.getNewValue() : oldValue;

            Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            book.setRest(value);
            book.getBookId();
            try {
                if(!oldValue.equals(value)){
                    BookDAO.updateBookRest(String.valueOf(book.getBookId()), String.valueOf(value));
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            tableMainBook.refresh();
        });


        bookPriceClmn.setCellFactory(EditCell.forTableColumn(new MyIntegerStringConverter()));
        bookPriceClmn.setOnEditCommit(event -> {
            Integer oldValue = event.getOldValue();

            final Integer value = (event.getNewValue() != null && event.getNewValue()!= 0 ) ? event.getNewValue() : oldValue;

            Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            book.setPrice(value);
            book.getBookId();
            try {
                if(!oldValue.equals(value)){
                    BookDAO.updateBookPrice(String.valueOf(book.getBookId()), String.valueOf(value));
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            tableMainBook.refresh();
        });


        bookSalePriceClmn.setCellFactory(EditCell.forTableColumn(new MyIntegerStringConverter()));
        bookSalePriceClmn.setOnEditCommit(event -> {
            Integer oldValue = event.getOldValue();

            final Integer value = (event.getNewValue() != null && event.getNewValue()!= 0 ) ? event.getNewValue() : oldValue;

            Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            book.setSalePrice(value);
            book.getBookId();
            try {
                if(!oldValue.equals(value)){
                    BookDAO.updateBookSalePrice(String.valueOf(book.getBookId()), String.valueOf(value));
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            tableMainBook.refresh();
        });

        bookWeightClmn.setCellFactory(EditCell.forTableColumn(new MyIntegerStringConverter()));
        bookWeightClmn.setOnEditCommit(event -> {
            Integer oldValue = event.getOldValue();

            final Integer value = (event.getNewValue() != null && event.getNewValue()!= 0 ) ? event.getNewValue() : oldValue;

            Book book = ((Book) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            book.setWeight(value);
            book.getBookId();
            try {
                if(!oldValue.equals(value)){
                    BookDAO.updateBookRest(String.valueOf(book.getBookId()), String.valueOf(value));
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            tableMainBook.refresh();
        });
    }

    private void setTableEditable() {
        tableMainBook.setEditable(true);
        // allows the individual cells to be selected
        tableMainBook.getSelectionModel().cellSelectionEnabledProperty().set(true);
// when character or numbers pressed it will start edit in editable
        // fields
            tableMainBook.setOnKeyPressed(event -> {
        if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
            editFocusedCell();
        } else if (event.getCode() == KeyCode.RIGHT ||event.getCode() == KeyCode.TAB) {
            tableMainBook.getSelectionModel().selectNext();
            event.consume();
        } else if (event.getCode() == KeyCode.LEFT) {
            // work around due to
            // TableView.getSelectionModel().selectPrevious() due to a bug
            // stopping it from working on
            // the first column in the last row of the table
            selectPrevious();
            event.consume();
        }
        //Show book item in the Preview side by Enter Clicked
        if(event.getCode() == KeyCode.ENTER){
            updatePreview(tableMainBook.getSelectionModel().getSelectedItem());
            System.out.println("Book found in table and added to PreView side By Enter Click!");
        }

      });
    }

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition <Book, ? > focusedCell = tableMainBook.focusModelProperty().get().focusedCellProperty().get();
        tableMainBook.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void selectPrevious() {
        if (tableMainBook.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from
            // right-to-left, and then wrapping to the end of the previous line
            TablePosition < Book, ? > pos = tableMainBook.getFocusModel()
                    .getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                tableMainBook.getSelectionModel().select(pos.getRow(),
                        getTableColumn(pos.getTableColumn(), -1));
            } else if (pos.getRow() < tableMainBook.getItems().size()) {
                // wrap to end of previous row
                tableMainBook.getSelectionModel().select(pos.getRow() - 1,
                        tableMainBook.getVisibleLeafColumn(tableMainBook.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = tableMainBook.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                tableMainBook.getSelectionModel().select(tableMainBook.getItems().size() - 1);
            } else if (focusIndex > 0) {
                tableMainBook.getSelectionModel().select(focusIndex - 1);

            }
        }
    }

    private TableColumn < Book, ? > getTableColumn(final TableColumn < Book, ? > column, int offset) {
        int columnIndex = tableMainBook.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return tableMainBook.getVisibleLeafColumn(newColumnIndex);

    }

    public void updateUniqueBookCount(){
        lblMainUniqueBooks.setText(String.valueOf(BookDAO.getObsBooksList().size()));
    }

    public  void updateAllBooksCount(){
        int allBooks = 0;
        for(Book book : BookDAO.getObsBooksList()){
            allBooks +=book.getRest();
        }
        lblMainAllBooks.setText(String.valueOf(allBooks));
    }

    public void updateOrderedPrice(){
        int total = 0;
        for(Book book : BookDAO.getObsBooksList()){
            total +=book.getPrice()*book.getRest();
        }
        lblMainOrderedPrice.setText(String.valueOf(total));
    }

   /* public void updateDiscPrice(){
        int total = 0;
        for(Book book : BookDAO.getObsBooksList()){
            total +=book.getPrice();
        }
        lblMainOrderedWithDiscPrice.setText(String.valueOf(total));
    }*/

    public void updateSellPrice(){
        int total = 0;
        for(Book book : BookDAO.getObsBooksList()){
            total +=book.getSalePrice() * book.getRest();
        }
        lblMainOrderedWithDiscPrice.setText(String.valueOf(total));
    }
    //Search all Books
    @FXML
    public void fillMainTabTable() {
        try {
            //Get all Books information
            BookDAO.getAllItemsFromTable();
            //Populate Books on TableView
            populateBooks(BookDAO.getObsBooksList());
        } catch (SQLException e){
            System.out.println("Error occurred while getting Books information from DB.\n" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate Books for TableView
    @FXML
    private void populateBooks (ObservableList<Book> bookData) throws ClassNotFoundException {
        //Set items to the book table
        //sort with lambda
        bookData.sort((a, b) -> Integer.compare(a.getBookId(), b.getBookId()));
        /*//sort with comparator
        Comparator<Book> comparator = Comparator.comparingInt(Book::getBookId);
        FXCollections.sort(bookData,comparator);*/
        tableMainBook.setItems(bookData);
        //set latest book id for add new book dialog
        latestBookId = bookData.get(bookData.size() - 1).getBookId();
        AddBookController.bookId = latestBookId;

    }

    //Search Book by name, or ID with # character
    private void searchBookByName () throws ClassNotFoundException, SQLException {
        try {
            //Populate Books on TableView and Display on TextArea
            populateAndShowBook(BookDAO.searchBookResult(txtFieadMainBookSearch.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
 //           resultArea.setText("Error occurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }

    //Populate Book for TableView
    @FXML
    private void populateAndShowBook(ObservableList<Book> bookList) throws ClassNotFoundException {
        if (bookList != null) {
            tableMainBook.setItems(bookList);
           // setEmpInfoToTextArea(emp);
        } else {
          //  resultArea.setText("This employee does not exist!\n");
        }
    }

    public void onBookEditDescrip(ActionEvent actionEvent) {
    }

    public void onBookRemove(ActionEvent actionEvent) {
        Book b = tableMainBook.getSelectionModel().getSelectedItem();
        String bName = "";
        String cont = "";
        boolean isSelected = false;
        if(b == null){
            bName = "Книга не обрана!";
            cont  = "Закрийте діалог та виберіть книгу для видалення.";
        }else {
            isSelected = true;
            bName = b.getBookName();
            cont = "Книга буде видалена із бази!";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Видалення книги");
        alert.setHeaderText(bName);
        alert.setContentText(cont);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && isSelected){
            try {
                BookDAO.deleteBookByName(b.getBookName());
                fillMainTabTable();
                main.updateStatus("Книга " + bName + " була видалена із бази!");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void onBookAddToOrder(ActionEvent actionEvent) {

    }

    public void onBookEdit(ActionEvent actionEvent) {
    }

    public void onBookArchive(ActionEvent actionEvent) {
    }

    public void onBookAdd(ActionEvent actionEvent) {
        Stage stage = null;
        try{
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/paliy/view/dialogs/AddBook.fxml"));
            stage.setTitle("Додати нову книгу в базу");
            stage.setMinHeight(190);
            stage.setMinWidth(255);
            //stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e){
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(AddBookController.isAdded){
                    fillMainTabTable();
                    updateOrderedPrice();
                    updateAllBooksCount();
                    updateUniqueBookCount();
                }
            }
        });
    }

    public void onBookSearch(ActionEvent actionEvent) {
        try {
            searchBookByName();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //  fillMainTabTable();
    }

    public void onSearchByEnter(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(keyEvent.getCode() == KeyCode.ENTER ){
            searchBookByName();
        }
    }

    public void onRefresh(MouseEvent mouseEvent) {
        main.updateStatus("Перезавантаження данних із таблиці ...");

        RotateTransition rt= new RotateTransition();
        rt.setNode(imageBtnMainRefresh);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setCycleCount(2);
        rt.setDuration(new Duration(700));
        rt.play();

        fillMainTabTable();
        updateOrderedPrice();
        updateSellPrice();
        updateUniqueBookCount();
        updateAllBooksCount();

        SimpleDateFormat sdf = new SimpleDateFormat("E, h:mm:ss");
        main.updateStatus("Дані про книги актуальні!  - " + sdf.format(Calendar.getInstance().getTime()));
    }

  /*  @FXML
    private ImageView imgMainBookImage;
*/
    @FXML
    void imageDragOver(DragEvent de) {
        Dragboard board = de.getDragboard();
        if (board.hasFiles()) {
            de.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void imageDropped(DragEvent de) {
        File imgFile = null;
        try {
            Dragboard board = de.getDragboard();
            List<File> files = board.getFiles();
            imgFile = files.get(0);
            Image image = new Image(new FileInputStream(imgFile));
            imgMainBookImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BookDAO.updateBookImage(lblMainBookName.getText(), imgFile);

        if(imgMainBookImage.getImage() != null){
            lblDropImgTabMainBook.setVisible(false);
        }
    }

    public void onFioEnter(ActionEvent actionEvent) {

    }

    public void onMouseClickedDeleteImg(MouseEvent mouseEvent) {
        try {
            BookDAO.deleteBookImgByName(lblMainBookName.getText());
            imgMainBookImage.imageProperty().set(null);
            lblDropImgTabMainBook.setVisible(true);
            imgMainBookRemoveImg.setVisible(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        main.updateStatus("Картинка була видалена із бази!");
    }
}
