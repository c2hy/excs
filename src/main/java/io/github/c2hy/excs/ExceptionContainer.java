package io.github.c2hy.excs;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ExceptionContainer<T extends Throwable> {
    void doThrow() throws T;

    T returnException();

    Supplier<T> getSupplier();

    <E extends Throwable> ExceptionContainer<E> transform(Function<T, E> transformFn);

    ExceptionContainer<RuntimeException> transformToRuntimeException();
}
