package arqui.web.grupo_9.viaje.controller;

import arqui.web.grupo_9.viaje.model.dto.ViajeDTO;
import arqui.web.grupo_9.viaje.model.dto.converter.ViajeConverter;
import arqui.web.grupo_9.viaje.model.entities.Viaje;
import arqui.web.grupo_9.viaje.service.ViajeService;

import arqui.web.grupo_9.viaje.service.exceptions.NotFoundViajeException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/viajes")
public class ViajeController {
    private ViajeService service;
    private ViajeConverter converter;

    public ViajeController(ViajeService service, @Lazy ViajeConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping()
    public ResponseEntity<List<ViajeDTO>> findAll() {
        List<Viaje> viajes = this.service.findAll();
        if(viajes.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(viajes), HttpStatus.OK);
    }

    @GetMapping("/{idViaje}")
    public ResponseEntity<ViajeDTO> findById(@PathVariable Long idViaje) {
        Viaje viajeFinded = this.service.findById(idViaje);
        return new ResponseEntity<>(this.converter.fromEntity(viajeFinded), HttpStatus.FOUND);
    }

    @DeleteMapping("/{idViaje}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idViaje) {
        return new ResponseEntity<>(this.service.deleteById(idViaje), HttpStatus.OK);
    }

    /*@PutMapping("/{idViaje}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idViaje, @RequestBody Viaje viajeModified) {
        if(this.service.findById(idViaje) == null)
            throw new NotFoundViajeException("El id enviado es incorrecto, no existe en la tabla viaje", "El viaje solicitado nunca fue generado. Por favor, solicita un viaje finalizado", "low");

        if(viajeModified == null)
            throw new BodyViajeException("El body enviado esta vacio", "Debe actualizar algun campo. Por favor, actualiza algun valor", "high");

        return new ResponseEntity<>(this.service.updateById(idViaje, viajeModified), HttpStatus.OK);
    }*/

    @GetMapping("/generar/{idUsuario}/{idMonopatin}")
    public ResponseEntity<Boolean> generarViaje(@PathVariable Long idUsuario, @PathVariable Long idMonopatin) {
        boolean generated = this.service.generar(idUsuario, idMonopatin);
        return new ResponseEntity<>(generated, HttpStatus.OK);
    }

    @GetMapping("/finalizar/viaje")
    public ResponseEntity<Boolean> finalizarViaje() {
        boolean finalizated = this.service.finalizar();
        return new ResponseEntity<>(finalizated, HttpStatus.OK);
    }

}
