package arqui.web.grupo_9.usuario.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(name = "nro_celular")
    private String nroCelular;

    @Column
    private String email;
}
