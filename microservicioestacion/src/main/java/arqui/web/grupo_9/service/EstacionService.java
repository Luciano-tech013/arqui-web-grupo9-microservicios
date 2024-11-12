package arqui.web.grupo_9.service;

import arqui.web.grupo_9.clients.MonopatinFeignClient;
import arqui.web.grupo_9.model.clients.MonopatinDTO;
import arqui.web.grupo_9.model.entities.Estacion;
import arqui.web.grupo_9.repository.IEstacionRepository;
import arqui.web.grupo_9.service.exceptions.NotFoundEstacionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstacionService {

    private IEstacionRepository estacionRepository;
    private MonopatinFeignClient monopatinClient;

    public EstacionService(IEstacionRepository estacionRepository, @Lazy MonopatinFeignClient monopatinClient) {
        this.estacionRepository = estacionRepository;
        this.monopatinClient = monopatinClient;
    }

    public List<Estacion> findAll() {
        return this.estacionRepository.findAll();
    }

    public Estacion findById(Long id) {
        Optional<Estacion> estacion = this.estacionRepository.findById(id);
        if(estacion.isPresent())
            return estacion.get();

        throw new NotFoundEstacionException("La estacion con ese id no esta cargada en el sistema", "No se encontro la estacion con ese id solicitado. Por favor, verfica que este cargada en el sistema", "low");
    }

    public boolean save(Estacion estacion) {
        this.estacionRepository.save(estacion);
        return true;
    }

    public boolean deleteById(Long id) {
        this.findById(id);
        this.estacionRepository.deleteById(id);
        return true;
    }

    public boolean updateById(Long id, Estacion eModified) {
        Estacion estacion = this.findById(id);

        estacion.setIdEstacion(eModified.getIdEstacion());
        estacion.setNombre(eModified.getNombre());
        estacion.setLatitud(eModified.getLatitud());
        estacion.setLongitud(eModified.getLongitud());
        return this.save(estacion);
    }

    public List<Estacion> getEstacionesByUbicacion(Double latitud, Double longitud) {
        return this.estacionRepository.getEstacionsByUbicacion(latitud, longitud);
    }

    public List<MonopatinDTO> getMonopatinesByUbicacion(double latitud, double longitud) {
        return this.monopatinClient.getMonopatinesByUbicacion(latitud, longitud).getBody();
    }

}
