package arqui.web.grupo_9.clients;

import arqui.web.grupo_9.model.clients.CuentaClient;
import arqui.web.grupo_9.model.clients.CuentaMpDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microserviciocuenta")
public interface CuentaFeignClient {

    @GetMapping("/mercadopago/api/cuentas/{idUsuario}")
    CuentaMpDTO findById(@PathVariable Long idUsuario);

    @PostMapping
    boolean save(@RequestBody @Valid CuentaMpDTO cuenta);
}
