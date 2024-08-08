package com.hpugs.movedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com.hpugs.movedata")
@SpringBootApplication
public class MoveDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveDataApplication.class, args);
    }

}
