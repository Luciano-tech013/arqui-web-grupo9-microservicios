package arqui.web.grupo_9.service;

import arqui.web.grupo_9.clients.CuentaFeignClient;
import arqui.web.grupo_9.clients.MonopatinFeignClient;
import arqui.web.grupo_9.model.clients.CuentaMpDTO;
import arqui.web.grupo_9.model.entities.Viaje;
import arqui.web.grupo_9.model.clients.MonopatinDTO;
import arqui.web.grupo_9.model.clients.CuentaClient;
import arqui.web.grupo_9.repository.IViajeRepository;
import arqui.web.grupo_9.service.exceptions.FinalizarViajeException;
import arqui.web.grupo_9.service.exceptions.NotFoundMonopatinClientException;
import arqui.web.grupo_9.service.exceptions.NotFoundUsuarioClientException;
import arqui.web.grupo_9.service.exceptions.NotFoundViajeException;
import arqui.web.grupo_9.service.exceptions.*;
import feign.FeignException;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ViajeService {
    private IViajeRepository repository;
    private CuentaFeignClient cuentaClient;
    private MonopatinFeignClient monopatinClient;
    private static double creditoDescontado;
    private static double kmsRecorridos;
    private static Viaje viajeGenerado;
    private static AtomicBoolean viajeEnCurso;
    private static AtomicBoolean viajePausado;
    private static AtomicBoolean viajePausadoConRecargo;
    private static double nuevoPrecio;
    private static LocalDate fechaNueva;
    private CuentaMpDTO cuenta;
    private MonopatinDTO monopatin;

    public ViajeService(IViajeRepository repository, @Lazy CuentaFeignClient cuentaClient, @Lazy MonopatinFeignClient monopatinClient) {
        this.repository = repository;
        this.cuentaClient = cuentaClient;
        this.monopatinClient = monopatinClient;
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
        try {
            cuenta = cuentaClient.findById(idCuenta);
        } catch(FeignException.FeignClientException ex) {
            throw new NotFoundUsuarioClientException("El usuario no está en el sistema", "No se pudo generar el viaje. Verifica los datos.", "high");
        }

        try {
            monopatin = monopatinClient.findById(idMonopatin);
        } catch(FeignException.FeignClientException ex) {
            throw new NotFoundMonopatinClientException("El monopatín no está en el sistema", "No se pudo generar el viaje. Verifica el monopatín.", "high");
        }

        //Chekear el credito del usuario
        if(cuenta.getCredito() < 0)
            throw new CreditoInsuficienteException("La cuenta del usuario no tiene suficiente dinero para realizar un viaje", "No tienes suficiente dinero para realizar un via. Por favor, carga credito", "high");

        // Generar el viaje
        viajeGenerado = new Viaje(LocalDateTime.now(), idCuenta, idMonopatin);

        if(LocalDate.now().isEqual(fechaNueva) || LocalDate.now().isAfter(fechaNueva))
            viajeGenerado.setPrecio(nuevoPrecio);

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

        //aplicar credito descontado a la cuenta del usuario
        cuenta.setCredito(cuenta.getCredito() - creditoDescontado);

        //aplicar los kms recorridos al monopatin
        monopatin.setKmsRecorridos(monopatin.getKmsRecorridos() + kmsRecorridos);

        this.cuentaClient.save(cuenta);
        this.monopatinClient.save(monopatin);

        if(this.save(viajeGenerado))
            return this.findById(viajeGenerado.getIdViaje());

        return null;
    }

    public List<MonopatinDTO> getMonopatinesByCantViajesInOneYear(int anio, int cantViajes) {
        List<Long> idMonopatines = this.repository.getMonopatinesByCantViajesInOneYear(anio, cantViajes);

        List<MonopatinDTO> monopatinesObtenidos = new LinkedList<>();
        for(Long id : idMonopatines) {
            monopatinesObtenidos.add(monopatinClient.findById(id));
        }

        return monopatinesObtenidos;
    }

    public double getTotalFacturadoByMesesInOneYear(int mesIni, int mesFin, int anio) {
        try {
            return this.repository.getTotalFacturadoByMesesInOneYear(mesIni, mesFin, anio);
        } catch(Exception e) {
            throw new NotFoundFechaException("Los meses o el año enviado no estaban cargados en el sistema", "No se encontro ningun viaje registrado en la fecha de facturacion enviada. Por favor, envia otra fecha", "high");
        }
    }

    public boolean ajustarPrecioParaUnaFecha(LocalDate fecha, double precio) {
        nuevoPrecio = precio;
        fechaNueva = fecha;
        return true;
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
