package arqui.web.grupo_9.usuario.repository;

import arqui.web.grupo_9.usuario.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
}
