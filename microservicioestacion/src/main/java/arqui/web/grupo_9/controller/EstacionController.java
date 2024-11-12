package arqui.web.grupo_9.controller;

import arqui.web.grupo_9.model.clients.MonopatinDTO;
import arqui.web.grupo_9.model.dto.EstacionDTO;
import arqui.web.grupo_9.model.dto.ReporteDTO;
import arqui.web.grupo_9.model.dto.converter.EstacionConverter;
import arqui.web.grupo_9.model.entities.Estacion;
import arqui.web.grupo_9.service.EstacionService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController("/api/estaciones")
public class EstacionController {

    private EstacionService estacionService;
    private EstacionConverter converter;

    public EstacionController(EstacionService estacionService, @Lazy EstacionConverter converter) {
        this.estacionService = estacionService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<EstacionDTO>> findAll() {
        List<Estacion> estaciones = this.estacionService.findAll();
        if(estaciones.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(estaciones), HttpStatus.OK);
    }

    @GetMapping("/{idEstacion}")
    public ResponseEntity<EstacionDTO> findById(@PathVariable Long idEstacion) {
        Estacion estacion = this.estacionService.findById(idEstacion);
        return new ResponseEntity<>(this.converter.fromEntity(estacion), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody EstacionDTO estacion) {
        return new ResponseEntity<>(this.estacionService.save(this.converter.fromDTO(estacion)), HttpStatus.OK);
    }

    @DeleteMapping("/{idEstacion}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idEstacion) {
        return new ResponseEntity<>(this.estacionService.deleteById(idEstacion), HttpStatus.OK);
    }

    @PostMapping("/{idEstacion}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idEstacion, @RequestBody EstacionDTO estacion) {
        return new ResponseEntity<>(this.estacionService.updateById(idEstacion, this.converter.fromDTO(estacion)), HttpStatus.OK);
    }

    @GetMapping("/ubicacion")
    public ResponseEntity<List<ReporteDTO>> getMonopatinesByUbicacion(
            @RequestParam(name = "latitud", required = true) @NotEmpty(message = "La latitud enviada no es valida") Double latitud,
            @RequestParam(name = "longitud", required = true) @NotEmpty(message = "La longitud enviada no es valida") Double longitud){

        List<Estacion> estaciones = this.estacionService.getEstacionesByUbicacion(latitud, longitud);

        if(estaciones.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        List<ReporteDTO> reportes = new LinkedList<>();
        for(Estacion e : estaciones) {
            reportes.add(new ReporteDTO(this.converter.fromEntity(e), this.estacionService.getMonopatinesByUbicacion(e.getLatitud(), e.getLongitud())));
        }

        return new ResponseEntity<>(reportes, HttpStatus.OK);
    }
}
