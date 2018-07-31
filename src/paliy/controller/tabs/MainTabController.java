package paliy.controller.tabs;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import paliy.EditCell;
import paliy.MyIntegerStringConverter;
import paliy.controller.MainController;
import paliy.controller.dialogs.AddBookController;
import paliy.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public TableView<TmpOrder> tableMainOrder;
    @FXML
    public TableColumn<TmpOrder, Integer> orderIdClmn;
    @FXML
    public TableColumn<TmpOrder, String> orderBookNameClmn;
    @FXML
    public TableColumn<TmpOrder, Integer>  orderBookWeightClmn;
    @FXML
    public TableColumn<TmpOrder, Integer> orderBookQuantityClmn;
    @FXML
    public TableColumn<TmpOrder, Integer>  orderBookPriceClmn;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

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

        tableMainBook.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Show first book on Preview
        updatePreview(tableMainBook.getItems().get(0));
        // Show book on Preview
        tableMainBook.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    updatePreview(row.getItem());
                    System.out.println("Book found in table and added to PreView side!");
                }
            });
            return row ;
        });

        //set current date for orders
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


// Table Order. Temp table.
        orderIdClmn.setCellValueFactory(cell -> cell.getValue().tmpOrderNumProperty().asObject());
        orderBookNameClmn.setCellValueFactory(cell -> cell.getValue().tmpOrderNameProperty());
        orderBookWeightClmn.setCellValueFactory(cell -> cell.getValue().tmpOrderWeightProperty().asObject());
        orderBookPriceClmn.setCellValueFactory(cell -> cell.getValue().tmpOrderPriceProperty().asObject());
        orderBookQuantityClmn.setCellValueFactory(cell -> cell.getValue().tmpOrderQuantityProperty().asObject());
        //orderBookQuantityClmn.setCellFactory(getTmpOrderCellFactory());

        tableMainOrder.setItems(orderData);

        // init drop down lists for tmp order
        dropDMainOrderedFrom.setItems(FXCollections.observableList(Arrays.asList("Instagram","Facebook", "Other")));
        dropDMainOrderedFrom.getSelectionModel().selectFirst();

        dropDMainSendVia.setItems(FXCollections.observableList(Arrays.asList("НП","УкрП", "Доставка", "Самовивіз")));
        dropDMainSendVia.getSelectionModel().selectFirst();

        Order o = OrderDAO.getLastOrder();
        if(o != null){
            lblMainOrderNumber.setText(String.valueOf(o.getId() +1));
        }else {
            lblMainOrderNumber.setText("1");
        }

        // disabled if no items in order list
        btnMainCreateOrder.setDisable(true);

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
                    BookDAO.updateBookWeight(String.valueOf(book.getBookId()), String.valueOf(value));
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
           // setEmpInfoToTextArea(book);
        } else {
          //  resultArea.setText("This book does not exist!\n");
        }
    }

    public void onBookEditDescrip(ActionEvent actionEvent) {
    }

    public void onBookRemove(ActionEvent actionEvent) {
      //  Book b = tableMainBook.getSelectionModel().getSelectedItem();
        List<Book> bList = tableMainBook.getSelectionModel().getSelectedItems();
        List<String> bNameList = new ArrayList<>();
        for(Book book : bList){
            bNameList.add(book.getBookName());
        }
        String bNames = "";
        String cont = "";
        boolean isSelected = false;
        if(bList.size() <= 0){
            bNames = "Не обрано жодної Книги!";
            cont  = "Закрийте діалог та виберіть книгу(и) для видалення.";
        }else {
            isSelected = true;
            bNames = bNameList.toString();
            if(bNameList.size() > 1)
                cont = "Книги будуть видалені із бази!";
            else
                cont = "Книга буде видалена із бази!";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Видалення книги");
        alert.setHeaderText(bNames);
        alert.setContentText(cont);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && isSelected){
            try {
                BookDAO.deleteBooksByName(bNameList);
                fillMainTabTable();
                main.updateStatus("Книга(и) " + bNames + " була видалена із бази!");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    final ObservableList<TmpOrder> orderData = FXCollections.observableArrayList();
    int orderNum = 1;

    public void onBookAddToOrder() {
         Book book = tableMainBook.getSelectionModel().getSelectedItem();

        if(orderData != null && book.getRest() >= 1){
            boolean isAvail = false;
            if(orderData.isEmpty()){
                TmpOrder itemOrder = new TmpOrder(orderNum++, book.getBookId(), book.getBookName(),book.getWeight(), 1, book.getSalePrice());
                orderData.add(itemOrder);
            }else {
                for (TmpOrder item : orderData){
                    if(item.getBookId() == book.getBookId() ){
                        item.setQuantity(item.getQuantity()+1);
                        orderData.set(orderData.indexOf(item), item);
                        isAvail = true;
                    }
                }
                if(!isAvail){
                    TmpOrder itemOrder = new TmpOrder(orderNum++, book.getBookId(), book.getBookName(),book.getWeight(), 1, book.getSalePrice());
                    orderData.add(itemOrder);
                }
            }
            updateTmpOrderPrice(orderData);
            updateTmpOrderWeight(orderData);
            book.setRest(book.getRest() - 1);
        }
        btnMainCreateOrder.setDisable(false);
    }

    private void updateTmpOrderWeight(ObservableList<TmpOrder> orderData) {
        if(orderData.isEmpty()){
            lbaMainParcelWeight.setText("0");
        }else {
            int weight = 0;
            for (TmpOrder item : orderData){
                weight += item.getWeight()*item.getQuantity();
            }
            lbaMainParcelWeight.setText(String.valueOf(weight));
        }
    }

    private void updateTmpOrderPrice(ObservableList<TmpOrder> orderData) {
        if(orderData.isEmpty()){
            lbaMainParcelPrice.setText("0");
        }else {
            int price = 0;
            for (TmpOrder item : orderData){
                price += item.getPrice()*item.getQuantity();
            }
            lbaMainParcelPrice.setText(String.valueOf(price));
        }
    }


    public void onBookArchive(ActionEvent actionEvent) {
    }

    public void onBookAdd(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/paliy/view/dialogs/AddBook.fxml"));
            stage.setTitle("Додати нову книгу в базу");
            stage.setMinHeight(190);
            stage.setMinWidth(255);
            //stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();
        }catch (IOException ignored){
        }

        if(AddBookController.isAdded){
            System.out.println("Book Added");
            fillMainTabTable();
            updateOrderedPrice();
            updateAllBooksCount();
            updateUniqueBookCount();
            AddBookController.isAdded = false;
        }
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


    public void onBookEdit(ActionEvent actionEvent) {
    }


    public void onCommitOrder() throws SQLException, ClassNotFoundException {

        Order order = new Order();
        order.setId(Integer.parseInt(lblMainOrderNumber.getText()));

        String ordBooks = "";

        for(TmpOrder item : orderData){
           int quantity =  BookDAO.getBookByID(String.valueOf(item.getBookId())).getRest() - item.getQuantity();
            BookDAO.updateBookRest(String.valueOf(item.getBookId()), String.valueOf(quantity));
           ordBooks += (item.getBook_name() + ", ");
        }
        order.setBooks(ordBooks);
        order.setClient_name(txtFieadMainClientFIO.getText());
        order.setOrdered_via(dropDMainOrderedFrom.getSelectionModel().getSelectedItem().toString());
        order.setSend_via(dropDMainSendVia.getSelectionModel().getSelectedItem().toString());
        order.setPrice(Integer.parseInt(lblMainOrderedPrice.getText()));
        //order.setDate(Date.valueOf(lblMainOrderDate.getText()));

        OrderDAO.insertOrder(order);
        orderData.clear();
        orderNum = 1;
        updateTmpOrderWeight(orderData);
        updateTmpOrderPrice(orderData);

        clearClientFields();

    }

    private void clearClientFields() {
        txtFieadMainClientTel.clear();
        txtFieadMainClientAddress.clear();
        txtFieadMainClientFIO.clear();
    }
}

/*
 public AnchorPane getAnchorPaneTemplate(){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.minWidth(35.0);
        anchorPane.minHeight(90.0);

        Button btnMinus = new Button("-");
        AnchorPane.setBottomAnchor(btnMinus,4.0);
        AnchorPane.setRightAnchor(btnMinus,2.0);
        AnchorPane.setTopAnchor(btnMinus,4.0);

        Button btnPlus = new Button("+");
        AnchorPane.setBottomAnchor(btnPlus,4.0);
        AnchorPane.setLeftAnchor(btnPlus,2.0);
        AnchorPane.setTopAnchor(btnPlus,4.0);

        TextField tf = new TextField("1");
        tf.setAlignment(Pos.BASELINE_CENTER);
        tf.maxWidth(10.0);
        tf.prefWidth(10.0);
        tf.prefHeight(10.0);
        tf.maxHeight(10);
        AnchorPane.setBottomAnchor(tf,4.0);
        AnchorPane.setLeftAnchor(tf,30.0);
        AnchorPane.setRightAnchor(tf,30.0);
        AnchorPane.setTopAnchor(tf,4.0);

        anchorPane.getChildren().addAll(btnMinus,tf,btnPlus);

        return anchorPane;
    }

Callback<TableColumn<TmpOrder, String>, TableCell<TmpOrder, String>> cellFactory = null;
     public Callback<TableColumn<TmpOrder, String>, TableCell<TmpOrder, String>> getTmpOrderCellFactory(){
        cellFactory = new Callback<TableColumn<TmpOrder, String>, TableCell<TmpOrder, String>>() {
                     @Override
                     public TableCell call(final TableColumn<TmpOrder, String> param) {
                         final TableCell<TmpOrder, String> cell = new TableCell<TmpOrder, String>() {

                             @Override
                             public void updateItem(String item, boolean empty) {
                                 super.updateItem(item, empty);
                                 AnchorPane anchorPane = getAnchorPaneTemplate();
                                 if (empty) {
                                     setGraphic(null);
                                     setText(null);
                                 } else {
                                     Button btn = (Button) anchorPane.getChildren().get(0);
                                     TextField tf = (TextField) anchorPane.getChildren().get(1);
                                     Button btn2 = (Button) anchorPane.getChildren().get(2);
                                     if(tableMainOrder.getSelectionModel().getSelectedItem() != null)
                                     tf.setText( tableMainOrder.getSelectionModel().getSelectedItem().getQuantity());
                                     btn.setOnAction(event -> {
                                         this.setFocused(true);
                                         TmpOrder tmpOrder = tableMainOrder.getSelectionModel().getSelectedItem();
                                         Book book1 = null;
                                         for(Book b : BookDAO.getObsBooksList()){
                                             if(b.getBookId() == tmpOrder.getBookId()){
                                                 book1 = b;
                                             }
                                         }
                                         if(!tf.equals("")){
                                             int quantity = Integer.parseInt(tf.getText());
                                             if (quantity >= 2 ) {
                                                 tf.setText(String.valueOf(quantity - 1));
                                                 book1.setRest(book1.getRest() + 1);
                                             }
                                         }
                                     });
                                     setGraphic(anchorPane);
                                     setText(null);

                                     btn2.setOnAction(event -> {
                                         this.setFocused(true);
                                         TmpOrder tmpOrder = tableMainOrder.getSelectionModel().getSelectedItem();
                                         Book book1 = null;
                                         for(Book b : BookDAO.getObsBooksList()){
                                             if(b.getBookId() == tmpOrder.getBookId()){
                                                 book1 = b;
                                             }
                                         }
                                         int quantity = Integer.parseInt(tf.getText());
                                         if(book1.getRest() >= 1 ){
                                             tf.setText(String.valueOf(++quantity));
                                             book1.setRest(book1.getRest() - 1);
                                             for(int i = 0; i < orderData.size(); i++){
                                                 if(tmpOrder.getBookId() == orderData.get(i).getBookId())
                                                     orderData.get(i).setQuantity(String.valueOf(quantity));
                                                     tmpOrder.setQuantity(String.valueOf(quantity));
                                             }
                                         }
                                     });
                                     setGraphic(anchorPane);
                                     setText(null);
                                 }
                             }
                         };
                         return cell;
                     }
                 };
         return cellFactory;
     }*/
