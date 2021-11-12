package locationservice;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Clock;
import java.util.Properties;

@Component
public class LocationServiceBeans {

    private final Properties applicationProperties = new Properties();

    @PostConstruct
    private void loadProperties() throws IOException {
        applicationProperties.load(new FileInputStream(ResourceUtils.getFile("./application.properties")));
    }

    @Bean
    public Properties properties() {
        return applicationProperties;
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}