package paliy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import paliy.controller.tabs.*;

public class MainController {


    @FXML
    public Label lbl_status;

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

        findDeliveryController.init(this);
        ordersController.init(this);
        mainTabController.init(this);
        booksController.init(this);
        clientsController.init(this);


        mainTabController.fillMainTabTable();
        mainTabController.updateAllBooksCount();
        mainTabController.updateUniqueBookCount();
        mainTabController.updateSellPrice();
        mainTabController.updateOrderedPrice();

        updateStatus("Готово!");
    }

    public void updateStatus(String status){
        lbl_status.setText(status);
    }
}
