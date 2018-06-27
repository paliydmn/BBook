package paliy.controller.tabs;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import paliy.controller.MainController;

public class FindDeliveryController {
    public WebView deliveryTabWView;
    private MainController main;

    public void init(MainController mainController) {
        System.out.println("tabFindDelivery");
        main = mainController;
        System.out.println(main.getClass().getCanonicalName());

        new WViewHelper().loadUrl("https://novaposhta.ua/tracking");

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
