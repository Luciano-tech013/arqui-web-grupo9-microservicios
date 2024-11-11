package arqui.web.grupo_9.service.exceptions;

/**
 * Excepción que representa un error de validación de una ubicación no válida en el sistema.
 * <p>
 * Esta excepción es lanzada cuando se detecta que una ubicación no cumple con los requisitos
 * o estándares esperados, y se hereda de la clase base {@link MyException}.
 * <p>
 * La clase incluye información detallada sobre el error:
 * - Un mensaje técnico para los registros internos del sistema.
 * - Un mensaje amigable para ser mostrado al usuario final.
 * - Un nivel de severidad que permite clasificar la importancia del error.
 *
 * @see MyException
 */
public class NotValidUbicacionException extends MyException {
    /**
     * Crea una nueva instancia de {@code MyException} con el mensaje de error técnico,
     * el mensaje amigable para el usuario y el nivel de severidad.
     *
     * @param message     Mensaje técnico para los registros internos del sistema.
     * @param userMessage Mensaje que será mostrado al usuario final.
     * @param severity    Nivel de severidad del error.
     */
    public NotValidUbicacionException(String message, String userMessage, String severity) {
        super(message, userMessage, severity);
    }
}
