package com.ems.mis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Server railwayServer = new Server();
        railwayServer.setUrl("https://employeemanagementsystem-production-4ff0.up.railway.app");
        railwayServer.setDescription("Production (Railway)");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local development");

        return new OpenAPI().servers(List.of(railwayServer, localServer));
    }
}
