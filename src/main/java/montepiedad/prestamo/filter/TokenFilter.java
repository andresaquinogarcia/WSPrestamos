package montepiedad.prestamo.filter;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import montepiedad.prestamo.dto.ErrorDto;
import montepiedad.prestamo.error.TokenInvalidoException;
import montepiedad.prestamo.service.TokenService;

/*
 * filter encargado de validar el token antes de llegar al controller
*/
@Component
public class TokenFilter extends OncePerRequestFilter {

	/*
	 * delcaracion de constantes
	 */
    private static final String HEADER_TOKEN = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    /*
	 * Vairable de tipo TokenService usando inyeccion de dependencia
	 */
    @Autowired
    private TokenService tokenService;

    /*
     * filtrado de solicitudes y negacion de las mismas
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if (!path.startsWith("/api/prestamos/calcular")) 
        {
            return true;
        }

        if ("/api/prestamos/status".equals(path)) 
        {
            return true;
        }

        return false;
    }
    /*
     * validar si el token es correcto y no ha expirado 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException 
    {

        String tokenHeader = request.getHeader(HEADER_TOKEN);
        String token = null;
        if (tokenHeader == null || !tokenHeader.startsWith(BEARER_PREFIX)) 
        {
            ErrorDto error = new ErrorDto();
            error.setDate(obtenerFecha());
            error.setError("Debe de mandar un token");
            error.setMessage("Token obligatorio");
            error.setStatus(HttpStatus.UNAUTHORIZED.value());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);

            return;
        }
        else 
        {
            token = tokenHeader.substring(BEARER_PREFIX.length());
        }

        try 
        {
            tokenService.validarToken(token);
        }
        catch (TokenInvalidoException ex) 
        {
            ErrorDto error = new ErrorDto();
            error.setDate(obtenerFecha());
            error.setError("Token invalido o expirado");
            error.setMessage(ex.getMessage());
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);

            return;
        }
        filterChain.doFilter(request, response);
    }

    
    private String obtenerFecha() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zdt.format(formatter);
    }

}
