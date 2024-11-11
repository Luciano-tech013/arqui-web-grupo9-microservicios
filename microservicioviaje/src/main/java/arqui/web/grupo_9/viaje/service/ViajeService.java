package arqui.web.grupo_9.viaje.service;

import arqui.web.grupo_9.viaje.model.entities.Viaje;
import arqui.web.grupo_9.viaje.repository.IViajeRepository;
import arqui.web.grupo_9.viaje.service.exceptions.NoContentViajeException;
import arqui.web.grupo_9.viaje.service.exceptions.NotFoundViajeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {
    private IViajeRepository repository;

    public ViajeService(IViajeRepository repository) {
        this.repository = repository;
    }

    public List<Viaje> findAll() {
        List<Viaje> viajes = this.repository.findAll();
        if(viajes.isEmpty())
            throw new NoContentViajeException("La tabla de viaje esta vacia", "No se ah generado ningun viaje. Para ver esta informacion, deben existir viajes finalizados", "low");

        return viajes;
    }

    public Viaje findById(Long idViaje) {
        Optional<Viaje> viaje = this.repository.findById(idViaje);
        if(viaje.isPresent())
            return viaje.get();

        throw new NotFoundViajeException("El id enviado es incorrecto, no existe en la tabla viaje", "El viaje solicitado nunca fue generado. Por favor, solicita un viaje finalizado", "low");
    }

    public boolean save(Viaje viaje) {
        this.repository.save(viaje);
        return true;
    }

    public boolean deleteById(Long idViaje) {
        this.findById(idViaje);

        this.repository.deleteById(idViaje);

        return true;
    }

    public boolean updateById(Long idViaje, Viaje viajeModified) {
        Viaje viaje = this.findById(idViaje);

        viaje.setFechaIniViaje(viajeModified.getFechaIniViaje());
        viaje.setFechaFinViaje(viajeModified.getFechaFinViaje());
        viaje.setKmsRecorridos(viajeModified.getKmsRecorridos());
        viaje.setCostoTotal(viajeModified.getCostoTotal());
        viaje.setFechaIniViajeConPausa(viajeModified.getFechaIniViajeConPausa());
        viaje.setFechaFinViajeConPausa(viajeModified.getFechaFinViajeConPausa());
        viaje.setIdUsuario(viajeModified.getIdUsuario());
        viaje.setIdMonopatin(viajeModified.getIdMonopatin());

        return this.save(viaje);
    }
}
