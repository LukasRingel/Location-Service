package locationservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LocationServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LocationServiceApplication.class).profiles("prometheus").run(args);
    }

}
