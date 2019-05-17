package com.cb.springdata.sample;

import com.github.liquicouch.LiquiCouch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class SampleApplication {

    @Autowired
	private ApplicationContext context;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SampleApplication.class, args);
	}
}
