package codes.dimitri.htmx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HtmxExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmxExampleApplication.class, args);
	}

}
