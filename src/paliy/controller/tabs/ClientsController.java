package paliy.controller.tabs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import paliy.controller.MainController;
import paliy.model.Clients;
import paliy.model.ClientsDAO;

import java.sql.Date;
import java.sql.SQLException;

public class ClientsController {

    private MainController main;

    @FXML
    public TableView tableClients;

    @FXML
    public TableColumn<Clients, Integer> idClientClmn;
    @FXML
    public TableColumn<Clients, String> fioClientClmn;
    @FXML
    public TableColumn<Clients, String> emailClientClmn;
    @FXML
    public TableColumn<Clients, String> telClientClmn;
    @FXML
    public TableColumn<Clients, String> addressClientClmn;
    @FXML
    public TableColumn<Clients, String> ordersClientClmn;
    @FXML
    public TableColumn<Clients, String> notesClientClmn;
    @FXML
    public TableColumn<Clients, String> fromClientClmn;
    @FXML
    public TableColumn<Clients, Date> dateClientClmn;

    public void init(MainController mainController) {
        System.out.println("tabClients Initialization");
        main = mainController;


        System.out.println(main.getClass().getCanonicalName());

        idClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty().asObject());
        fioClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientFioProperty());
        emailClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientEmailProperty());
        telClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientTelProperty());
        addressClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientAddressProperty());
        ordersClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientOrdersProperty());
        notesClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientNotesProperty());
        fromClientClmn.setCellValueFactory(cellData -> cellData.getValue().clientFromProperty());
        dateClientClmn.setCellValueFactory(cellData -> cellData.getValue().addedDateProperty());

        fillMainTabTable();
    }

    //Search all Books
    @FXML
    public void fillMainTabTable() {
        try {
            //Get all Clients information
            ClientsDAO.getAllItemsFromTable();
            //Populate Clients on TableView
            populateClients(ClientsDAO.getObsClientsList());
        } catch (SQLException e){
            System.out.println("Error occurred while getting Clients information from DB.\n" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate Books for TableView
    @FXML
    private void populateClients(ObservableList<Clients> clientsData) throws ClassNotFoundException {
        //Set items to the employeeTable
        tableClients.setItems(clientsData);
    }


}
