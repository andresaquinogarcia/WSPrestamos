package montepiedad.prestamo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import montepiedad.prestamo.error.TokenInvalidoException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class TokenServiceImplTest {

    @Test
    void tokennulo() {
        TokenServiceImpl service = new TokenServiceImpl();
        TokenInvalidoException ex = assertThrows(TokenInvalidoException.class,() -> service.validarToken(null));
        assertTrue(ex.getMessage().contains("Token requerido"));
    }

    @Test
    void tokenvacioi() {
        TokenServiceImpl service = new TokenServiceImpl();
        TokenInvalidoException ex = assertThrows(TokenInvalidoException.class,() -> service.validarToken(" "));
        assertTrue(ex.getMessage().contains("Token requerido"));
    }

    @Test
    void tokeninalcanzable() throws Exception {
        TokenServiceImpl service = new TokenServiceImpl();
        Field f = TokenServiceImpl.class.getDeclaredField("tokenServiceUrl");
        f.setAccessible(true);
        f.set(service, "http://localhost:9999/api/token");
        TokenInvalidoException ex = assertThrows(TokenInvalidoException.class,() -> service.validarToken("token_incorrecto"));
        assertTrue(ex.getMessage().contains("Token incorrecto"));
    }
    
    @Test
    void tokenvalido() throws Exception {
        TokenServiceImpl service = new TokenServiceImpl();
        Field urlField = TokenServiceImpl.class.getDeclaredField("tokenServiceUrl");
        urlField.setAccessible(true);
        urlField.set(service, "http://dummy/api/token");
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        Field rtField = TokenServiceImpl.class.getDeclaredField("restTemplate");
        rtField.setAccessible(true);
        rtField.set(service, restTemplateMock);
        ResponseEntity<String> respuestaOk = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplateMock.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
            .thenReturn(respuestaOk);
        assertDoesNotThrow(() -> service.validarToken("token_valido"));
    }

    @Test
    void tokenexpirado() throws Exception {
        TokenServiceImpl service = new TokenServiceImpl();
        Field urlField = TokenServiceImpl.class.getDeclaredField("tokenServiceUrl");
        urlField.setAccessible(true);
        urlField.set(service, "http://dummy/api/token");
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        Field rtField = TokenServiceImpl.class.getDeclaredField("restTemplate");
        rtField.setAccessible(true);
        rtField.set(service, restTemplateMock);
        ResponseEntity<String> respuestaNoOk = new ResponseEntity<>("NO OK", HttpStatus.UNAUTHORIZED);
        when(restTemplateMock.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
            .thenReturn(respuestaNoOk);
        TokenInvalidoException ex = assertThrows(
                TokenInvalidoException.class,
                () -> service.validarToken("token_invalido"));
        assertTrue(ex.getMessage().contains("Token invalido o expirado"));
    }

}
