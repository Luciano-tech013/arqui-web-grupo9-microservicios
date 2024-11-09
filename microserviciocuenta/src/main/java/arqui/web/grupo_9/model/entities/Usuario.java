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

    public boolean tieneCuenta(CuentaMP cuenta) {
        return this.cuentasMp.contains(cuenta);
    }

    public void asociarCuenta(CuentaMP cuenta) {
        this.cuentasMp.add(cuenta);
    }

    public void eliminarCuenta(CuentaMP cuenta) {
        this.cuentasMp.remove(cuenta);
    }

    @Override
    public boolean equals(Object o) {
        Usuario otroU = (Usuario) o;
        return this.getIdUsuario().equals(otroU.getIdUsuario());
    }
}
