package FirstService;

import org.springframework.boot.SpringApplication;

import java.io.IOException;

public class FirstService {
    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(ScheduleGenerator.class);
        app.run();
    }
}
