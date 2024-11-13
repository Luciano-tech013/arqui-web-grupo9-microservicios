package arqui.web.grupo_9.service;

import arqui.web.grupo_9.client.ViajeFeignClient;
import arqui.web.grupo_9.model.dto.MonopatinDTO;
import arqui.web.grupo_9.model.dto.ReporteUsoDTO;
import arqui.web.grupo_9.model.entities.Monopatin;
import arqui.web.grupo_9.repository.IMonopatinRepository;
import arqui.web.grupo_9.service.exceptions.NotFoundMonopatinException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MonopatinService {

    private IMonopatinRepository repository;
    private ViajeFeignClient viajeClient;


    public MonopatinService(IMonopatinRepository repository, @Lazy ViajeFeignClient viajeClient) {
        this.repository = repository;
        this.viajeClient = viajeClient;
    }

    public List<Monopatin> findAll() {
        return this.repository.findAll();
    }

    public Monopatin findById(Long id) {
        Optional<Monopatin> monopatin = this.repository.findById(id);
        if(monopatin.isPresent())
            return monopatin.get();

        throw new NotFoundMonopatinException("No esta cargado en el sistema el monopatin con el id solicitado", "No se encontro el monopatin con el id solicitado. Por favor, verifica que este cargado en el sistema", "low");
    }

    public boolean save(Monopatin monopatin) {
        this.repository.save(monopatin);
        return true;
    }

    public boolean deleteByid(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
        return true;
    }

    public boolean updateById(Long id, Monopatin mModified) {
        Monopatin monopatin = this.findById(id);

        monopatin.setIdMonopatin(mModified.getIdMonopatin());
        monopatin.setKmsRecorridos(mModified.getKmsRecorridos());
        monopatin.setLatitud(mModified.getLatitud());
        monopatin.setLongitud(mModified.getLongitud());
        monopatin.setEstado(mModified.getEstado());
        return this.save(monopatin);
    }

    public List<Monopatin> getMonopatinesByEstado(String estado) {
        return this.repository.findAllEstadoMonopatines(estado.toLowerCase());
    }

    public List<Monopatin> getMonopatinesByUbicacion(double latitud, double longitud) {
        return this.repository.findAllByUbicacion(latitud, longitud);
    }

    public List<ReporteUsoDTO> getMonopatinesByUso(boolean conPausa) {
        List<ReporteUsoDTO> reportes = new LinkedList<>();

        //obtengo los monopatines en operacion ordenados por mayor cantidad de kms recorridos
        List<Monopatin> monopatines = this.repository.findAllByUso();

        //Si la tabla de monopatines no tiene nada, me ahorro null pointer exception
        if(monopatines.isEmpty())
            return null;

        //por cada monopatin obtenido
        for(Monopatin m : monopatines) {
            //Si solicitaron incluir tiempo con pausa
            if(conPausa) {
                //obtengo el tiempo total pausado de ese monopatin pegandole al microservicio de viajes, quien es el que posee ese dato y me lo devuelve ya calculado
                Duration tiempoConPausa = this.viajeClient.getTiempoTotalPausadoDeMonopatin(m.getIdMonopatin()).getBody();
                //genero el reporte con los kms y el tiempo de uso
                reportes.add(new ReporteUsoDTO(m.getIdMonopatin(), m.getKmsRecorridos(), tiempoConPausa, m.getEstado()));
            } else {
                //Si no, genero el reporte solamente con los kms
                reportes.add(new ReporteUsoDTO(m.getIdMonopatin(), m.getKmsRecorridos(), null, m.getEstado()));
            }
        }

        return reportes;
    }
}
