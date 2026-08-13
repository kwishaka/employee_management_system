package com.ems.mis;
import org.springframework.boot.SpringApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://employeemanagementsystem-production-89b4.up.railway.app/", description = "Production Live Server")
        })
@SpringBootApplication

public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

}
