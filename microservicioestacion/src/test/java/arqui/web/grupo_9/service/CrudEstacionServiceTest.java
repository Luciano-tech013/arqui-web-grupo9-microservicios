package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.entities.Estacion;
import arqui.web.grupo_9.repository.IEstacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = arqui.web.grupo_9.EstacionApplication.class)
public class CrudEstacionServiceTest {
    @Autowired
    private EstacionService service;

    @Autowired
    private IEstacionRepository repository;

    @Test
    @DisplayName("Test para guardar una estacion en el sistema")
    void testSaveEstacion() {
        Estacion e = new Estacion(1L, "Estacion 1", 348.90, 237.72);

        assertTrue(this.service.save(e));
    }

    @Test
    @DisplayName("Test para obtener una estacion por su identificador")
    void testFindByIdEstacion() {
        Estacion e = new Estacion(2L, "Estacion 2", 128.47, 137.91);

        this.service.save(e);
        Estacion eFinded = this.service.findById(2L);

        assertEquals(e.getIdEstacion(), eFinded.getIdEstacion());
        assertEquals(e.getNombre(), eFinded.getNombre());
        assertEquals(e.getLatitud(), eFinded.getLatitud());
        assertEquals(e.getLongitud(), eFinded.getLongitud());
    }

    @Test
    @DisplayName("Test para obtener todas las estaciones cargadas en el sistema")
    void testFindAll() {
        Estacion e3 = new Estacion(3L, "Estacion 3", 456.35, 429.02);

        this.service.save(e3);
        List<Estacion> estaciones = this.service.findAll();

        assertEquals(3, estaciones.size());
    }

    @Test
    @DisplayName("Test para eliminar una estacion por su identificador")
    void testDeletebyIdEstacion() {
        Long id = 3L;

        this.service.deleteById(id);

        this.service.findById(id);
    }

    @Test
    @DisplayName("Test para actualizar una estacion por su identificador")
    void testUpdateById() {
        Long id = 2L;
        Estacion eModified = new Estacion(id, "Estacion Periclo", 128.47, 141.23);

        this.service.updateById(id, eModified);
        Estacion eFinded = this.service.findById(id);

        assertEquals(eModified.getNombre(), eFinded.getNombre());
        assertEquals(eModified.getLatitud(), eFinded.getLatitud());
        assertEquals(eModified.getLongitud(), eFinded.getLongitud());
    }
}
