package paliy.controller.tabs;

import paliy.controller.MainController;

public class FindDeliveryController {
    private MainController main;

    public void init(MainController mainController) {
        System.out.println("tabFindDelivery");
        main = mainController;
        System.out.println(main.getClass().getCanonicalName());
    }
}
