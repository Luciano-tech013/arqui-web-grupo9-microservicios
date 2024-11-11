package arqui.web.grupo_9.controller;

import arqui.web.grupo_9.model.dto.ViajeDTO;
import arqui.web.grupo_9.model.dto.converter.ViajeConverter;
import arqui.web.grupo_9.model.entities.Viaje;
import arqui.web.grupo_9.service.ViajeService;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("/generar/{idUsuario}/{idMonopatin}")
    public ResponseEntity<Boolean> generarViaje(@PathVariable Long idUsuario, @PathVariable Long idMonopatin) {
        return new ResponseEntity<>(this.service.generar(idUsuario, idMonopatin), HttpStatus.OK);
    }

    @PutMapping("/finalizar/viaje")
    public ResponseEntity<ViajeDTO> finalizarViaje() {
        return new ResponseEntity<>(this.converter.fromEntity(this.service.finalizar()), HttpStatus.OK);
    }

    @PutMapping("/pausar/viaje")
    public ResponseEntity<Boolean> pausarViaje() {
        return new ResponseEntity<>(this.service.pausar(), HttpStatus.OK);
    }

    @PutMapping("/despausar/viaje")
    public ResponseEntity<Boolean> despausarViaje() {
        return new ResponseEntity<>(this.service.despausar(), HttpStatus.OK);
    }

    @GetMapping("/monopatines/")
    public ResponseEntity<List<Long>> getMonopatinesByCantViajesInoneYear(
            @RequestParam(name = "anio", required = true) @NotEmpty(message = "El anio no puede ser vacio") @Positive(message = "El anio no puede ser negatvivo") int anio,
            @RequestParam(name = "cant_viajes", required = true) @NotEmpty(message = "La cantidad no puede ser negativa") @Positive(message = "El anio no puede ser negativo") int cantViajes) {

        List<Long> monopatines = this.service.getMonopatinesByCantViajesInOneYear(anio, cantViajes);
        if(monopatines.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @GetMapping("/facturacion")
    public ResponseEntity<Double> getTotalFacturadoByMesesInOneYear(
            @RequestParam(name = "desde", required = true) @NotEmpty(message = "La fecha de inicio no puede ser vacia") @Positive(message = "El mes de inicio no puede ser negativo") int mesIni,
            @RequestParam(name = "hasta", required = true) @NotEmpty(message = "La fecha de fin no puede ser vacia") @Positive(message = "El mes de fin no puede ser negativo") int mesFin,
            @RequestParam(name = "anio", required = true) @NotEmpty(message = "El año no puede ser vacio") @Positive(message = "El anio no puede ser negativo") int anio) {

        return new ResponseEntity<>(this.service.getTotalFacturadoByMesesInOneYear(mesIni, mesFin, anio), HttpStatus.OK);
    }

    @PutMapping("/ajustes/precio")
    public ResponseEntity<Boolean> ajustarPrecioParaUnaFecha(
            @RequestParam(name = "fecha", required = true) @NotEmpty(message = "La fecha no es valida") LocalDate aPartirDe,
            @RequestParam(name = "precio", required = true) @NotEmpty(message = "El precio no es valido") @Positive(message = "El precio no puede ser negativo") double precio) {

        return new ResponseEntity<>(this.service.ajustarPrecioParaUnaFecha(aPartirDe, precio), HttpStatus.OK);
    }
}
