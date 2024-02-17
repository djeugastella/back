package projet.world.world;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import projet.world.world.service.CityService;

@SpringBootApplication
public class WorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CityService cityService) {
		return args -> {
			cityService.synchReadExcel();
		};
	}
}
