package webadv.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "webadv.***" })
@SpringBootApplication
public class CdmsMavenApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CdmsMavenApplication.class, args);
		
	}
}
