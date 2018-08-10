package paliy.controller.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import paliy.controller.MainController;
import paliy.model.TmpImportBooks;
import paliy.util.TableUtils;

import java.io.File;
import java.util.*;


public class ImportBooksController {
    private static final ObservableList<TmpImportBooks> data = FXCollections.observableArrayList();
    final static List<String> labels = new ArrayList<>();
    public TextField txtFieldChooseFile;
    public Button btnChooseFile;
    public TextField txtFieldIdLable;
    public TextField txtFieldNameLable;
    public TextField txtFieldRestLable;
    public TextField txtFieldAgeLable;
    public TextField txtFieldWeightLable;
    public TextField txtFieldDescriptionLable;
    public TextField txtFieldPriceLable;

    public TableView<TmpImportBooks> tblImportBooks;
    public TableColumn<TmpImportBooks, Integer> idClmn;
    public TableColumn<TmpImportBooks, String> bookNameClmn;
    public TableColumn<TmpImportBooks, Integer> ageClmn;
    public TableColumn<TmpImportBooks, String> priceClmn;
    public TableColumn<TmpImportBooks, String> restClmn;
    public TableColumn<TmpImportBooks, String> weightClmn;
    public TableColumn<TmpImportBooks, String> descriptionClmn;


    public void init(MainController mainController) {

      /*  idClmn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        bookNameClmn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        ageClmn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        priceClmn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        restClmn.setCellValueFactory(cellData -> cellData.getValue().restProperty());
        weightClmn.setCellValueFactory(cellData -> cellData.getValue().weightProperty());
        descriptionClmn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
*/
        idClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, Integer>("id"));
        bookNameClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, String>("bookName"));
        ageClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, Integer>("age"));
        priceClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, String>("price"));
        restClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, String>("rest"));
        weightClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, String>("weight"));
        descriptionClmn.setCellValueFactory(new PropertyValueFactory<TmpImportBooks, String>("description"));


        data.add(new TmpImportBooks());

        tblImportBooks.setItems(data);

        tblImportBooks.getSelectionModel().setCellSelectionEnabled(true);
        tblImportBooks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableUtils.installCopyPasteHandler(tblImportBooks);
    }

    public static void setData(int n){
        for (int i = 1; i < n; i++) {
            data.add(new TmpImportBooks());
        }
    }

    private void parseExcelFile(File file) {
        Map<Integer, Map<String, String>> tableMap = new HashMap<>();
        Map<String, String > mapValue;
        try {

            Workbook wb = WorkbookFactory.create(file);
            Sheet sheet  = wb.getSheetAt(0);
            Iterator<Row> rowIter = sheet.rowIterator();
            // System.out.println(sheet.getRow(1).getCell(0));

            //   HSSFRow row;
            //   HSSFCell cell;

            Row row;
            Cell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            TmpImportBooks p;

            final String[] lab = new String[cols];
            lab[0] = "";
            for(int r = 0; r < rows; r++) {

                row = sheet.getRow(r);
                if(row != null) {
                    p = new TmpImportBooks();
                    mapValue = new HashMap<String, String>();

                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell((short)c);
                        if(cell != null && r == 0 ){
                            labels.add(cell.toString());
                            lab[c] = cell.toString();
                        }

                        if(cell != null && r >=1 ) {
                            String key = sheet.getRow(0).getCell(cell.getAddress().getColumn()).toString();
                            String val = cell.toString();
                            //System.out.println(key);
                            //System.out.println(val);
                            mapValue.put(key,val);
                        }

                    }
                    if(!mapValue.isEmpty())
                        tableMap.put(r,mapValue);
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }

        TmpImportBooks importBooks;
        for(Map.Entry<Integer, Map<String, String>> map : tableMap.entrySet()){
            int i = 0;
            importBooks = new TmpImportBooks();
            for(Map.Entry<String, String> mapVal : map.getValue().entrySet()){
                for(String label : labels){
                    if(label.equals(mapVal.getKey()))
                        importBooks.createPerson(mapVal.getKey(), mapVal.getValue());
                }
                System.out.println(mapVal.getKey());
                System.out.println(mapVal.getValue());
            }
            data.add(importBooks.getTmpImportBook());
        }
    }

    public void onChooseFile(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(btnChooseFile.getScene().getWindow());
        txtFieldChooseFile.setText(file.getAbsolutePath());
        System.out.println(file.getAbsolutePath());

    }

    public void onImport(ActionEvent actionEvent) {
        parseExcelFile(new File(txtFieldChooseFile.getText()));
    }

    public void onClearData(ActionEvent actionEvent) {
        data.clear();
        data.add(new TmpImportBooks());
    }

    public void onImportApply(ActionEvent actionEvent) {
    }
}
