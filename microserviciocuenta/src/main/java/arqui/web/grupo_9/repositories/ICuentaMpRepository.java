package arqui.web.grupo_9.usuario.repositories;

import arqui.web.grupo_9.usuario.model.entities.CuentaMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaMpRepository extends JpaRepository<CuentaMP, Long> {
}
