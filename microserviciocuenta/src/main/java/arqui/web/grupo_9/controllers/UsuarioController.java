package arqui.web.grupo_9.controllers;

import arqui.web.grupo_9.model.dto.UsuarioDTO;
import arqui.web.grupo_9.model.dto.converters.UsuarioConverter;
import arqui.web.grupo_9.model.entities.Usuario;
import arqui.web.grupo_9.services.CuentaMpService;
import arqui.web.grupo_9.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    private CuentaMpService cuentaService;
    private UsuarioConverter converter;

    public UsuarioController(UsuarioService usuarioService, @Lazy CuentaMpService cuentaService, @Lazy UsuarioConverter converter) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
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
        return new ResponseEntity<>(this.converter.fromEntity(u), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody @Valid UsuarioDTO u) {
        return new ResponseEntity<>(this.usuarioService.save(this.converter.fromDTO(u)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long idUsuario) {
        return new ResponseEntity<>(this.usuarioService.deleteById(idUsuario), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Boolean> updateById(@PathVariable Long idUsuario, @RequestBody @Valid UsuarioDTO uModified) {
        return new ResponseEntity<>(this.usuarioService.updateById(idUsuario, this.converter.fromDTO(uModified)), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}/asociar/cuenta/{idCuentaMP}")
    public ResponseEntity<Boolean> asociarCuentaAUsuario(@PathVariable Long idUsuario, @PathVariable Long idCuentaMP) {
        this.cuentaService.estaHabilitada(idCuentaMP);
        return new ResponseEntity<>(this.usuarioService.asociarCuenta(idUsuario, idCuentaMP), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}/eliminar/cuenta/{idCuentaMP}")
    public ResponseEntity<Boolean> eliminarCuentaDeUsuario(@PathVariable Long idUsuario, @PathVariable Long idCuentaMP) {
        //Interprete que el usuario puede desligarse de una cuenta inhabilitada, no hay que chekear
        return new ResponseEntity<>(this.usuarioService.eliminarCuentaDeUsuario(idUsuario, idCuentaMP), HttpStatus.OK);
    }
}
