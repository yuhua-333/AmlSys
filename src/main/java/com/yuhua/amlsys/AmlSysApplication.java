package com.yuhua.amlsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yuhua.amlsys.*.mapper")
public class AmlSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmlSysApplication.class, args);
    }

}
