package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.Message;

public class InputView {

    public String getNameAndQuantity() {
        System.out.println(Message.INPUT_NAME_NUMBER.getMessage());
        return Console.readLine();
    }
}
