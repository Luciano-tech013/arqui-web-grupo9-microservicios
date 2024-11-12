package arqui.web.grupo_9.clients;

import arqui.web.grupo_9.model.clients.MonopatinDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microserviciomonopatin")
public interface MonopatinFeignClient {

    @GetMapping("/api/monopatines/{idMonopatin}")
    ResponseEntity<MonopatinDTO> findById(@PathVariable Long idMonopatin);

    @PostMapping
    ResponseEntity<Boolean> save(@RequestBody @Valid MonopatinDTO monopatin);
}
