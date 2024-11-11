package arqui.web.grupo_9.clients;

import arqui.web.grupo_9.model.clients.MonopatinClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserviciomonopatin")
public interface MonopatinFeignClient {

    @GetMapping("/api/monopatines/{idMonopatin}")
    MonopatinClient findById(@PathVariable Long idMonopatin);
}
