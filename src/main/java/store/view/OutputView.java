package store.view;

import store.constant.Message;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println(Message.OUTPUT_WELCOME_MESSAGE.getMessage());
    }

    public void printOurProducts() {
        System.out.println(Message.OUTPUT_OUR_PRODUCTS.getMessage());
    }
}
