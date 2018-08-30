package br.com.cvc.taxas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="file:./application.yml")
@EnableCircuitBreaker
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class Application 
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
