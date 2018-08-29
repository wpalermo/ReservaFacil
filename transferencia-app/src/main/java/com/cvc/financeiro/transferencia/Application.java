package com.cvc.financeiro.transferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="file:./application.yml")
@SpringBootApplication
public class Application 
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
