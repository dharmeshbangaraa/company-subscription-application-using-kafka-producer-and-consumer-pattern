package com.producerconsumer.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;

import java.sql.SQLException;

@SpringBootApplication
public class BillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);

	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server1() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9080");
	}

}
