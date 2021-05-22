package SecondService;

import org.springframework.boot.SpringApplication;

import java.io.IOException;

public class SecondService {
    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(Writer.class);
        app.run();
    }
}
