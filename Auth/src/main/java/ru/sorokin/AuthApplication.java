package ru.sorokin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.security.SecureRandom;

@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        /*byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String secret = new BigInteger(1, bytes).toString(16);
        System.out.println(secret);*/
    }
}