package montepiedad.prestamo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import montepiedad.prestamo.error.TokenInvalidoException;
/*
 * implementacion de interfaz de tipio  TokenService
*/
@Service
public class TokenServiceImpl implements TokenService {

    /*
     * Se pasa como parametro la url del WSToken desde properties
    */
    @Value("${token.service.url}")
    private String tokenServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /*
     * llamar al api de Token y validar que sea correcto
     * @param token como cadena a validar 
    */
    @Override
    public void validarToken(String token) 
    {
        if (token == null || token.isBlank()) 
        {
            throw new TokenInvalidoException("Token requerido");
        }
        try 
        {
            String url = String.format("%s/validar", tokenServiceUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
            if (!HttpStatus.OK.equals(response.getStatusCode())) 
            {
                throw new TokenInvalidoException("Token invalido o expirado");
            }

        }
        catch (RestClientException ex) 
        {
        	 throw new TokenInvalidoException("Token incorrecto");
        }
    }
}
