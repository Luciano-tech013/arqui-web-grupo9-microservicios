package arqui.web.grupo_9.controller;

import arqui.web.grupo_9.model.dto.EstacionDTO;
import arqui.web.grupo_9.model.dto.converter.ConverterDTO;
import arqui.web.grupo_9.model.dto.converter.EstacionConverter;
import arqui.web.grupo_9.model.entities.Estacion;
import arqui.web.grupo_9.service.EstacionService;
import arqui.web.grupo_9.service.exceptions.NotValidUbicacionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<EstacionDTO>> getUbicacion(@RequestParam Double latitud, @RequestParam Double longitude){
        if(latitud == null || longitude == null) {
            throw new NotValidUbicacionException("No enviaron la ubicacion del usuario", "La ubicacion enviada no es valida. Por faovr, envia una ubicacion valida", "high");
        }

        List<Estacion> estaciones = this.estacionService.getEstacionsByUbicacion(latitud, longitude);

        if(estaciones.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        // queda pendiente hacer la consultas a los monopatines

        return new ResponseEntity<>(this.converter.fromEntity(estaciones), HttpStatus.OK);
    }
}
