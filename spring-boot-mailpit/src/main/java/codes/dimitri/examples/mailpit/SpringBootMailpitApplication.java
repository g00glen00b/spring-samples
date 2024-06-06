package codes.dimitri.examples.mailpit;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootMailpitApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SpringBootMailpitApplication.class, args);
		Thread.currentThread().join();
	}

	@Bean
	public ApplicationRunner runner(MailService service) {
		return args -> service.send();
	}
}
