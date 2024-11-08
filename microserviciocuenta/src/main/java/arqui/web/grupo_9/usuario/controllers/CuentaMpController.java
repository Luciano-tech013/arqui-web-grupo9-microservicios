package arqui.web.grupo_9.usuario.controllers;

import arqui.web.grupo_9.usuario.model.dto.CuentaMpDTO;
import arqui.web.grupo_9.usuario.model.entities.CuentaMP;
import arqui.web.grupo_9.usuario.services.CuentaMpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/mercadopago/api/cuentas")
public class CuentaMpController {

    CuentaMpService cuentaMpService;


    @GetMapping("/cuentas")
    public ResponseEntity<List<CuentaMpDTO>> getAll() {
        List<CuentaMP> cuentasMp = this.cuentaMpService.findAll();
        if (cuentasMp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(convertir(cuentasMp), HttpStatus.OK)
    }
}
