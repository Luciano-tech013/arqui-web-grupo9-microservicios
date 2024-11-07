package arqui.web.grupo_9.viaje.controller;

import arqui.web.grupo_9.viaje.model.dto.ExcepcionDTO;
import arqui.web.grupo_9.viaje.service.exceptions.NotFoundViajeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase de manejo de excepciones para servicios de la aplicación.
 * <p>
 * Esta clase actúa como un interceptador de excepciones lanzadas dentro de los servicios,
 * permitiendo centralizar el manejo de errores específicos y proporcionando respuestas personalizadas al cliente.
 * Utiliza {@link RestControllerAdvice} para capturar excepciones de forma global en la aplicación.
 * </p>
 *
 * <p>
 * De esta forma se evita realizar verificaciones extras sobre los controladores. Con esta implementacion solo se retornan respuesta validas
 * </p>
 *
 * @see RestControllerAdvice
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Maneja la excepción {@code NotFoundViajeException}.
     * <p>
     * Este método intercepta la excepción {@code NotFoundViajeException} cuando un ID de viaje no es encontrado.
     * Se encarga de crear un DTO de excepcion que contiene un mensaje y un nivel de severidad, devolviendo esta información
     * al cliente junto con un código de estado 404 para indicar que el recurso no fue encontrado.
     * </p>
     *
     * @param ex La excepción {@code NotFoundViajeException} lanzada al no encontrar el viaje solicitado.
     *
     * @return {@code ResponseEntity<ExcepcionDTO>}: Un objeto especial de Spring Boot que envuelve el objeto de error de tipo
     * {@code ErrorDTO} y proporciona una respuesta estructurada en formato JSON, junto con el código de estado {@code HttpStatus.NOT_FOUND}.
     *
     * @see arqui.web.grupo_9.viaje.service.exceptions.NotFoundViajeException
     * @see ExcepcionDTO
     * @see HttpStatus#NOT_FOUND
     */
    @ExceptionHandler(value = NotFoundViajeException.class)
    public ResponseEntity<ExcepcionDTO> notFoundViajeExceptionHandler(NotFoundViajeException ex) {
        ExcepcionDTO error = new ExcepcionDTO(ex.getUserMessage(), ex.getSeverity());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
