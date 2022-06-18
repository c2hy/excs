package io.github.c2hy.excs;

import java.util.function.Supplier;

public class Excs {
    public static <T extends Throwable> ExceptionContainer<T> create(T exception) {
        return new ExceptionContainerLogic<>(exception);
    }

    public static <T extends Throwable> ExceptionContainer<T> create(boolean condition, T exception) {
        return condition
                ? new ExceptionContainerLogic<>(exception)
                : new NonExceptionContainer<>();
    }

    public static <T extends Throwable> ExceptionContainer<T> create(Supplier<Boolean> condition, T exception) {
        return condition.get()
                ? new ExceptionContainerLogic<>(exception)
                : new NonExceptionContainer<>();
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(String message) {
        return new ExceptionContainerLogic<>(new RuntimeException(message));
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(String message, Throwable cause) {
        return new ExceptionContainerLogic<>(new RuntimeException(message, cause));
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(boolean condition, String message) {
        return condition
                ? new ExceptionContainerLogic<>(new RuntimeException(message))
                : new NonExceptionContainer<>();
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(boolean condition, String message, Throwable cause) {
        return condition
                ? new ExceptionContainerLogic<>(new RuntimeException(message, cause))
                : new NonExceptionContainer<>();
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(Supplier<Boolean> condition, String message) {
        return condition.get()
                ? new ExceptionContainerLogic<>(new RuntimeException(message))
                : new NonExceptionContainer<>();
    }

    public static ExceptionContainer<RuntimeException> createRuntimeException(Supplier<Boolean> condition, String message, Throwable cause) {
        return condition.get()
                ? new ExceptionContainerLogic<>(new RuntimeException(message, cause))
                : new NonExceptionContainer<>();
    }
}
