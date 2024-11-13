package arqui.web.grupo_9.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteUsoDTO {
    private Long idMonopatin;
    private double kmsRecorridos;
    private Duration tiempoConPausa;
    private String estado;
}
