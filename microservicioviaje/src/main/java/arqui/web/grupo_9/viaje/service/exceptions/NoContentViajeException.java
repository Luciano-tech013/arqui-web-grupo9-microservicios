package arqui.web.grupo_9.viaje.service.exceptions;

import lombok.Getter;

/**
 * Excepción personalizada que indica la ausencia de contenido en una respuesta relacionada con un viaje.
 * <p>
 * {@code NoContentViajeException} se lanza cuando no hay contenido relevante para devolver en la respuesta a una solicitud de viaje.
 * Hereda de {@link MyException} y permite proporcionar un mensaje amigable al usuario junto con un nivel de severidad.
 * Esta excepción se asocia típicamente con el código de estado HTTP 204 (No Content).
 * </p>
 *
 * <ul>
 *     <li>{@code message}: Mensaje técnico de error para los registros internos.</li>
 *     <li>{@code userMessage}: Mensaje informativo para el usuario sobre la ausencia de contenido.</li>
 *     <li>{@code severity}: Nivel de severidad del error, proporcionando contexto adicional sobre su impacto en el sistema.</li>
 * </ul>
 *
 * <p>
 * Se utiliza cuando una solicitud de viaje no genera ningún contenido útil para devolver, lo cual es informado al
 * usuario de forma clara. Es manejada generalmente por un controlador de excepciones global que intercepta y responde
 * con detalles específicos para el frontend.
 * </p>
 *
 * @see MyException
 * @see arqui.web.grupo_9.viaje.controller.ControllerAdvice
 */
@Getter
public class NoContentViajeException extends MyException {

    /**
     * Crea una nueva instancia de {@code NoContentViajeException} con el mensaje de error técnico,
     * el mensaje amigable para el usuario y el nivel de severidad.
     *
     * @param message Mensaje técnico para los registros internos del sistema.
     * @param userMessage Mensaje que será mostrado al usuario final sobre la falta de contenido.
     * @param severity Nivel de severidad del error.
     */
    public NoContentViajeException(String message, String userMessage, String severity) {
        super(message, userMessage, severity);
    }
}

