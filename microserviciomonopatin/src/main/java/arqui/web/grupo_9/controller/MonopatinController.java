package arqui.web.grupo_9.controller;

import arqui.web.grupo_9.model.dto.MonopatinDTO;
import arqui.web.grupo_9.model.dto.ReporteEstadoDTO;
import arqui.web.grupo_9.model.dto.converter.MonopatinConverter;
import arqui.web.grupo_9.model.entities.Monopatin;
import arqui.web.grupo_9.service.MonopatinService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/monopatines")
public class MonopatinController {

    private MonopatinService monopatinService;
    private MonopatinConverter converter;

    public MonopatinController(MonopatinService monopatinService, @Lazy MonopatinConverter converter) {
        this.monopatinService = monopatinService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> findAll() {
        List<Monopatin> monopatines = this.monopatinService.findAll();
        if(monopatines.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(monopatines), HttpStatus.OK);
    }

    @GetMapping("{idMonopatin}")
    public ResponseEntity<MonopatinDTO> findById(@PathVariable Long idMonopatin) {
        Monopatin monopatin = this.monopatinService.findById(idMonopatin);
        return new ResponseEntity<>(this.converter.fromEntity(monopatin), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody MonopatinDTO monopatin) {
        return new ResponseEntity<>(this.monopatinService.save(this.converter.fromDTO(monopatin)), HttpStatus.OK);
    }

    @DeleteMapping("/{idMonopatin}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idMonopatin) {
        return new ResponseEntity<>(this.monopatinService.deleteByid(idMonopatin), HttpStatus.OK);
    }

    @PutMapping("/{idMonopatin}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idMonopatin, @RequestBody MonopatinDTO monopatin) {
        return new ResponseEntity<>(this.monopatinService.updateById(idMonopatin, this.converter.fromDTO(monopatin)), HttpStatus.OK);
    }

    @GetMapping("/reporte/estado")
    public ResponseEntity<ReporteEstadoDTO> generarReporteEstado() {
        List<Monopatin> monopatinesEnOperacion = this.monopatinService.getMonopatinesByEstado("operacion");
        List<Monopatin> monopatinesEnMantenimiento = this.monopatinService.getMonopatinesByEstado("mantenimiento");

        ReporteEstadoDTO reporte = new ReporteEstadoDTO((monopatinesEnMantenimiento.isEmpty()) ? 0 : monopatinesEnMantenimiento.size(),
                (monopatinesEnOperacion.isEmpty()) ? 0 : monopatinesEnOperacion.size());
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

}
