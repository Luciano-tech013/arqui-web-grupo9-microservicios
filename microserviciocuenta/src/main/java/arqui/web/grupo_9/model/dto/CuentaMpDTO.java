package arqui.web.grupo_9.model.dto;

import arqui.web.grupo_9.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CuentaMpDTO {
    private Long idCuentaMP;

    private Double saldo;

    private List<Usuario> usuarios;
}
