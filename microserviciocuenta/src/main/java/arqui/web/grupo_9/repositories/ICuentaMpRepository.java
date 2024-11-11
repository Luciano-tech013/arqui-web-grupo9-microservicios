package arqui.web.grupo_9.repositories;

import arqui.web.grupo_9.model.entities.CuentaMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaMpRepository extends JpaRepository<CuentaMP, Long> {
}
