package arqui.web.grupo_9.usuario.controllers;

import arqui.web.grupo_9.usuario.model.dto.UsuarioDTO;
import arqui.web.grupo_9.usuario.model.dto.converters.UsuarioConverter;
import arqui.web.grupo_9.usuario.model.entities.Usuario;
import arqui.web.grupo_9.usuario.services.UsuarioService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    private UsuarioConverter converter;

    public UsuarioController(UsuarioService usuarioService, @Lazy UsuarioConverter converter) {
        this.usuarioService = usuarioService;
        this.converter = converter;
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<Usuario> usuarios = this.usuarioService.findAll();
        if(usuarios.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(usuarios), HttpStatus.OK);
    }
}
