package arqui.web.grupo_9.usuario.model.dto.converters;

import arqui.web.grupo_9.usuario.model.dto.UsuarioDTO;
import arqui.web.grupo_9.usuario.model.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends ConverterDTO<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO fromEntity(Usuario e) {
        if(e == null)
            return null;

        return new UsuarioDTO(e.getNombre(), e.getApellido(), e.getNroCelular(), e.getEmail(), e.getCuentasMp());
    }

    @Override
    public Usuario fromDTO(UsuarioDTO d) {
        if(d == null)
            return null;

        return new Usuario(null, d.getNombre(), d.getApellido(), d.getNroCelular(), d.getEmail(), d.getCuentasMp());
    }
}
