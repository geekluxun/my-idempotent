package com.geekluxun.myidempotent;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = {"com.geekluxun.myidempotent.service"})
public class MyIdempotentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyIdempotentApplication.class, args);
    }

}

