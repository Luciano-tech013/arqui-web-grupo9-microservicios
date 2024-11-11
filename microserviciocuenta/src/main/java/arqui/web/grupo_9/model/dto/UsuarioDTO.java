package arqui.web.grupo_9.model.dto;

import arqui.web.grupo_9.model.entities.CuentaMP;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;

    private String nombre;

    private String apellido;

    private String nroCelular;

    private String email;

    private List<CuentaMP> cuentasMp;
}
