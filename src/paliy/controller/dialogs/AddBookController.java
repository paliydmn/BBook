package paliy.controller.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import paliy.model.BookDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddBookController {

    @FXML
    public TextField tfieldDialogAddBtags;
    public TextField tfieldDialogAddBorderPrice;
    public TextField tfieldDialogAddBsellPrice;
    public TextField tfieldDialogAddBname;
    public TextField tfieldDialogAddBage;
    public TextField tfieldDialogAddBweight;
    public TextField tfieldDialogAddBquantity;
    public TextArea tareaDialogAddBdescrip;

    public Label lblDialogAddBnum;
    public Label lblDialogAddBdate;

    public Button btnDialogAddBadd;
    public Button btnDialogAddBCancel;

    public static boolean isAdded = false;

    public static int bookId;
    public ImageView imgAddBookDao;
    public Label lblWarning;

    private File imgFile = null;

   /* public void setBookId(int bookId) {
        this.bookId = bookId;
    }*/

    private final ValidationSupport validationSupport = new ValidationSupport();

    @FXML
    public void initialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        lblDialogAddBdate.setText(date);

         Validator<String> numberValidator = (control, value) -> {
             boolean condition = value != null ? !value.matches("\\d+" ) : value == null;
             return ValidationResult.fromMessageIf( control, "not a number", Severity.ERROR, condition );
         };
         Validator<String> notEmptyValidator = (control, value) -> {
             boolean condition = value != null ? !value.matches("^(?=\\s*\\S).*$" ) : value == null;
             return ValidationResult.fromMessageIf( control, "not a number", Severity.ERROR, condition );
         };

        lblDialogAddBnum.setText(String.valueOf(bookId + 1));

        validationSupport.registerValidator(tfieldDialogAddBname, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldDialogAddBage, true, notEmptyValidator);
        validationSupport.registerValidator(tareaDialogAddBdescrip, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldDialogAddBage, true, notEmptyValidator);
        validationSupport.registerValidator(tfieldDialogAddBtags, true, notEmptyValidator);

        validationSupport.registerValidator( tfieldDialogAddBorderPrice, true, numberValidator );
        validationSupport.registerValidator( tfieldDialogAddBquantity, true, numberValidator );
        validationSupport.registerValidator( tfieldDialogAddBsellPrice, true, numberValidator );
        validationSupport.registerValidator( tfieldDialogAddBweight, true, numberValidator );

        validationSupport.invalidProperty().addListener((obs, wasInvalid, isNowInvalid) -> {
            if (isNowInvalid) {
                btnDialogAddBadd.setDisable(true);
                lblWarning.setVisible(true);
            } else {
                btnDialogAddBadd.setDisable(false);
                lblWarning.setVisible(false);
            }
        });
    }

    private boolean addNewBook() throws SQLException, ClassNotFoundException {
        String name = tfieldDialogAddBname.getText();
        String age = tfieldDialogAddBage.getText();
        String descrip = tareaDialogAddBdescrip.getText();
        String weight = tfieldDialogAddBweight.getText();
        String price = tfieldDialogAddBorderPrice.getText();
        String sale_price = tfieldDialogAddBsellPrice.getText();
        String rest = tfieldDialogAddBquantity.getText();
        String tags = tfieldDialogAddBtags.getText();

        if(imgFile != null) {
            return BookDAO.insertBook(name, age, descrip, weight, price, sale_price, rest, tags, imgFile);
        }
        else {
            return BookDAO.insertBook(name, age, descrip, weight, price, sale_price, rest, tags);
        }
    }

    public void onAddBook() {
        try {
            isAdded = addNewBook();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(isAdded){
            ((Stage)tfieldDialogAddBtags.getScene().getWindow()).close();
        }
    }

    public void onCancel() {
        ((Stage) btnDialogAddBCancel.getScene().getWindow()).close();
    }

    @FXML
    void imageDragOver(DragEvent de) {
        Dragboard board = de.getDragboard();
        if (board.hasFiles()) {
            de.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void imageDropped(DragEvent de) {
        try {
            Dragboard board = de.getDragboard();
            List<File> files = board.getFiles();
            imgFile = files.get(0);
            Image image = new Image(new FileInputStream(imgFile));
            imgAddBookDao.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

