package arqui.web.grupo_9.usuario.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "cuenta_mp")
public class CuentaMP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta_mp")
    private Long idCuentaMP;

    @Column
    private Double saldo;

    @Column(name = "fecha_inhabilitada")
    private LocalDate fechaInahilitada;

    @Column
    private boolean inhabilitada;

    @ManyToMany
    private List<Usuario> usuarios;

    public CuentaMP() {}

    public CuentaMP(Long idCuentaMP, Double saldo, List<Usuario> usuarios) {
        this.idCuentaMP = idCuentaMP;
        this.saldo = saldo;
        this.usuarios = usuarios;
    }

    public boolean tieneUsuario(Usuario u) {
        return this.usuarios.contains(u);
    }

    public void registrarUsuario(Usuario u) {
        this.usuarios.add(u);
    }

    public void eliminarUsuario(Usuario u) {
        this.usuarios.remove(u);
    }

    @Override
    public boolean equals(Object o) {
        CuentaMP otraCuenta = (CuentaMP) o;
        return this.idCuentaMP.equals(otraCuenta.getIdCuentaMP());
    }
}
