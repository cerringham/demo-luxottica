package it.bitrock.demoluxottica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoLuxotticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLuxotticaApplication.class, args);
	}

}
