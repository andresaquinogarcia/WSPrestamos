package montepiedad.prestamo.error;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import montepiedad.prestamo.dto.ErrorDto;

/*
 * Clase encargada de perzonalizar las posibles excepciones 
 * durante el flujo del servicio
*/
@RestControllerAdvice
public class ExceptionController 
{

	/*
	 * excepcion para errores de datos de entrada
	 * @param ex de tipo IllegalArgumentException
	*/
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgument(IllegalArgumentException ex) 
    {
        ErrorDto error = new ErrorDto();
        error.setDate(obtenerFecha());
        error.setError("Parametro invalido");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

	/*
	 * Excepcion para errores en general dentro del flujo del servicio
	 * @param ex de tipo Exception
	*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneral(Exception ex) 
    {
        ErrorDto error = new ErrorDto();
        error.setDate(obtenerFecha());
        error.setError("Error interno del servidor");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

	/*
	 * Excepcion para validacion de token
	 * @param ex de tipo TokenInvalidoException
	*/
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErrorDto> handleTokenInvalido(TokenInvalidoException ex) {
        ErrorDto error = new ErrorDto();
        error.setDate(obtenerFecha());
        error.setError("Token invalido o expirado");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
    /*
     * Metodo para obetener la fercha y hora
    */
    private String obtenerFecha() 
    {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zdt.format(formatter);
    }
}
