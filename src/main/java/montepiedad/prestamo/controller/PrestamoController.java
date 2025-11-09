package montepiedad.prestamo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import montepiedad.prestamo.dto.PrestamoRequestDto;
import montepiedad.prestamo.dto.PrestamoResponseDto;
import montepiedad.prestamo.service.PrestamoService;

/*
 * controller que expone los servicios disponibles para el calculo de prestamo 
*/
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PrestamoController 
{

	/*
	 * Vairable de tipo PrestamoService usando inyeccion de dependencia
	 */
	@Autowired
    private PrestamoService prestamoService;

    /*
     * Servicio para calcular posible prestamo.
     * @param request de tipo  PrestamoRequestDto  que contiene el id y los gramos del material a calcular
    */
    @PostMapping("/prestamos/calcular")
    public ResponseEntity<PrestamoResponseDto> calcularPrestamo(@RequestBody PrestamoRequestDto request) 
    {
        PrestamoResponseDto response = prestamoService.calcularPrestamo(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Servicio para probar el estado del api  
    */
    @GetMapping("/prestamos/status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Servicio disponible");
    }
}
