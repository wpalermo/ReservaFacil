package com.wpalermo.HystrixDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world!
 *
 */
@PropertySource(value="file:./application.yml")
@SpringBootApplication
@EnableHystrixDashboard
public class App 
{
	  public static void main(String[] args) {
	        SpringApplication.run(App.class, args);
	    }
	    
	    
}
