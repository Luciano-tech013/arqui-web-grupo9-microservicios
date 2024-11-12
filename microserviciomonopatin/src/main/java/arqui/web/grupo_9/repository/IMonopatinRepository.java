package arqui.web.grupo_9.repository;

import arqui.web.grupo_9.model.entities.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query("SELECT m FROM Monopatin m WHERE m.estado = :estado")
    List<Monopatin> findAllEstadoMonopatines(String estado);

    @Query("SELECT m FROM Monopatin WHERE m.latitud >= :latitud AND m.longitud <= :longitud")
    List<Monopatin> findAllByUbicacion(double latitud, double longitud);

}
