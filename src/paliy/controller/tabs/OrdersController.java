package paliy.controller.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import paliy.controller.MainController;
import paliy.model.Order;
import paliy.model.OrderDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersController {
    @FXML
    public TableView<Order> tableOrders;
    @FXML
    public TableColumn<Order, Integer> orderIdClmn;
    @FXML
    public TableColumn<Order, String>  clientNameClmn;
    @FXML
    public TableColumn<Order, String>  bookClmn;
    @FXML
    public TableColumn<Order, String>  orderViaClmn;
    @FXML
    public TableColumn<Order, String>  sendViaClmn;
    @FXML
    public TableColumn<Order, Integer>  priceClmn;
    @FXML
    public TableColumn<Order, String>  statusClmn;
    @FXML
    public TableColumn<Order, String> invoiceClmn;
    @FXML
    public TableColumn<Order, Date>  dateClmn;
    public TextField txtFieldSearchOrder;
    public Button btnSearchOrder;

    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> fioList = new ArrayList<>();


    private MainController main;

    public void init(MainController mainController) {
        System.out.println("Orders");
        main = mainController;
        System.out.println(main.getClass().getCanonicalName());


        orderIdClmn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        clientNameClmn.setCellValueFactory(cellData -> cellData.getValue().client_nameProperty());
        bookClmn.setCellValueFactory(cellData -> cellData.getValue().booksProperty());
        orderViaClmn.setCellValueFactory(cellData -> cellData.getValue().ordered_viaProperty());
        sendViaClmn.setCellValueFactory(cellData -> cellData.getValue().send_viaProperty());
        priceClmn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        statusClmn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        invoiceClmn.setCellValueFactory(cellData -> cellData.getValue().invoiceProperty());
        dateClmn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        fillMainTabTable();
        refreshAutoComplet();

        autoCompletionBinding.setOnAutoCompleted(autoCompleted -> {
            List<Order> clientsList = OrderDAO.getObsOrdersList().stream()
                    .filter(item -> item.getClient_name().equals(autoCompleted.getCompletion()))
                    .collect(Collectors.toList());
            tableOrders.setItems(FXCollections.observableList(clientsList));

            System.out.println(autoCompleted.getCompletion());
        });

        btnSearchOrder.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER )
                onSearch();
        });

        btnSearchOrder.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                onSearch();
        });



    }
    private void refreshAutoComplet() {
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(txtFieldSearchOrder, fioList);
    }
    public void onSearch() {
        try {
            populateFoundOrders(OrderDAO.searchOrdersResult(txtFieldSearchOrder.getText()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate Clients for TableView
    @FXML
    private void populateFoundOrders(ObservableList<Order> ordersData) throws ClassNotFoundException {
        //Set items to the clients table
        tableOrders.setItems(ordersData);

        // fill name list for quick search
        if(fioList != null){
            fioList.clear();
        }
        for(Order clientName : ordersData){
            fioList.add(clientName.getClient_name());
        }
    }


    @FXML
    public void fillMainTabTable() {
        try {
            OrderDAO.getAllItemsFromTable();
            populateFoundOrders(OrderDAO.getObsOrdersList());
        } catch (SQLException e){
            System.out.println("Error occurred while getting Books information from DB.\n" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void populateOrders(ObservableList<Order> orderData) throws ClassNotFoundException {
        tableOrders.setItems(orderData);
    }


}
