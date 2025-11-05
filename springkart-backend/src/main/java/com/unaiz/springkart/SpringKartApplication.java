package com.unaiz.springkart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKartApplication.class, args);

        //startup message
        System.out.println("SpringKartApplication started Successfully!");
    }

}
