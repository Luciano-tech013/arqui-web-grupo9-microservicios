package arqui.web.grupo_9.model.dto;

import arqui.web.grupo_9.model.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CuentaMpDTO {
    private Long idCuentaMP;

    @NotBlank(message = "El saldo enviado no es valido")
    @Positive(message = "El saldo no puede ser negativo")
    private Double saldo;
}
