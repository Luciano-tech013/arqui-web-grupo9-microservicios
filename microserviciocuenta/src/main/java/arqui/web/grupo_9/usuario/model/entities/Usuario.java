package arqui.web.grupo_9.usuario.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(name = "nro_celular")
    private String nroCelular;

    @Column
    private String email;

    @ManyToMany(mappedBy = "usuarios")
    private List<CuentaMP> cuentasMp;

    public Usuario() {
    }
}
