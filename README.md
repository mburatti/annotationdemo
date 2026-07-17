# annotationdemo

A small Quarkus sample that demonstrates how to create a custom Java annotation and use it to trigger interceptor-based exception handling.

## Overview

This project uses Quarkus and a custom annotation to protect risky method execution. The sample shows:

- defining an interceptor binding annotation (`@SafeExecution`)
- applying the annotation to a service method
- implementing a Quarkus/Jakarta interceptor that handles exceptions
- building the application and generating a native executable

> Note: the Maven project is currently configured for Java 25, but the same Quarkus annotation pattern applies to Java 21 and later.

## Project structure

- `src/main/java/com/example/annotationdemo/annotation/SafeExecution.java` - custom interceptor binding annotation
- `src/main/java/com/example/annotationdemo/aspect/ExceptionHandlingAspect.java` - interceptor handling method exceptions
- `src/main/java/com/example/annotationdemo/service/CalculatorService.java` - sample service using the annotation
- `src/main/java/com/example/annotationdemo/app/DemoRunner.java` - Quarkus startup runner

## Create a custom annotation

In this project, the annotation is defined as an interceptor binding:

- `@InterceptorBinding`
- `@Target({ElementType.METHOD, ElementType.TYPE})`
- `@Retention(RetentionPolicy.RUNTIME)`

You can create a new annotation with the same pattern and use it to trigger custom interceptor behavior.

## How to use the annotation

1. Define the annotation in `src/main/java/com/example/annotationdemo/annotation`.
2. Create or update an interceptor class in `src/main/java/com/example/annotationdemo/aspect`.
3. Apply the annotation to a service method, for example:

```java
@SafeExecution
public int divide(int a, int b) {
    return a / b;
}
```

4. The interceptor will run around the annotated method and can handle exceptions, logging, or fallback behavior.

## Build and run

### Standard Maven build

Use the Maven wrapper to compile and package the application:

```bash
./mvnw clean package
```

### Run tests

```bash
./mvnw test
```

### Generate a native executable

This project uses the Quarkus native profile to build a native executable.

```bash
./mvnw -Pnative clean package
```

After a successful native build, the executable is generated under:

- `target/annotationdemo-0.0.1-SNAPSHOT-native-image-source-jar/`

## Performance benchmark

The following table shows end-to-end startup runs for the Quarkus JVM jar and the Quarkus native executable, plus observed peak memory usage for a single execution.

| Runtime | Run 1 | Run 2 | Run 3 | Peak memory |
|---|---|---|---|---|
| Quarkus JVM | 0.402s | 0.307s | 0.220s | ~86.6 MB |
| Quarkus Native | 0.884s | 0.022s | 0.023s | ~4.2 MB |
| Spring Boot JVM | 1.05s | 0.91s | 0.71s | ~189 MB |

> Note: the first native run includes cold startup and any process load overhead. Subsequent native runs are much faster, which is typical for a compiled native image. The Spring Boot row shows three JVM startup measurements for comparison.

## Notes

- This sample is built with Quarkus and should work on Java 21 or newer, with the project currently configured for Java 25.
- If you want to target Java 21 specifically, update the Maven compiler properties in `pom.xml`.

## License

This project is provided as a small demo for custom Quarkus interceptor annotations.
