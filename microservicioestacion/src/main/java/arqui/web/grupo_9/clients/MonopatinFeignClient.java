package arqui.web.grupo_9.clients;

import arqui.web.grupo_9.model.clients.MonopatinDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@FeignClient(name = "microserviciomonopatin")
public interface MonopatinFeignClient {

    @GetMapping("/api/monopatines/ubicacion")
    ResponseEntity<List<MonopatinDTO>> getMonopatinesByUbicacion(@RequestParam(name = "latitud", required = true) @NotEmpty(message = "La latitud enviada no es valida") Double latitud,
                                                                 @RequestParam(name = "longitud", required = true) @NotEmpty(message = "La longitud enviada no es valida") Double longitud);
}
