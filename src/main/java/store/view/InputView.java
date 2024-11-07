package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getNameAndQuantity() {
        System.out.println(ViewMessage.INPUT_NAME_NUMBER.getMessage());
        return Console.readLine();
    }
}
