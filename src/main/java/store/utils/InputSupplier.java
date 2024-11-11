package store.utils;

@FunctionalInterface
public interface InputSupplier<T> {
    T get() throws IllegalArgumentException;
}
