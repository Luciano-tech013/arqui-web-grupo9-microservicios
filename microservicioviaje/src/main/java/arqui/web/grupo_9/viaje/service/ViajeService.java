package arqui.web.grupo_9.viaje.service;

/*import arqui.web.grupo_9.viaje.clients.MonopatinFeignClient;
import arqui.web.grupo_9.viaje.clients.UsuarioFeignClient;*/
import arqui.web.grupo_9.viaje.model.entities.Viaje;
import arqui.web.grupo_9.viaje.model.clients.UsuarioClient;
import arqui.web.grupo_9.viaje.model.clients.MonopatinClient;
import arqui.web.grupo_9.viaje.repository.IViajeRepository;
import arqui.web.grupo_9.viaje.service.exceptions.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ViajeService {
    private IViajeRepository repository;
    //private UsuarioFeignClient usuarioClient;
    //private MonopatinFeignClient monopatinClient;
    private static double creditoDescontado;
    private static double kmsRecorridos;
    private static Viaje viajeGenerado;
    private static AtomicBoolean viajeEnCurso;

    public ViajeService(IViajeRepository repository) {
        this.repository = repository;
        //this.usuarioClient = usuarioClient;
        //this.monopatinClient = monopatinClient;
        viajeEnCurso = new AtomicBoolean(false);
    }

    public List<Viaje> findAll() {
        return this.repository.findAll();
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
        Viaje viaje = this.findById(idViaje);
        this.repository.delete(viaje);
        return true;
    }

    /*public boolean updateById(Long idViaje, Viaje viajeModified) {
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
    }*/

    public boolean generar(Long idUsuario, Long idMonopatin) {
        UsuarioClient usuario = new UsuarioClient();
        MonopatinClient monopatin = new MonopatinClient();

        if (usuario == null)
            throw new NotFoundUsuarioClientException("El usuario no está en el sistema", "No se pudo generar el viaje. Verifica los datos.", "high");
        if (monopatin == null)
            throw new NotFoundMonopatinClientException("El monopatín no está en el sistema", "No se pudo generar el viaje. Verifica el monopatín.", "high");

        //Chekear el credito del usuario
        //Habria que activar el monopatin?

        // Generar el viaje
        viajeGenerado = new Viaje(LocalDateTime.now(), idUsuario, idMonopatin);
        creditoDescontado = 0;
        kmsRecorridos = 0;

        // Activar la tarea programada
        viajeEnCurso.set(true);

        return true;
    }

    // Método programado que se ejecuta cada segundo para descontar crédito y aumentar kilómetros
    @Scheduled(fixedRate = 1000)
    public synchronized void actualizarViajeEnCurso() {
        if(viajeEnCurso.get()) {
            creditoDescontado += Viaje.PRECIO_BASE;
            kmsRecorridos += 1;
        }
    }

    public boolean finalizar() {
        if (!viajeEnCurso.get())
            throw new FinalizarViajeException("Se intentó finalizar un viaje que no fue generado aún", "Para finalizar un viaje, primero debes generarlo.", "high");

        viajeEnCurso.set(false);  // Detener la tarea programada
        viajeGenerado.setFechaFinViaje(LocalDateTime.now());
        viajeGenerado.setCostoTotal(creditoDescontado);
        viajeGenerado.setKmsRecorridos(kmsRecorridos);
        this.save(viajeGenerado);

        //setearle el credito al usuario
        return true;
    }

    public List<Long> getCantViajesOfMonopatinByAnio(int anio, int cantViajes) {
        return this.repository.getMonopatinesByCantViajesAndAnio(anio, cantViajes);
    }

    public double getTotalFacturadoByMesesOfAnio(int mesIni, int mesFin, int anio) {
        return this.repository.getTotalFacturadoByMesesOfAnio(mesIni, mesFin, anio);
    }
}
