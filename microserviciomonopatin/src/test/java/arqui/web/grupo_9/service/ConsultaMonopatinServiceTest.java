package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.dto.ReporteUsoDTO;
import arqui.web.grupo_9.model.entities.Monopatin;
import arqui.web.grupo_9.repository.IMonopatinRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConsultaMonopatinServiceTest {
    @Autowired
    private MonopatinService service;

    @Autowired
    private IMonopatinRepository repository;

    @Test
    @DisplayName("Test para obtener cantidad de monopatines en operacion y cantidad de monopatines en matenimiento")
    void testGetCantMonopatinesByOperacionAndMantenimiento() {
        Monopatin m1 = new Monopatin(1L, 12.00, 134.20, 92.31, "operacion");
        Monopatin m2 = new Monopatin(2L, 9.02, 77.90, 389.01, "mantenimiento");
        Monopatin m3 = new Monopatin(3L, 9.00, 205.45, 201.03, "mantenimiento");

        this.service.save(m1);
        this.service.save(m2);
        this.service.save(m3);

        int cantEnOperacion = this.service.getMonopatinesByEstado("operacion").size();
        int cantEnMantenimiento = this.service.getMonopatinesByEstado("mantenimiento").size();

        assertEquals(1, cantEnOperacion);
        assertEquals(2, cantEnMantenimiento);
    }

    @Test
    @DisplayName("Test para obtener los monopatines por kms recorridos sin tiempo pausa")
    void testGetMonopatinesByUsoSinTiempoPausa() {
        Monopatin m1 = new Monopatin(1L, 12.00, 134.20, 92.31, "operacion");
        Monopatin m2 = new Monopatin(2L, 9.02, 77.90, 389.01, "operacion");
        Monopatin m3 = new Monopatin(3L, 16.70, 205.45, 201.03, "operacion");

        this.service.save(m1);
        this.service.save(m2);
        this.service.save(m3);

        List<ReporteUsoDTO> monopatines = this.service.getMonopatinesByUso(false);

        List<Monopatin> resultado = List.of(m3, m1, m2);

        assertEquals(monopatines.get(0).getIdMonopatin(), resultado.get(0).getIdMonopatin());
        assertEquals(monopatines.get(0).getKmsRecorridos(), resultado.get(0).getKmsRecorridos());
        assertEquals(monopatines.get(1).getIdMonopatin(), resultado.get(1).getIdMonopatin());
        assertEquals(monopatines.get(1).getKmsRecorridos(), resultado.get(1).getKmsRecorridos());
        assertEquals(monopatines.get(2).getIdMonopatin(), resultado.get(2).getIdMonopatin());
        assertEquals(monopatines.get(2).getKmsRecorridos(), resultado.get(2).getKmsRecorridos());
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }
}
