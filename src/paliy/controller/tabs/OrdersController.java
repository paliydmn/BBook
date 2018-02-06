package paliy.controller.tabs;

import paliy.controller.MainController;

public class OrdersController {
    private MainController main;

    public void init(MainController mainController) {
        System.out.println("Orders");
        main = mainController;
        System.out.println(main.getClass().getCanonicalName());

    }
}
