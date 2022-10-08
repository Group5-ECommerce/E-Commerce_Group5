package com.hcl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
public class SpringBootEx1Application {
	
    public static void main(String[] args) {
    	ApplicationInsights.attach();
		SpringApplication.run(SpringBootEx1Application.class, args);
    }
}