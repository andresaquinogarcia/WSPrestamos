package montepiedad.prestamo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import montepiedad.prestamo.error.TokenInvalidoException;

class TokenServiceImplTest {

    @Test
    void tokennulo() {
        TokenServiceImpl service = new TokenServiceImpl();

        TokenInvalidoException ex = assertThrows(TokenInvalidoException.class,
                () -> service.validarToken(null));

        assertTrue(ex.getMessage().contains("Token requerido"));
    }

    @Test
    void tokenvacioi() {
        TokenServiceImpl service = new TokenServiceImpl();

        TokenInvalidoException ex = assertThrows(TokenInvalidoException.class,
                () -> service.validarToken(" "));

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
}
