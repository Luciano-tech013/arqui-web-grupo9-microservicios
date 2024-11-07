package arqui.web.grupo_9.viaje.repository;

import arqui.web.grupo_9.viaje.model.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IViajeRepository extends JpaRepository<Viaje, Long> {
}
