package arqui.web.grupo_9.usuario.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Usuario {

    @Id
    private Long id;
    @Column
    private String nombre;
    @ManyToMany(mappedBy = "usuarios")
    private List<CuentaMP> cuentasMp;

    public Usuario() {
    }
}
