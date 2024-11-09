package arqui.web.grupo_9.usuario.controllers;

import arqui.web.grupo_9.usuario.model.dto.UsuarioDTO;
import arqui.web.grupo_9.usuario.model.dto.converters.UsuarioConverter;
import arqui.web.grupo_9.usuario.model.entities.Usuario;
import arqui.web.grupo_9.usuario.services.UsuarioService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    private UsuarioConverter converter;

    public UsuarioController(UsuarioService usuarioService, @Lazy UsuarioConverter converter) {
        this.usuarioService = usuarioService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<Usuario> usuarios = this.usuarioService.findAll();
        if(usuarios.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(this.converter.fromEntity(usuarios), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long idUsuario) {
        Usuario u = this.usuarioService.findById(idUsuario);
        return new ResponseEntity<>(this.converter.fromEntity(u), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody UsuarioDTO u) {
        return new ResponseEntity<>(this.usuarioService.save(this.converter.fromDTO(u)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idUsuario) {
        return new ResponseEntity<>(this.usuarioService.deleteById(idUsuario), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idUsuario, @RequestBody UsuarioDTO uModified) {
        return new ResponseEntity<>(this.usuarioService.updateById(idUsuario, this.converter.fromDTO(uModified)), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}/eliminar/cuenta/{idCuentaMP}")
    public ResponseEntity<Boolean> eliminarCuentaDeUsuario(@PathVariable Long idUsuario, @PathVariable Long idCuentaMP) {
        //Interprete que el usuario puede desligarse de una cuenta inhabilitada, no hay que chekear
        return new ResponseEntity<>(this.usuarioService.eliminarCuentaDeUsuario(idUsuario, idCuentaMP), HttpStatus.OK);
    }
}
