package arqui.web.grupo_9.viaje.controller;

import arqui.web.grupo_9.viaje.model.dto.ViajeDTO;
import arqui.web.grupo_9.viaje.model.dto.converter.ViajeConverter;
import arqui.web.grupo_9.viaje.model.entities.Viaje;
import arqui.web.grupo_9.viaje.service.ViajeService;

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
        return new ResponseEntity<>(this.converter.fromEntity(viajes), HttpStatus.OK);
    }

    @GetMapping("/{idViaje}")
    public ResponseEntity<ViajeDTO> findById(@PathVariable Long idViaje) {
        Viaje viajeFinded = this.service.findById(idViaje);
        return new ResponseEntity<>(this.converter.fromEntity(viajeFinded), HttpStatus.FOUND);
    }

    //El viaje se levanta una vez finalizado
    private boolean save(Viaje viaje) {
        return this.service.save(viaje);
    }

    @DeleteMapping("/{idViaje}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idViaje) {
        boolean deleted = this.service.deleteById(idViaje);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @PutMapping("/{idViaje}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idViaje, @RequestBody Viaje viajeModified) {
        boolean updated = this.service.updateById(idViaje, viajeModified);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/generar")
    public ViajeDTO generarViaje() {
        return null;
    }
}
