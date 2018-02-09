package paliy.controller.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import paliy.model.ClientsDAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddClientController {


    public TextField tfieldAddClientFio;
    public TextField tfieldAddClientEmail;
    public TextField tfieldAddClientTel;
    public TextField tfieldAddClientAddr;
    public TextField tfieldAddClientFrom;
    public TextArea tfieldAddClientNotes;
    public Label lblAddClientNum;
    public Label lblAddClientDate;
    public Label lblWarning;
    public Button btnAddClientOk;
    public Button btnAddClientCancel;

    public static boolean isAdded = false;

    @FXML
    public void initialize() {

        ValidationSupport validationSupport = new ValidationSupport();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        lblAddClientDate.setText(date);

        Validator<String> notEmptyValidator = (control, value) -> {
            boolean condition = value != null ? !value.matches("^(?=\\s*\\S).*$") : value == null;
            return ValidationResult.fromMessageIf(control, "not a number", Severity.ERROR, condition);
        };

        validationSupport.registerValidator(tfieldAddClientFio, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldAddClientAddr, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldAddClientTel, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldAddClientEmail, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldAddClientFrom, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldAddClientNotes, true, notEmptyValidator);

        validationSupport.invalidProperty().addListener((obs, wasInvalid, isNowInvalid) -> {
            if (isNowInvalid) {
                btnAddClientOk.setDisable(true);
                lblWarning.setVisible(true);
            } else {
                btnAddClientOk.setDisable(false);
                lblWarning.setVisible(false);
            }
        });
    }
    public void onAddClient(ActionEvent actionEvent) {
        try {
            isAdded = addNewClient();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(isAdded){
            ((Stage)btnAddClientCancel.getScene().getWindow()).close();
        }
    }

    public void onCancel(ActionEvent actionEvent) {((Stage) btnAddClientCancel.getScene().getWindow()).close();
    }

    private boolean addNewClient() throws SQLException, ClassNotFoundException {
        String fio = tfieldAddClientFio.getText();
        String email = tfieldAddClientEmail.getText();
        String tel = tfieldAddClientTel.getText();
        String address = tfieldAddClientAddr.getText();
        String from = tfieldAddClientFrom.getText();
        String notes = tfieldAddClientNotes.getText();

        return ClientsDAO.insertClient(fio, email, tel, address, from, notes );
    }

}
