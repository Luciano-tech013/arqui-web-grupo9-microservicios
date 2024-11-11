package arqui.web.grupo_9.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MonopatinDTO {
    private Long idMonopatin;
    private double kmsRecorridos;
    private double latitud;
    private double longitud;
    private String estado;

    public MonopatinDTO(double latitud, double longitud, String estado) {
        this.latitud = latitud;
        this.kmsRecorridos = 0;
        this.longitud = longitud;
        this.estado = estado;
    }

    public MonopatinDTO(Long idMonopatin, double kmsRecorridos, double latitud, double longitud, String estado) {
        this.idMonopatin = idMonopatin;
        this.kmsRecorridos = kmsRecorridos;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }
}
