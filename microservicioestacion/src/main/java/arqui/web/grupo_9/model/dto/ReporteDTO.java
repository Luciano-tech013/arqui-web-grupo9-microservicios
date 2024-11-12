package arqui.web.grupo_9.model.dto;

import arqui.web.grupo_9.model.clients.MonopatinDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReporteDTO {
    private EstacionDTO estacion;
    private List<MonopatinDTO> monopatin;
}
