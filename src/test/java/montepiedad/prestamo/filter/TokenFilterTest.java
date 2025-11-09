package montepiedad.prestamo.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;
import montepiedad.prestamo.dto.ErrorDto;
import montepiedad.prestamo.error.TokenInvalidoException;
import montepiedad.prestamo.service.TokenService;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TokenFilterTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenFilter tokenFilter;

    @Test
    void noautorizado() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/prestamos/calcular");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        tokenFilter.doFilter(request, response, chain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        String body = response.getContentAsString();
        assertFalse(body.isBlank());

        ErrorDto error = new ObjectMapper().readValue(body, ErrorDto.class);
        assertEquals("Debe de mandar un token", error.getError());
        assertEquals("Token obligatorio", error.getMessage());
    }

    @Test
    void tokeninvalido() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/prestamos/calcular");
        request.addHeader("Authorization", "Bearer token_invalido");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        doThrow(new TokenInvalidoException("expirado")).when(tokenService).validarToken("token_invalido");

        tokenFilter.doFilter(request, response, chain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        String body = response.getContentAsString();
        assertFalse(body.isBlank());

        ErrorDto error = new ObjectMapper().readValue(body, ErrorDto.class);
        assertEquals("Token invalido o expirado", error.getError());
        assertEquals("expirado", error.getMessage());
    }

    @Test
    void tokenvalido() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/prestamos/calcular");
        request.addHeader("Authorization", "Bearer token_ok");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        doNothing().when(tokenService).validarToken("token_ok");
        tokenFilter.doFilter(request, response, chain);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
