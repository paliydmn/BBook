package paliy.controller.tabs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import paliy.controller.MainController;
import paliy.model.OrderDAO;

public class FindDeliveryController {
    public WebView deliveryTabWView;
    public TextField txtFieldSearch;
    public Button btnSearch;
    public ListView listViewInvoices;
    private MainController main;

    WViewHelper webHelper;

    public void init(MainController mainController) {
        System.out.println("tabFindDelivery");
        main = mainController;
        System.out.println(main.getClass().getCanonicalName());

        webHelper = new WViewHelper();
        webHelper.loadUrl("https://novaposhta.ua/tracking");

        btnSearch.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER )
                onSearch();
        });

        btnSearch.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                onSearch();
        });

        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(OrderDAO.getInvoices());

        listViewInvoices.setItems(data);
        listViewInvoices.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    txtFieldSearch.setText(listViewInvoices.getSelectionModel().getSelectedItem().toString());
                    onSearch();
                }
            }
        });
    }

    public void onSearch() {

        String link = "https://novaposhta.ua/tracking/?cargo_number=%s&phone=";
        if(!txtFieldSearch.getText().equals("")){
            webHelper.loadUrl(String.format(link,txtFieldSearch.getText()));

        }
    }

    class WViewHelper {
        final WebEngine eng = deliveryTabWView.getEngine();
        void loadUrl(String url) {
            //tv_url.setText(url);
            eng.load(url);
            //eng.executeScript(url);
        }

        String goBack() {
            final WebHistory history = eng.getHistory();
            ObservableList<WebHistory.Entry> entryList = history.getEntries();
            int currentIndex = history.getCurrentIndex();
            //    Out("currentIndex = "+currentIndex);
            //    Out(entryList.toString().replace("],","]\n"));

            Platform.runLater(() -> history.go(-1));
            return entryList.get(currentIndex > 0 ? currentIndex - 1 : currentIndex).getUrl();
        }

        String goForward() {
            final WebHistory history = eng.getHistory();
            ObservableList<WebHistory.Entry> entryList = history.getEntries();
            int currentIndex = history.getCurrentIndex();
            //    Out("currentIndex = "+currentIndex);
            //    Out(entryList.toString().replace("],","]\n"));

            Platform.runLater(() -> history.go(1));
            return entryList.get(currentIndex < entryList.size() - 1 ? currentIndex + 1 : currentIndex).getUrl();
        }
    }
}
