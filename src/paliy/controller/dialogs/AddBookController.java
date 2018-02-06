package paliy.controller.dialogs;

import javafx.event.ActionEvent;
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
    public ImageView imgAddBookDia;

    private File imgFile = null;

   /* public void setBookId(int bookId) {
        this.bookId = bookId;
    }*/


    @FXML
    public void initialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        lblDialogAddBdate.setText(date);
        // ToDo . get correct next Book_ID from table. Temp solution here
        lblDialogAddBnum.setText(String.valueOf(bookId + 1));

    }

    public boolean addNewBook() throws SQLException, ClassNotFoundException {
        String name = null;
        String age = null;
        String descrip = null;
        String weight = null;
        String price = null;
        String sale_price = null;
        String rest = null;
        String tags = null;

        name = tfieldDialogAddBname.getText();
        age = tfieldDialogAddBage.getText();
        descrip = tareaDialogAddBdescrip.getText();
        weight = tfieldDialogAddBweight.getText();
        price = tfieldDialogAddBorderPrice.getText();
        sale_price = tfieldDialogAddBsellPrice.getText();
        rest = tfieldDialogAddBquantity.getText();
        tags = tfieldDialogAddBtags.getText();
        if(imgFile != null)
            return BookDAO.insertBook(name, age, descrip, weight, price, sale_price, rest, tags, imgFile);
        else
            return BookDAO.insertBook(name, age, descrip, weight, price, sale_price, rest, tags);

    }

    public void onAddBook(ActionEvent actionEvent) {
        try {
            isAdded = addNewBook();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
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
            imgAddBookDia.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

