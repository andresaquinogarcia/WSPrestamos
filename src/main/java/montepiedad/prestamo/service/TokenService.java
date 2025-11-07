package montepiedad.prestamo.service;

/*
 * interfaz para validar token con el WSToken
*/
public interface TokenService 
{
    /*
     * metodo para validar token
     * @param token como cadena 
    */
    void validarToken( String token);
}
