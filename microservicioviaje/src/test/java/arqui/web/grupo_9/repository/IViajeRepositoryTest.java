package arqui.web.grupo_9.viaje.repository;

import arqui.web.grupo_9.viaje.model.entities.Viaje;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IViajeRepositoryTest {

    @Autowired
    private IViajeRepository repository;

    @DisplayName("Test para guardar un viaje en la base de datos")
    @Test
    void testSaveViaje() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1230L, 0001L);

        Viaje viajeSaved = this.repository.save(viaje);

        assertNotNull(viajeSaved);
        assertEquals(viaje.getIdViaje(), viajeSaved.getIdViaje());
    }

    @DisplayName("Test para listar todos los viajes registrados en la base de datos")
    @Test
    void testFindAllViaje() {
        Viaje viaje1 = new Viaje(LocalDateTime.now(), 1243L, 1L);
        Viaje viaje2 = new Viaje(LocalDateTime.now(), 1237L, 2L);

        this.repository.save(viaje1);
        this.repository.save(viaje2);

        List<Viaje> viajes = this.repository.findAll();
        assertEquals(viajes.size(), 7);
    }

    @DisplayName("Test para buscar un viaje por su identificador")
    @Test
    void testFindbyIdViaje() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1247L, 3L);

        this.repository.save(viaje);
        Optional<Viaje> viajeFinded = this.repository.findById(viaje.getIdViaje());

        assertTrue(viajeFinded.isPresent());
        assertEquals(viaje.getIdViaje(), viajeFinded.get().getIdViaje());
    }

    @DisplayName("Test para eliminar un viaje por su identificador")
    @Test
    void testDeleteByIdViaje() {
        Long id = 5L;

        this.repository.deleteById(id);

        Optional<Viaje> viajeDeleted = this.repository.findById(id);
        assertFalse(viajeDeleted.isPresent());
    }

    @DisplayName("Test para actualizar un viaje por su identificador")
    @Test
    void testUpdateById() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1228L, 3L);
        this.repository.save(viaje);

        Viaje viajeFinded = this.repository.findById(viaje.getIdViaje()).get();
        viajeFinded.setCostoTotal(2000.00);
        viajeFinded.setKmsRecorridos(2.0);
        viajeFinded.setFechaFinViaje(LocalDateTime.now());
        Viaje viajeSaved = this.repository.save(viajeFinded);

        assertNotEquals(viaje.getCostoTotal(), viajeSaved.getCostoTotal());
        assertNotEquals(viaje.getKmsRecorridos(), viajeSaved.getKmsRecorridos());
        assertNotEquals(viaje.getFechaFinViaje(), viajeSaved.getFechaFinViaje());
    }


}
