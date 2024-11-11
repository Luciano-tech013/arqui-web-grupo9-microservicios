package arqui.web.grupo_9.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstacionDTO {
    private Long idEstacion;
    private String nombre;
    private Double latitud;
    private Double longitud;

    public EstacionDTO() {}
}
