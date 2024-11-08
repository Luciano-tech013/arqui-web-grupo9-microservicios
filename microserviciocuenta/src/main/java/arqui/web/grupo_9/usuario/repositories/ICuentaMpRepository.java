package arqui.web.grupo_9.usuario.repositories;

import arqui.web.grupo_9.usuario.model.entities.CuentaMP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaMpRepository extends JpaRepository<CuentaMP, Long> {
}
