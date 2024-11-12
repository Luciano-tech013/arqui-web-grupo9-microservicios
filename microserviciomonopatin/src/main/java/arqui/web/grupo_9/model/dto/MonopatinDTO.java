package arqui.web.grupo_9.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MonopatinDTO {
    private Long idMonopatin;

    @NotBlank(message = "Los kms recorridos enviados no son validos")
    @Positive(message = "Los kms recorridos no pueden ser negativos")
    private double kmsRecorridos;

    @NotBlank(message = "La latitud enviada no es valida")
    private double latitud;

    @NotBlank(message = "La longitud enviada no es valida")
    private double longitud;

    @NotBlank(message = "El estado enviado no es valido")
    @Pattern(regexp = "^(operacion|mantenimiento)$", message = "El estado debe ser 'operacion' o 'mantenimiento'")
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
