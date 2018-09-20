package com.cvc.financeiro.transferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCircuitBreaker
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
public class TransferenciaApplication
{
    public static void main(String[] args) {


        System.out.println("afe " + TransferenciaApplication.class.getCanonicalName());
        SpringApplication.run(TransferenciaApplication.class, args);


    }
}
