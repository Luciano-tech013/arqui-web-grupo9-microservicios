package arqui.web.grupo_9.usuario.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "cuenta_mp")
public class CuentaMP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta_mp")
    private Long idCuentaMP;

    @Column
    private Double saldo;

    @ManyToMany
    private List<Usuario> usuarios;

    public CuentaMP() {}
}
