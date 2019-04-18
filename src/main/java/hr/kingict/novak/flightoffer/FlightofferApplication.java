package hr.kingict.novak.flightoffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlightofferApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightofferApplication.class, args);
    }

}
