package store.utils;

import java.util.function.Predicate;
import java.util.function.Supplier;
import store.constant.ErrorMessage;

public class Retry<T> {

    public T execute(Supplier<T> supplier, Predicate<T> stopCondition) {
        while (true) {
            try {
                T result = supplier.get();
                if (stopCondition.test(result)) {
                    return result; // 결과가 조건에 맞을 때 반환
                }
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.DEFAULT_HEADER_MESSAGE + e.getMessage()); // 예외 메시지 출력
            }
        }
    }
}