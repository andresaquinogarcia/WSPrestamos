package montepiedad.prestamo.error;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import montepiedad.prestamo.dto.ErrorDto;

class ExceptionControllerTest {

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    void parametroincalido() {
        IllegalArgumentException ex = new IllegalArgumentException("dato invalido");

        ResponseEntity<ErrorDto> response = exceptionController.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Parametro invalido", response.getBody().getError());
        assertEquals("dato invalido", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getDate());
    }

    @Test
    void errorinterno() {
        Exception ex = new Exception("fallo general");

        ResponseEntity<ErrorDto> response = exceptionController.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error interno del servidor", response.getBody().getError());
        assertEquals("fallo general", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getDate());
    }

    @Test
    void tokennovalido() {
        TokenInvalidoException ex = new TokenInvalidoException("token expirado");

        ResponseEntity<ErrorDto> response = exceptionController.handleTokenInvalido(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Token invalido o expirado", response.getBody().getError());
        assertEquals("token expirado", response.getBody().getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getDate());
    }
}
