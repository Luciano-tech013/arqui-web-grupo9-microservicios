package arqui.web.grupo_9.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@AllArgsConstructor
@Getter
public class Estacion {
    @Id
    private Long idEstacion;
    @Column
    private String nombre;
    @Column
    private Double latitud;
    @Column
    private Double longitud;

    public Estacion() {}
}
