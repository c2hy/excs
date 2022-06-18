# EXCS 库使用教程
EXCS 是一个 Java 异常工具类，其为异常使用提供了现代化的 fluent API 以及在 JDK 1.8 版本以后异常的部分使用场景提供了便利的工具。

**Github**：https://github.com/c2hy/excs

maven 引入 EXCS 库

```xml
<dependency>
    <groupId>io.github.c2hy</groupId>
    <artifactId>excs</artifactId>
    <version>1.0.0</version>
</dependency>
```



##### EXCS 提供了那些便利 ?

- 校验参数是否合法，不合法时抛出校验异常

  ```java
  Parameter p = new Parameter();
  boolean isIllegal = p.check();
  Excs.createRuntimeException(isIllegal, "参数不合法").doThrow();
  ```

- Optional 使用时获取值或抛出异常

  ```java
  Optional.ofNullable(null).orElseThrow(Excs.createRuntimeException("null value").getSupplier())
  ```

- 转换异常至 RuntimeException

  ```
  Excs.create(new Exception()).transformToRuntimeException().doThrow();
  ```

- 包装异常至自定义异常

  ```java
  Excs.create(new Exception()).transform(CustomException::new).doThrow();
  ```



##### 如何使用 EXCS ？

下文是对 EXCS 工具类使用提供的说明文档和示例。

`ExceptionContainer` 接口是 EXCS 的核心能力，下文针对该接口的每个方法提供使用讲解和实例：

- `void doThrow() throws T` 抛出已创建的异常。

  ```java
  // 常规写法
  throw new RuntimeException("...");
  // EXCS 写法
  Excs.createRuntimeException("...").doThrow();
  ```

- `T returnException()` 获取已创建的异常

  ```java
  // 常规写法
  RuntimeException e = new RuntimeException();
  // EXCS 写法
  RuntimeException excsE = Excs.RuntimeException("...").returnException();
  ```

- `Supplier<T> getSupplier()` 获取提供已创建的异常的 Supplier 函数

  ```java
  // 常规写法
  Supplier<RuntimeException> s = () -> new RuntimeException();
  // EXCS 写法
  Supplier<RuntimeException> excsS = Excs.RuntimeException("...").getSupplier();
  ```

- `<E extends Throwable> ExceptionContainer<E> transform(Function<T, E> transformFn)` 转换已创建的异常

  ```java
  Exception e = new Exception();
  // 常规写法
  throw new CustomException(e);
  // EXCS 写法
  Excs.create(e).transform(CustomException::new).doThrow();
  ```

- `ExceptionContainer<RuntimeException> transformToRuntimeException()` transform 定制场景，转换已创建的异常值 RuntimeException

  ```java
  Exception e = new Exception();
  // 常规写法
  throw new RuntimeException(e);
  // EXCS 写法
  Excs.create(e).transformToRuntimeException().doThrow();
  ```

  

`Excs` 工具类提供了众多创建 `ExceptionContainer` 的方法

- `create` 方法可以提供任意的 Throwable 创建 `ExceptionContainer` 

  ```java
  Excs.create(new Exception());
  ```

  可以通过提供一个 condition 来控制是否真的需要创建异常，有两种提供 condition 的方式。当不需要创建异常时，会返回一个特殊的 `ExceptionContainer` 实现 `NonExceptionContainer`，后文会说明该实现的特殊行为。

  ```java
  // 直接提供一个 Boolean 值
  Excs.create(false, new Exception());
  // 提供了返回 Boolean 值的 Supplier 函数
  Excs.create(() -> {
      // 判断逻辑
      return true;
  }, new Exception());
  ```

- `createRuntimeException` 针对高频使用的使用 RuntimeException 创建  `ExceptionContainer` 提供的定制方法

  ```java
  // 提供 message 创建异常
  Excs.createRuntimeException("...");
  // 提供 message 和 cause 创建异常, 常规写法是这样的 new RuntimeException("...", new Exception())
  Excs.createRuntimeException("...", new Exception());
  ```

  该方法也支持通过提供一个 condition 来控制是否真的需要创建异常

  ```java
  Excs.createRuntimeException(true, "...");
  Excs.createRuntimeException(() -> true, "...", new Exception());
  ```



##### `NonExceptionContainer` 的特殊行为说明

- `void doThrow() throws T` 将不会抛出异常
- `T returnException()` 将会返回 null
- `Supplier<T> getSupplier()` 将会返回一个 `() -> null`
- `<E extends Throwable> ExceptionContainer<E> transform(Function<T, E> transformFn)` 将会返回一个 `NonExceptionContainer` 对象
