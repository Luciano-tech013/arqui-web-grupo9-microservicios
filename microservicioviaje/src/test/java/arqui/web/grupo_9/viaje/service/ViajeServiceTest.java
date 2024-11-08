package arqui.web.grupo_9.viaje.service;

import arqui.web.grupo_9.viaje.model.entities.Viaje;
import arqui.web.grupo_9.viaje.repository.IViajeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ViajeServiceTest {
    @Autowired
    private IViajeRepository repository;

    @Autowired
    private ViajeService service;

    @BeforeEach
    void setUp() {
        this.service = new ViajeService(this.repository);
    }

    @DisplayName("Test para registrar un viaje finalizado")
    @Test
    void testSaveViaje() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1256L, 1L);

        boolean saved = this.service.save(viaje);

        assertTrue(saved);
    }

    @DisplayName("Test para obtener una lista de viajes")
    @Test
    void testFindAllViajes() {
        Viaje viaje1 = new Viaje(LocalDateTime.now(), 1222L, 1L);
        Viaje viaje2 = new Viaje(LocalDateTime.now(), 1256L, 2L);

        this.repository.save(viaje1);
        this.repository.save(viaje2);

        assertEquals(4, this.service.findAll().size());
    }

    @DisplayName("Test para obtener un viaje especifico")
    @Test
    void testFindById() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1132L, 1L);
        this.repository.save(viaje);

        Viaje vFinded = this.service.findById(viaje.getIdViaje());

        assertEquals(viaje.getIdViaje(), vFinded.getIdViaje());
    }

    @DisplayName("Test para eliminar un viaje por su identificador")
    @Test
    void testDeleteById() {
        Long id = 14L;

        boolean deleted = this.service.deleteById(id);

        if (deleted)
            assertFalse(this.repository.findById(id).isPresent());
    }

    @DisplayName("Test para generar y finalizar un viaje con programación")
    @Test
    void testGenerarFinalizarViaje() throws InterruptedException {
        this.service.generar(1009L, 3L);

        // Espera un tiempo para que el método programado se ejecute al menos una vez
        TimeUnit.SECONDS.sleep(15); // Ajusta según el intervalo configurado

        this.service.finalizar();
        Viaje viaje = this.service.findById(service.getIdViaje());

        assertEquals(150, this.service.getCreditoDescontado());
        assertEquals(15, this.service.getKmsRecorridos());
        assertNotNull(viaje);
        assertEquals(150, viaje.getCostoTotal());
        assertEquals(15, viaje.getKmsRecorridos());
    }


    /*@AfterEach
    public void tearDown() {
        this.repository.deleteAll();
    }*/
}
