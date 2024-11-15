package arqui.web.grupo_9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EstacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstacionApplication.class, args);
    }

}
