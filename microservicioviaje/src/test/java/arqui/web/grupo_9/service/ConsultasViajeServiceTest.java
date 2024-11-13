package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.clients.MonopatinDTO;
import arqui.web.grupo_9.model.entities.Viaje;
import arqui.web.grupo_9.repository.IViajeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConsultasViajeServiceTest {
    @Autowired
    private ViajeService service;

    private IViajeRepository repository;

    @Test
    @DisplayName("Test para obtener los monopatines con X cantidad de viajes en un año")
    void testGetMonopatinesByCantViajesInOneYear() {
        int anio = 2024;
        int cantViajes = 2;

        Viaje viaje = new Viaje(LocalDateTime.now(), 102L, 3L);
        viaje.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(4, 30, 15, 18)));

        Viaje viaje2 = new Viaje(LocalDateTime.now(), 245L, 2L);
        viaje2.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 11, 23), LocalTime.of(2, 45, 20, 30)));

        Viaje viaje3 = new Viaje(LocalDateTime.now(), 790L, 3L);
        viaje3.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 6, 17), LocalTime.of(9, 10, 27, 11)));

        Viaje viaje4 = new Viaje(LocalDateTime.now(), 233L, 2L);
        viaje4.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 5, 2), LocalTime.of(17, 12, 9, 4)));

        Viaje viaje5 = new Viaje(LocalDateTime.now(), 567L, 4L);
        viaje5.setFechaFinViajeConPausa(LocalDateTime.of(LocalDate.of(2024, 7, 22), LocalTime.of(14, 13, 34, 42)));

        this.service.save(viaje);
        this.service.save(viaje2);
        this.service.save(viaje3);
        this.service.save(viaje4);
        this.service.save(viaje5);
        List<Long> idMonopatines = List.of(viaje2.getIdMonopatin(), viaje3.getIdMonopatin());
        List<MonopatinDTO> resultado = this.service.getMonopatinesByCantViajesInOneYear(anio, cantViajes);

        assertEquals(idMonopatines, resultado);
    }

    @Test
    @DisplayName("Test para obtener el total facturado en un rango de meses de un año determinado")
    void testGetTotalFacturadoByMesesInOneYear() {
        double totalFacturado = 1457.00;

        Viaje viaje = new Viaje(LocalDateTime.now(), 567L, 2L);
        viaje.setCostoTotal(1200.00);
        viaje.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 11, 23), LocalTime.of(2, 45, 20, 30)));

        Viaje viaje2 = new Viaje(LocalDateTime.now(), 738L, 5L);
        viaje2.setCostoTotal(257.00);
        viaje2.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2024, 10, 15), LocalTime.of(7, 13, 20, 36)));

        Viaje viaje3 = new Viaje(LocalDateTime.now(), 1456L, 7L);
        viaje3.setCostoTotal(781.00);
        viaje3.setFechaFinViaje(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(18, 10, 20, 24)));

        this.service.save(viaje);
        this.service.save(viaje2);
        this.service.save(viaje3);
        double resultado = this.service.getTotalFacturadoByMesesInOneYear(5, 6, -2024);

        assertEquals(totalFacturado, resultado);
    }

    @Test
    @DisplayName("Test para obtener el tiempo total de pausa de un monopatin")
    void testGetTiempoTotalPausadoDeMonopatin() {
        Viaje viaje = new Viaje(LocalDateTime.now(), 1456L, 1L);
        viaje.setFechaIniViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(18, 10, 20, 24)));
        viaje.setFechaFinViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(19, 13, 28, 44)));

        Viaje viaje2 = new Viaje(LocalDateTime.now(), 1176L, 1L);
        viaje2.setFechaIniViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(14, 24, 10, 4)));
        viaje2.setFechaFinViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(16, 18, 29, 48)));

        Viaje viaje3 = new Viaje(LocalDateTime.now(), 1681L, 2L);
        viaje3.setFechaIniViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(18, 10, 20, 24)));
        viaje3.setFechaFinViajeConPausa(LocalDateTime.of(LocalDate.of(2023, 9, 28), LocalTime.of(19, 13, 28, 44)));

        this.service.save(viaje);
        this.service.save(viaje2);
        this.service.save(viaje3);

        Duration d = this.service.getTiempoTotalPausadoDeMonopatin(1L);

        // Verifica que la duración total tenga 2 horas y 51 minutos
        assertEquals(2L, d.toHoursPart()); // Parte de horas
        assertEquals(57L, d.toMinutesPart()); // Parte de minutos
    }
}
