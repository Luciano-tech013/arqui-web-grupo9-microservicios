package arqui.web.grupo_9.services;

import arqui.web.grupo_9.model.entities.CuentaMP;
import arqui.web.grupo_9.model.entities.Usuario;
import arqui.web.grupo_9.repositories.IUsuarioRepository;
import arqui.web.grupo_9.services.exceptions.CouldNotRegisterException;
import arqui.web.grupo_9.services.exceptions.NotFoundCuentaException;
import arqui.web.grupo_9.services.exceptions.NotFoundUsuarioException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private IUsuarioRepository repository;
    private CuentaMpService cuentaMpService;

    public UsuarioService(IUsuarioRepository repository, @Lazy CuentaMpService cuentaMpService) {
        this.repository = repository;
        this.cuentaMpService = cuentaMpService;
    }

    public List<Usuario> findAll() {
        return this.repository.findAll();
    }

    public Usuario findById(Long id) {
        Optional<Usuario> u = this.repository.findById(id);
        if(u.isPresent())
            return u.get();

        throw new NotFoundUsuarioException("El usuario solicitado no esta cargado en el sistema", "El usuario solicitado no existe. Por favor, verifica que este cargado en el sistema", "low");
    }

    public boolean save(Usuario usuario) {
        this.repository.save(usuario);
        return true;
    }

    public boolean deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
        return true;
    }

    public boolean updateById(Long id, Usuario usuarioModified) {
        Usuario u = this.findById(id);
        if(u == null)
            return false;

        u.setIdUsuario(usuarioModified.getIdUsuario());
        u.setNombre(usuarioModified.getNombre());
        u.setApellido(usuarioModified.getApellido());
        u.setEmail(usuarioModified.getEmail());
        u.setNroCelular(usuarioModified.getNroCelular());
        u.setCuentasMp(u.getCuentasMp());

        return this.save(u);
    }

    public boolean asociarCuenta(Long idUsuario, Long idCuentaMP) {
        Usuario u = this.findById(idUsuario);
        CuentaMP cuenta = this.cuentaMpService.findByIdCuenta(idCuentaMP);

        cuenta.registrarUsuario(u);
        u.asociarCuenta(cuenta);
        this.save(u);
        this.cuentaMpService.saveCuenta(cuenta);

        return cuenta.tieneUsuario(u);
    }

    public boolean eliminarCuentaDeUsuario(Long idUsuario, Long idCuentaMP) {
        CuentaMP cuenta = this.cuentaMpService.findByIdCuenta(idCuentaMP);
        Usuario usuario = this.findById(idUsuario);

        if(!usuario.tieneCuenta(cuenta))
            throw new NotFoundCuentaException("La cuenta solicitada para eliminar no esta cargada en el sistema", "La cuenta que intentas eliminar no esta asociada al usuario indicado", "low");

        usuario.eliminarCuenta(cuenta);
        cuenta.eliminarUsuario(usuario);

        if(usuario.tieneCuenta(cuenta))
            throw new CouldNotRegisterException("No se puedo deshacer la cuenta del usuario indicado", "No se puedo eliminar la cuenta del usuario indicado. Por favor intenta mas tarde", "high");

        return this.save(usuario);
    }

}
