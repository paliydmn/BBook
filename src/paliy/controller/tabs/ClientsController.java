package paliy.controller.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import paliy.controller.MainController;
import paliy.controller.dialogs.AddClientController;
import paliy.model.Clients;
import paliy.model.ClientsDAO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientsController {

    public Label lblAddrClients;
    public Label lblFullNameClients;
    public Label lblNameClients;
    public Label lblTelClients;
    public Label lblFromClients;
    public Label lblEmailClients;
    public Button btnSearchClients;
    public TextField txtSearchClients;
    public Button btnAddClients;
    public Button btnArchivehClients;
    public Button btnDeleteClients;
    public Button btnEditClients;
    private MainController main;

    @FXML
    public TableView<Clients> tableClients;

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


    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> fioList = new ArrayList<>();


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
        refreshAutoComplet();
        //Auto completed listener


        autoCompletionBinding.setOnAutoCompleted(autoCompleted -> {
            List<Clients> clientsList = ClientsDAO.getObsClientsList().stream()
                    .filter(item -> item.getClientFio().equals(autoCompleted.getCompletion()))
                    .collect(Collectors.toList());
            tableClients.setItems(FXCollections.observableList(clientsList));

            /* if(client != null){
                txtFieadMainClientFIO.setText(client.getClientFio());
                txtFieadMainClientAddress.setText(client.getClientAddress());
                txtFieadMainClientTel.setText(client.getClientTel());
            }
            lblMainOrderDate.setText(sdf.format(Calendar.getInstance().getTime()));*/
            System.out.println(autoCompleted.getCompletion());
        });

        btnSearchClients.setOnKeyPressed(event -> {
           if(event.getCode() == KeyCode.ENTER )
               onSearch();
        });

        btnSearchClients.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                onSearch();
        });

        // Show first book on Preview
        updatePreview(tableClients.getItems().get(0));
        // Show book on Preview
        tableClients.setRowFactory(tv -> {
            TableRow<Clients> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    updatePreview(row.getItem());
                    System.out.println("Client found in table and added to PreView side!");
                }
            });
            return row ;
        });

    }

    private void updatePreview(Clients client){
        lblNameClients.setText(client.getClientFio());
        lblFullNameClients.setText(client.getClientFio());
        lblAddrClients.setText(client.getClientAddress());
        lblEmailClients.setText(client.getClientEmail());
        lblFromClients.setText(client.getClientFrom());
        lblTelClients.setText(client.getClientTel());
    }

    private void refreshAutoComplet() {
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchClients, fioList);
    }

    //Search all Clients
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

    //Populate Clients for TableView
    @FXML
    private void populateClients(ObservableList<Clients> clientsData) throws ClassNotFoundException {
        //Set items to the clients table
        tableClients.setItems(clientsData);

        // fill name list for quick search
        for(Clients fio : clientsData){
            fioList.add(fio.getClientFio());
        }
    }


    public void onSearch() {
        try {
            populateFoundClients(ClientsDAO.searchClientsResult(txtSearchClients.getText()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate Clients for TableView
    @FXML
    private void populateFoundClients(ObservableList<Clients> clientsData) throws ClassNotFoundException {
        //Set items to the clients table
        tableClients.setItems(clientsData);
    }


    public void onAddedClient(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/paliy/view/dialogs/AddClient.fxml"));
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

        if(AddClientController.isAdded){
            System.out.println("Client Added");
            fillMainTabTable();
            refreshAutoComplet();
            AddClientController.isAdded = false;
        }
    }

    public void onArchiveClient(ActionEvent actionEvent) {
    }

    public void onRemoveClient(ActionEvent actionEvent) {
    }

    public void onEditClient(ActionEvent actionEvent) {
    }
}
