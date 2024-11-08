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

        return new UsuarioDTO(e.getNombre(), e.getApellido(), e.getNroCelular(), e.getEmail());
    }

    @Override
    public Usuario fromDTO(UsuarioDTO d) {
        return null;
    }
}
