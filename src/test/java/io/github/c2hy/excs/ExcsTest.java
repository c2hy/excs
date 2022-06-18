package io.github.c2hy.excs;

import org.junit.jupiter.api.Disabled;

import java.util.Optional;

class ExcsTest {

    @Disabled
    void draft() throws Exception {
        Excs.create(new RuntimeException()).doThrow();
        Excs.create(new Exception()).doThrow();
        Excs.create(new RuntimeException())
                .transform(e -> new Exception(e.getMessage()))
                .doThrow();

        Excs.createRuntimeException("error").doThrow();
        Exception exception = new Exception();
        Excs.create(exception).transform(RuntimeException::new).doThrow();
        Excs.create(() -> {
            // 判断逻辑
            return true;
        }, new Exception());

        Excs.createRuntimeException(() -> true, "...", new Exception());
    }
}