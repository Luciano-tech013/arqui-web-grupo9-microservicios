package arqui.web.grupo_9.usuario.repositories;

import arqui.web.grupo_9.usuario.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
