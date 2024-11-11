package arqui.web.grupo_9.service;

import arqui.web.grupo_9.model.entities.Monopatin;
import arqui.web.grupo_9.repository.IMonopatinRepository;
import arqui.web.grupo_9.service.exceptions.NotFoundMonopatinException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonopatinService {

    private IMonopatinRepository repository;


    public MonopatinService(IMonopatinRepository repository) {
        this.repository = repository;
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
}
