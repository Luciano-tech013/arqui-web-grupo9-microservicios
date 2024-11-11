package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.entities.Viaje;
import arqui.web.grupo_9.repository.IViajeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrudViajeServiceTest {
    @Autowired
    private ViajeService service;

    private IViajeRepository repository;

    @Test
    @DisplayName("Test para obtencion de un viaje por su identificador")
    void testFindById() {
        Long id = 1L;

        Viaje viaje = this.service.findById(id);

        assertEquals(id, viaje.getIdViaje());
    }
}
