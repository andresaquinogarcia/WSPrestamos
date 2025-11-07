package montepiedad.prestamo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import montepiedad.prestamo.dto.PrestamoRequestDto;
import montepiedad.prestamo.dto.PrestamoResponseDto;
import montepiedad.prestamo.entity.MaterialEntity;
import montepiedad.prestamo.repositories.MaterialRepository;

/*
 * implementacion de la interfaz PrestamoService
*/
@Service
public class PrestamoServiceImpl implements PrestamoService 
{

	/*
	 * Declaracion de constanstes
	*/
	private static final BigDecimal PORCENTAJE_PRESTAMO = BigDecimal.valueOf(0.8);
	
	/*
	 * Vairable de tipo MaterialRepository usando inyeccion de dependencia
	 */
	@Autowired
    private MaterialRepository materialRepository;

    /**
     * Calcular prestamo con base en material y gramos
     * Formula para calcular - gramos * precioGramoMaterial * PORCENTAJE_PRESTAMO (80%)
     * @param request objeto que contiene el id del material y los gramos
     */
    @Override
    public PrestamoResponseDto calcularPrestamo(PrestamoRequestDto request) 
    {

        if (request.getIdMaterial() == null || request.getIdMaterial().isBlank()) 
        {
            throw new IllegalArgumentException("El material es obligatorio.");
        }

        if (request.getGramos() <= 0) 
        {
            throw new IllegalArgumentException("Los gramos deben ser mayores a cero.");
        }

        MaterialEntity material = materialRepository.findById(request.getIdMaterial()).orElse(null);

        if (material == null) 
        {
            throw new IllegalArgumentException("Material no valido: " + request.getIdMaterial());
        }

        BigDecimal gramos = BigDecimal.valueOf(request.getGramos());
        BigDecimal precioGramo = BigDecimal.valueOf(material.getPrecioGramo());

        BigDecimal montoPrestamo = gramos.multiply(precioGramo).multiply(PORCENTAJE_PRESTAMO);

        return new PrestamoResponseDto(material.getIdMaterial(),material.getNombre(),request.getGramos(),precioGramo,montoPrestamo
        );
    }
}
