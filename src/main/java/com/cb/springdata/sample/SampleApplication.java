package com.cb.springdata.sample;

import com.github.liquicouch.LiquiCouch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@EnableAutoConfiguration
@SpringBootApplication
public class SampleApplication {

    @Autowired
	private ApplicationContext context;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SampleApplication.class, args);
	}


	@Bean
	public LiquiCouch liquicouch(){
		LiquiCouch runner = new LiquiCouch(context);
		runner.setChangeLogsScanPackage(
				"com.cb.springdata.sample.migration"); // the package to be scanned for changesets
		return runner;
	}
}
