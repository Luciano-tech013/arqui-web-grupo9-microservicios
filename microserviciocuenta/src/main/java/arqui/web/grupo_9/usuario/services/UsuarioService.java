package arqui.web.grupo_9.usuario.services;

import arqui.web.grupo_9.usuario.model.entities.Usuario;
import arqui.web.grupo_9.usuario.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private IUsuarioRepository repository;

    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        return this.repository.findAll();
    }
}
