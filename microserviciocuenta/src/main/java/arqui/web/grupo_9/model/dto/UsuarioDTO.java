package arqui.web.grupo_9.model.dto;

import arqui.web.grupo_9.model.entities.CuentaMP;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    @NotBlank(message = "El nombre ingresado es invalido")
    private String nombre;

    @NotBlank(message = "El apellido ingresado es invalido")
    private String apellido;

    @NotBlank(message = "El nro de celular es invalido")
    private String nroCelular;

    @NotBlank(message = "El email ingresado es invalido")
    private String email;
}
