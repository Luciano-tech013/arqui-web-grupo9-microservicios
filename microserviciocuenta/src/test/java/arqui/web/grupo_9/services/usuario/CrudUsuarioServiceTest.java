package arqui.web.grupo_9.services.usuario;

import arqui.web.grupo_9.model.entities.CuentaMP;
import arqui.web.grupo_9.model.entities.Usuario;
import arqui.web.grupo_9.repositories.IUsuarioRepository;
import arqui.web.grupo_9.services.CuentaMpService;
import arqui.web.grupo_9.services.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CrudUsuarioServiceTest {
    @Autowired
    private UsuarioService service;

    @Autowired
    private CuentaMpService cuentaService;

    @Autowired
    private IUsuarioRepository repository;

    @Test
    @DisplayName("Test para guardar un usuario en el sistema")
    void testSaveUsuario() {
        Usuario u = new Usuario(1L, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com");

        assertTrue(this.service.save(u));
    }

    @Test
    @DisplayName("Test para buscar un usuario por su identificador")
    void testFindByIdUsuario() {
        Long id = 1L;

        this.service.save(new Usuario(1L, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com"));
        Usuario usuario = this.service.findById(id);

        assertEquals(id, usuario.getIdUsuario());
    }

    @Test
    @DisplayName("Test para buscar todos los usuarios cargados en el sistema")
    void testFindAllUsuarios() {
        Usuario u1 = new Usuario(1L, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com");
        Usuario u2 = new Usuario(2L, "Adrian", "Torres", "2494658468", "adriant@gmail.com");

        this.service.save(u1);
        this.service.save(u2);

        List<Usuario> usuarios = this.service.findAll();
        assertEquals(2, usuarios.size());
    }

    @Test
    @DisplayName("Test para eliminar un usuario por su identificador")
    void testDeleteByIdUsuario() {
        Long id = 1L;

        this.service.save(new Usuario(id, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com"));
        this.service.deleteById(id);

        this.service.findById(id);
    }

    @Test
    @DisplayName("Test para actualizar un usuario por su identificador")
    void testUpdateByIdUsuario() {
        Long id = 1L;
        Usuario uModified = new Usuario(id, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com");

        this.service.save(new Usuario(id, "Alejandro", "Lopez", "2494014611", "tluciano943@gmail.com"));
        this.service.updateById(id, uModified);
        Usuario uFinded = this.service.findById(id);

        assertEquals(uModified.getNombre(), uFinded.getNombre());
        assertEquals(uModified.getApellido(), uFinded.getApellido());
        assertEquals(uModified.getNroCelular(), uFinded.getNroCelular());
        assertEquals(uModified.getEmail(), uFinded.getEmail());
    }

    @Test
    @DisplayName("Test para asociar una cuenta a un usuario")
    void testAsociarCuenta() {
        Usuario u1 = new Usuario(1L, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com");
        CuentaMP c1 = new CuentaMP(1L, 200.00);

        this.service.save(u1);
        this.cuentaService.saveCuenta(c1);
        this.service.asociarCuenta(u1.getIdUsuario(), c1.getIdCuentaMP());

        assertTrue(u1.tieneCuenta(c1));
        assertTrue(c1.tieneUsuario(u1));
        assertEquals(1, u1.getCuentasMp().size());
        assertEquals(1, c1.getUsuarios().size());
    }

    @Test
    @DisplayName("Test para eliminar una cuenta asociada a un usuario")
    void testEliminarCuentaAsociadaAUsuario() {
        Usuario u1 = new Usuario(1L, "Luciano", "Torres", "2494014611", "tluciano943@gmail.com");
        CuentaMP c1 = new CuentaMP(1L, 200.00);
        Usuario u2 = new Usuario(2L, "Majo", "Dicj", "2494658468", "majolafalca@gmail.com");

        this.cuentaService.crearCuenta(u1.getIdUsuario());
        this.service.asociarCuenta(u2.getIdUsuario(), c1.getIdCuentaMP());

        this.service.eliminarCuentaDeUsuario(u1.getIdUsuario(), c1.getIdCuentaMP());

        assertFalse(u1.tieneCuenta(c1));
        assertFalse(c1.tieneUsuario(u1));
        assertEquals(1, c1.getUsuarios().size());
        assertEquals(0, u1.getCuentasMp().size());
    }

    /*@AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }*/
}
