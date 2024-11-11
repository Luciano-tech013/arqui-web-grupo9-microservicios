package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.entities.Estacion;
import arqui.web.grupo_9.repository.IEstacionRepository;
import arqui.web.grupo_9.service.exceptions.NotFoundEstacionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacionService {

    private IEstacionRepository estacionRepository;

    public EstacionService(IEstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
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

        return true;
    }

    public List<Estacion> getEstacionsByUbicacion(Double latitud, Double longitud) {
        return this.estacionRepository.getEstacionsByUbicacion(latitud, longitud);
    }

}
