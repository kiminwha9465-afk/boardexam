package com.example.board0625;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.board0625.mapper")
public class Board0625Application {

    public static void main(String[] args) {
        SpringApplication.run(Board0625Application.class, args);
    }

}
