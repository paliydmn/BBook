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
import java.util.Optional;
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
    public TreeView listOrdersClientsTab;
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

        //for multi selections
        tableClients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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


        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll("Test1", "Test2", "Test3", "Test4", "Test1", "Test2", "Test3", "Test4", "Test1", "Test2", "Test3", "Test4");

        ArrayList<TreeItem> cars = new ArrayList<TreeItem>();

        TreeItem<String> ferrari = new TreeItem<String>("Ferrari");
        TreeItem<String> ferrari2 = new TreeItem<String>("Ferrari");
        TreeItem<String> ferrari3 = new TreeItem<String>("Ferrari");
      /*  TreeItem porsche = new TreeItem("Porsche");
        TreeItem ford = new TreeItem("Ford");
        TreeItem mercedes = new TreeItem("Mercedes");*/

        cars.add(ferrari);
        cars.add(ferrari2);
        cars.add(ferrari3);
  /*      cars.add(porsche);
        cars.add(ford);
        cars.add(mercedes);*/

        TreeItem<String> treeItem = new TreeItem<String>();
        treeItem.setValue("Tree Item Value");
        treeItem.setExpanded(true);
        treeItem.getChildren().addAll(ferrari,ferrari2,ferrari3);

        listOrdersClientsTab.setRoot(treeItem);
        //listOrdersClientsTab


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
        if(fioList != null){
            fioList.clear();
        }
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
        List<Clients> clients = tableClients.getSelectionModel().getSelectedItems();
        List<String> cNameList = new ArrayList<>();
        for(Clients client : clients){
            cNameList.add(client.getClientFio());
        }
        String bNames = "";
        String cont = "";
        boolean isSelected = false;
        if(clients.size() <= 0){
            bNames = "Не обрано жодного клієнта!";
            cont  = "Закрийте діалог та виберіть клієнта(ів) для видалення.";
        }else {
            isSelected = true;
            bNames = cNameList.toString();
            if(cNameList.size() > 1)
                cont = "Клієнти будуть видалені із бази!";
            else
                cont = "Клієнт буде видалений із бази!";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Видалення книги");
        alert.setHeaderText(bNames);
        alert.setContentText(cont);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && isSelected){
                ClientsDAO.deleteClientByName(cNameList);
                fillMainTabTable();
                main.updateStatus("Клієнт(и) " + bNames + " був(ли) видалені із бази!");
        }
    }

    public void onEditClient(ActionEvent actionEvent) {
    }
}
