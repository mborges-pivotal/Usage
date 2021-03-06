package io.pivotal.tola.cfapi.usage;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import io.pivotal.tola.cfapi.usage.configuration.SSLValidationDisabler;


@SpringBootApplication
public class UsageApplication {

	private static final Logger LOG = LoggerFactory.getLogger(UsageApplication.class);

	public static void main(String[] args) {
		SSLValidationDisabler.disableSSLValidation();
		SpringApplication.run(UsageApplication.class, args);
	}	

}
