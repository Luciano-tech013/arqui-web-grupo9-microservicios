package arqui.web.grupo_9.usuario.model.dto;

import arqui.web.grupo_9.usuario.model.entities.CuentaMP;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private String nombre;

    private String apellido;

    private String nroCelular;

    private String email;

    private List<CuentaMP> cuentasMp;
}
