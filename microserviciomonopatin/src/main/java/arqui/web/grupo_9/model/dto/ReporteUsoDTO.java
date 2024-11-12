package arqui.web.grupo_9.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteUsoDTO {
    private Long idMonopatin;
    private double kmsRecorridos;
    private LocalDateTime tiempoConPausa;
    private String estado;
}
