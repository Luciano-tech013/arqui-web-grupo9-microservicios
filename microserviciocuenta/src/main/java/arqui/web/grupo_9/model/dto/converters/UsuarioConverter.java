package arqui.web.grupo_9.model.dto.converters;

import arqui.web.grupo_9.model.dto.UsuarioDTO;
import arqui.web.grupo_9.model.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends ConverterDTO<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO fromEntity(Usuario e) {
        if(e == null)
            return null;

        return new UsuarioDTO(e.getIdUsuario(), e.getNombre(), e.getApellido(), e.getNroCelular(), e.getEmail(), e.getCuentasMp());
    }

    @Override
    public Usuario fromDTO(UsuarioDTO d) {
        if(d == null)
            return null;

        return new Usuario(d.getIdUsuario(), d.getNombre(), d.getApellido(), d.getNroCelular(), d.getEmail());
    }
}
