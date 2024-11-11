package arqui.web.grupo_9.service;

/*import arqui.web.grupo_9.viaje.clients.MonopatinFeignClient;
import arqui.web.grupo_9.viaje.clients.UsuarioFeignClient;*/
import arqui.web.grupo_9.model.entities.Viaje;
import arqui.web.grupo_9.model.clients.MonopatinClient;
import arqui.web.grupo_9.model.clients.CuentaMPClient;
import arqui.web.grupo_9.repository.IViajeRepository;
import arqui.web.grupo_9.service.exceptions.FinalizarViajeException;
import arqui.web.grupo_9.service.exceptions.NotFoundMonopatinClientException;
import arqui.web.grupo_9.service.exceptions.NotFoundUsuarioClientException;
import arqui.web.grupo_9.service.exceptions.NotFoundViajeException;
import arqui.web.grupo_9.service.exceptions.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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
    private static AtomicBoolean viajePausado;
    private static AtomicBoolean viajePausadoConRecargo;

    public ViajeService(IViajeRepository repository) {
        this.repository = repository;
        //this.usuarioClient = usuarioClient;
        //this.monopatinClient = monopatinClient;
        viajeEnCurso = new AtomicBoolean(false);
        viajePausado = new AtomicBoolean(false);
        viajePausadoConRecargo = new AtomicBoolean(false);
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
        this.findById(idViaje);
        this.repository.deleteById(idViaje);
        return true;
    }

    public boolean generar(Long idCuenta, Long idMonopatin) {
        CuentaMPClient cuenta = new CuentaMPClient();
        MonopatinClient monopatin = new MonopatinClient();

        if (cuenta == null)
            throw new NotFoundUsuarioClientException("El usuario no está en el sistema", "No se pudo generar el viaje. Verifica los datos.", "high");
        if (monopatin == null)
            throw new NotFoundMonopatinClientException("El monopatín no está en el sistema", "No se pudo generar el viaje. Verifica el monopatín.", "high");

        //Chekear el credito del usuario
        //Habria que activar el monopatin?

        // Generar el viaje
        viajeGenerado = new Viaje(LocalDateTime.now(), idCuenta, idMonopatin);
        creditoDescontado = 0;
        kmsRecorridos = 0;

        // Activar la tarea programada
        viajeEnCurso.set(true);

        return true;
    }

    public boolean pausar() {
        viajePausado.set(true);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Si el viaje sigue estando pausado
                if(viajePausado.get())
                    viajePausadoConRecargo.set(true);
            }
        };

        //timer.schedule(task, 900000);
        timer.schedule(task, 15000);
        return true;
    }

    public boolean despausar() {
        if(viajePausadoConRecargo.get())
            viajePausadoConRecargo.set(false);

        viajePausado.set(false);
        return true;
    }

    @Scheduled(fixedRate = 1000)
    public synchronized void actualizarViajeEnCurso() {
        if (!viajeEnCurso.get())
            return;

        double incrementoCredito = Viaje.PRECIO_BASE;

        // Si el viaje está pausado, verifica si aplica recargo
        if (viajePausado.get()) {
            incrementoCredito += viajePausadoConRecargo.get() ? Viaje.PRECIO_RECARGO : 0;
        } else {
            // Solo aumenta kilómetros si el viaje no está pausado
            kmsRecorridos += 1;
        }

        creditoDescontado += incrementoCredito;
        System.out.println(creditoDescontado);
        System.out.println(kmsRecorridos);
    }


    public Viaje finalizar() {
        if (!viajeEnCurso.get())
            throw new FinalizarViajeException("Se intentó finalizar un viaje que no fue generado aún", "Para finalizar un viaje, primero debes generarlo.", "high");

        viajeEnCurso.set(false);  // Detener la tarea programada
        viajeGenerado.setFechaFinViaje(LocalDateTime.now());
        viajeGenerado.setCostoTotal(creditoDescontado);
        viajeGenerado.setKmsRecorridos(kmsRecorridos);

        //APLICAR CREDITO DESCONTADO AL USUARIO
        //APLICAR LOS KMS RECORRIDOS AL MONOPATIN
        if(this.save(viajeGenerado))
            return this.findById(viajeGenerado.getIdViaje());

        return null;
    }

    public List<Long> getMonopatinesByCantViajesInOneYear(int anio, int cantViajes) {
        return this.repository.getMonopatinesByCantViajesInOneYear(anio, cantViajes);
    }

    public double getTotalFacturadoByMesesInOneYear(int mesIni, int mesFin, int anio) {
        try {
            double total = this.repository.getTotalFacturadoByMesesInOneYear(mesIni, mesFin, anio);
            return total;
        } catch(Exception e) {
            throw new NotFoundFechaException("Los meses o el año enviado no estaban cargados en el sistema", "No se encontro ningun viaje registrado en la fecha de facturacion enviada. Por favor, envia otra fecha", "high");
        }
    }

    //METODOS PARA EL TESTING
    public AtomicBoolean isViajeEnCurso() {
        return viajeEnCurso;
    }

    public double getCreditoDescontado() {
        return creditoDescontado;
    }

    public double getKmsRecorridos() {
        return kmsRecorridos;
    }
}
