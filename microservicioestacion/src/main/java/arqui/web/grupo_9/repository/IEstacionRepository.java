package arqui.web.grupo_9.repository;

import arqui.web.grupo_9.model.entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEstacionRepository extends JpaRepository<Estacion, Long> {

    @Query("Select e FROM Estacion e WHERE e.latitud = :latitud AND e.longitud = :longitud")
    public List<Estacion> getEstacionsByUbicacion(Double latitud, Double longitud);
}
