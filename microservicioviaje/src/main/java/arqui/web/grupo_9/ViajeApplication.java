package arqui.web.grupo_9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

public class ViajeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajeApplication.class, args);
    }
}
