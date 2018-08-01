package paliy.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import paliy.controller.tabs.*;

public class MainController {


    @FXML
    public Label lbl_status;
    public TabPane mainTabPane;

    @FXML
    private MainTabController mainTabController;
    @FXML
    private OrdersController ordersController;
    @FXML
    private FindDeliveryController findDeliveryController;
    @FXML
    private BooksController booksController;
    @FXML
    private ClientsController clientsController;

    @FXML
    public void initialize(){
        System.out.println("Initialization!");
        updateStatus("Запуск ...");



        try {
            mainTabController.init(this);
            updateStatus("Готово!");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Can't connect to database server!");
            alert.setContentText("Please try to restart application or write about issue to paliydmn@gmail.com");

            alert.showAndWait();

            lbl_status.setText("No connection to Database server! Please, restart application or write about issue to paliydmn@gmail.com");
            lbl_status.setTextFill(Color.RED);
            lbl_status.setStyle("-fx-font: 16 arial;");
        }

        //moved to tab listener
        //findDeliveryController.init(this);

        ordersController.init(this);
        booksController.init(this);
        //clientsController.init(this);

/*

        mainTabController.fillMainTabTable();
        mainTabController.updateAllBooksCount();
        mainTabController.updateUniqueBookCount();
        mainTabController.updateSellPrice();
        mainTabController.updateOrderedPrice();
*/

        MainController initObject = this;


        mainTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                           if(t1.getText().equals("Головна")){
                               mainTabController.init(initObject);

                               mainTabController.fillMainTabTable();
                               mainTabController.updateAllBooksCount();
                               mainTabController.updateUniqueBookCount();
                               mainTabController.updateSellPrice();
                               mainTabController.updateOrderedPrice();
                           }
                           if(t1.getText().equals("Клієнти")){
                               clientsController.init(initObject);
                           }
                           if(t1.getText().equals("Замовлення")){
                               ordersController.init(initObject);
                           }
                           if(t1.getText().equals("Відслідкувати")){
                               findDeliveryController.init(initObject);
                           }
                    }
                }
        );
        System.out.println(mainTabPane.getSelectionModel().getSelectedItem());


    }

    public void updateStatus(String status){
        lbl_status.setText(status);
    }
}
