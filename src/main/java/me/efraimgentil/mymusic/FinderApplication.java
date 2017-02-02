package me.efraimgentil.mymusic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class FinderApplication {

	@Bean
	@Qualifier(value = "config")
	public Properties configProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(System.getProperty("user.dir") + "/../finder.properties"));
		return properties;
	}

	public static void main(String[] args) throws IOException {

		SpringApplication.run(FinderApplication.class, args);

	}



}
