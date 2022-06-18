package io.github.c2hy.excs;

import java.util.function.Function;
import java.util.function.Supplier;

public class NonExceptionContainer<T extends Throwable> implements ExceptionContainer<T> {
    NonExceptionContainer() {
    }

    @Override
    public void doThrow() {
        // do nothing
    }

    @Override
    public T returnException() {
        return null;
    }

    @Override
    public Supplier<T> getSupplier() {
        return () -> null;
    }

    @Override
    public <E extends Throwable> ExceptionContainer<E> transform(Function<T, E> transformFn) {
        return new NonExceptionContainer<>();
    }

    @Override
    public ExceptionContainer<RuntimeException> transformToRuntimeException() {
        return new NonExceptionContainer<>();
    }

    @Override
    public String toString() {
        return "Non Exception";
    }
}
