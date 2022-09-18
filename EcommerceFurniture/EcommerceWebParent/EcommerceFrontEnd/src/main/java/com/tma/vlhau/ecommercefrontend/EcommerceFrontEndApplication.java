package com.tma.vlhau.ecommercefrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.tma.vlhau.ecommercecommon.entity"})
public class EcommerceFrontEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceFrontEndApplication.class, args);
    }

}
