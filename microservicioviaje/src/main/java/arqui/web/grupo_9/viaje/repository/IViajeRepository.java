package arqui.web.grupo_9.viaje.repository;

import arqui.web.grupo_9.viaje.model.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IViajeRepository extends JpaRepository<Viaje, Long> {
    @Query("SELECT v.idMonopatin FROM Viaje v WHERE YEAR(v.fechaFinViaje) = :anio GROUP BY v.idMonopatin HAVING COUNT(v.idViaje) > :cantViajes")
    List<Long> getMonopatinesByCantViajesAndAnio(int anio, int cantViajes);

    @Query("SELECT SUM(v.costoTotal) FROM Viaje v WHERE MONTH(v.fechaFinViaje) > :mesIni AND MONTH(v.fechaFinViaje) < :mesFin AND YEAR(v.fechaFinViaje) = :anio")
    Double getTotalFacturadoByMesesOfAnio(int mesIni, int mesFin, int anio);
}
