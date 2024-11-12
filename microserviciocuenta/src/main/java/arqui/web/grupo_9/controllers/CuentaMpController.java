package arqui.web.grupo_9.controllers;

import arqui.web.grupo_9.model.dto.CuentaMpDTO;
import arqui.web.grupo_9.model.dto.converters.CuentaMPConverter;
import arqui.web.grupo_9.model.entities.CuentaMP;
import arqui.web.grupo_9.services.CuentaMpService;
import arqui.web.grupo_9.services.exceptions.CuentaInhabilitadaException;
import arqui.web.grupo_9.services.exceptions.NotFoundFechaException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/mercadopago/api/cuentas")
public class CuentaMpController {
    private CuentaMpService cuentaMpService;
    private CuentaMPConverter converter;

    public CuentaMpController(CuentaMpService cuentaMpService, @Lazy CuentaMPConverter converter) {
        this.cuentaMpService = cuentaMpService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<CuentaMpDTO>> findAll() {
        List<CuentaMP> cuentasMp = this.cuentaMpService.findAllCuentas();
        if (cuentasMp.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(cuentasMp), HttpStatus.OK);
    }

    @GetMapping("/{idCuentaMP}")
    public ResponseEntity<CuentaMpDTO> findById(@PathVariable Long idCuentaMP) {
        if(this.cuentaMpService.estaHabilitada(idCuentaMP)) {
            CuentaMP cuenta = this.cuentaMpService.findByIdCuenta(idCuentaMP);
            return new ResponseEntity<>(this.converter.fromEntity(cuenta), HttpStatus.FOUND);
        }

        throw new CuentaInhabilitadaException("Se intento realizar una operacion sobre una cuenta inhabilitada y se lanzo la excepcion correspondiente", "La cuenta con la que intentas operar se encuentra inhabilitada. Debes esperar hasta la fecha correspondiente", "high");
    }

    @PostMapping("/crear")
    public ResponseEntity<Boolean> crearCuenta(@RequestParam Long idUsuario) {
        return new ResponseEntity<>(this.cuentaMpService.crearCuenta(idUsuario), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody @Valid CuentaMpDTO cuenta) {
        return new ResponseEntity<>(this.cuentaMpService.saveCuenta(this.converter.fromDTO(cuenta)), HttpStatus.CREATED);
    }

    @PutMapping("/{idCuentaMP}/cargarSaldo")
    public ResponseEntity<Boolean> cargarSaldo(@PathVariable Long idCuentaMP,
                                               @RequestParam(name="saldo", required = true) @NotEmpty(message="El sado no puede ser vacio") @Positive(message="El saldo no puede ser negativo") double saldo) {
        if(this.cuentaMpService.estaHabilitada(idCuentaMP)) {
            return new ResponseEntity<>(this.cuentaMpService.cargarSaldoCuenta(idCuentaMP, saldo), HttpStatus.OK);
        }

        throw new CuentaInhabilitadaException("Se intento realizar una operacion sobre una cuenta inhabilitada y se lanzo la excepcion correspondiente", "La cuenta con la que intentas operar se encuentra inhabilitada. Debes esperar hasta la fecha correspondiente", "high");
    }

    @PutMapping("/{idCuentaMP}/descontarSaldo/viaje")
    public ResponseEntity<Boolean> descontarSaldo(@PathVariable Long idCuentaMP,
                                                  @RequestParam(name="saldo", required = true) @NotEmpty(message="El sado no puede ser vacio") @Positive(message="El saldo no puede ser negativo") double saldo) {
        if(this.cuentaMpService.estaHabilitada(idCuentaMP)) {
            return new ResponseEntity<>(this.cuentaMpService.descontarSaldoCuenta(idCuentaMP, saldo), HttpStatus.OK);
        }

        throw new CuentaInhabilitadaException("Se intento realizar una operacion sobre una cuenta inhabilitada y se lanzo la excepcion correspondiente", "La cuenta con la que intentas operar se encuentra inhabilitada. Debes esperar hasta la fecha correspondiente", "high");
    }

    //PUEDE TRAER PROBLEMAS DE MAPEO PORQUE YA EXISTE OTRO ENDPOINT IGUAL
    @PutMapping("/{idCuentaMP}/inhabilitar")
    public ResponseEntity<Boolean> inhabilitarCuenta(@PathVariable Long idCuentaMP,
                                                     @RequestParam(name="hasta", required = true) @NotEmpty(message = "La fecha no puede ser vacia") LocalDate fechaHasta) {
        if(!fechaHasta.isAfter(LocalDate.now()))
            throw new NotFoundFechaException("La fecha ingresada es menor a hoy", "Debe ingresar una fecha tope mayor a hoy", "high");

        if(this.cuentaMpService.estaHabilitada(idCuentaMP)) {
            return new ResponseEntity<>(this.cuentaMpService.inhabilitarCuenta(idCuentaMP, fechaHasta), HttpStatus.OK);
        }

        throw new CuentaInhabilitadaException("Se intento inhabilitar una cuenta ya inhabilitada", "La cuenta ya se encuentra inhabilitada", "low");
    }

    @DeleteMapping("/{idCuentaMP}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idCuentaMP) {
        if(this.cuentaMpService.estaHabilitada(idCuentaMP)) {
            return new ResponseEntity<>(this.cuentaMpService.deleteByIdCuenta(idCuentaMP), HttpStatus.OK);
        }

        throw new CuentaInhabilitadaException("Se intento realizar una operacion sobre una cuenta inhabilitada y se lanzo la excepcion correspondiente", "La cuenta con la que intentas operar se encuentra inhabilitada. Debes esperar hasta la fecha correspondiente", "high");
    }
}
