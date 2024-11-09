package store.utils;

@FunctionalInterface
public interface InputSupplier<T> {
    T get() throws IllegalArgumentException;
}

/* 사용법 컨트롤러에서 유효성검사 될때까지하는거
private <T> T getValidatedInput(InputSupplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                viewFacade.showErrorMessage(e.getMessage());
            }
        }
    }
 */