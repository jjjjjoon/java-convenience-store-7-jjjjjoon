package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private enum Message{
        INPUT_NAME_NUMBER("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

        private String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public String getNameAndQuantity() {
        System.out.println(Message.INPUT_NAME_NUMBER.getMessage());
        return Console.readLine();
    }
}
