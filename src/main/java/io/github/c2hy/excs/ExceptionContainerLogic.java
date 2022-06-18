package io.github.c2hy.excs;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class ExceptionContainerLogic<T extends Throwable> implements ExceptionContainer<T> {
    private final T exception;

    ExceptionContainerLogic(T exception) {
        if (isNull(exception)) throw new IllegalArgumentException("must not null");
        this.exception = exception;
    }

    public void doThrow() throws T {
        throw exception;
    }

    public T returnException() {
        return exception;
    }

    public Supplier<T> getSupplier() {
        return () -> exception;
    }

    @Override
    public <E extends Throwable> ExceptionContainer<E> transform(Function<T, E> transformFn) {
        return new ExceptionContainerLogic<>(transformFn.apply(exception));
    }

    @Override
    public ExceptionContainer<RuntimeException> transformToRuntimeException() {
        if (exception instanceof RuntimeException) {
            RuntimeException e = (RuntimeException) exception;
            return new ExceptionContainerLogic<>(e);
        } else {
            return new ExceptionContainerLogic<>(new RuntimeException(exception));
        }
    }

    @Override
    public String toString() {
        return exception.toString();
    }
}
