package montepiedad.prestamo.service;

import montepiedad.prestamo.dto.PrestamoRequestDto;
import montepiedad.prestamo.dto.PrestamoResponseDto;

/*
* interfaz que controla las operaciones del servicio
* este expone el metodo calcularPrestamo 
*/
public interface PrestamoService 
{

	/*
	 * Metodo para calcular prestamo
	 * @param request de tipo PrestamoRequestDto contiene identificador del material y los gramos
	*/
    PrestamoResponseDto calcularPrestamo(PrestamoRequestDto request);

}
