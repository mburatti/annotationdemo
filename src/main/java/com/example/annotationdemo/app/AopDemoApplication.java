package com.example.annotationdemo.app;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class AopDemoApplication implements QuarkusApplication {

    @Inject
    DemoRunner demoRunner;

    public static void main(String[] args) {
        Quarkus.run(AopDemoApplication.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        demoRunner.run(args);
        return 0;
    }
}
