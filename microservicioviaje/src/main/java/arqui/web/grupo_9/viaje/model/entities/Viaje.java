package arqui.web.grupo_9.viaje.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Long idViaje;

    @Column(name = "fecha_ini_viaje")
    private LocalDateTime fechaIniViaje;

    @Column(name = "fecha_fin_viaje")
    private LocalDateTime fechaFinViaje;

    @Column(name = "kms_recorridos")
    private Double kmsRecorridos;

    @Column(name = "costo_total")
    private Double costoTotal;

    @Column(name = "fecha_ini_viaje_con_pausa")
    private LocalDateTime fechaIniViajeConPausa;

    @Column(name = "fecha_fin_viaje_con_pausa")
    private LocalDateTime fechaFinViajeConPausa;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_monopatin")
    private Long idMonopatin;

    @Transient
    private static final double PRECIO_BASE = 0.00;

    @Transient
    private static final double PRECIO_RECARGO = 0.00;

    @Transient
    private static final double TIEMPO_PAUSA_PERMITIDO = 15;

    /* Aplico recargo se puede calcular*/

    public Viaje() {}
}
