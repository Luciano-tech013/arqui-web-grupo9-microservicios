package arqui.web.grupo_9.usuario.model.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class CuentaMP {

    @Id
    private Long id_cuentaMP;
    @Column
    private Double saldo;
    @ManyToMany
    private List<Usuario> usuarios;

    public CuentaMP() {}
}
