package arqui.web.grupo_9.viaje.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ViajeDTO {
    private final double precioBase;

    private LocalDateTime fechaIniViaje;

    private LocalDateTime fechaFinViaje;

    private Double kmsRecorridos;

    //¿Deberia retornar el usuario y el monopatin o no?
}
