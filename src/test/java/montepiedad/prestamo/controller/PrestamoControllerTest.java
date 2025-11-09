package montepiedad.prestamo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import montepiedad.prestamo.dto.PrestamoRequestDto;
import montepiedad.prestamo.dto.PrestamoResponseDto;
import montepiedad.prestamo.service.PrestamoService;

class PrestamoControllerTest {

    private PrestamoService prestamoService;
    private PrestamoController controller;

    @BeforeEach
    void setUp() throws Exception {
        prestamoService = Mockito.mock(PrestamoService.class);
        controller = new PrestamoController();

        // Inyectamos el mock en el campo @Autowired usando reflexión
        Field f = PrestamoController.class.getDeclaredField("prestamoService");
        f.setAccessible(true);
        f.set(controller, prestamoService);
    }

    @Test
    void prestamovalido_devuelve200YBodyCorrecto() {
        PrestamoResponseDto respuestaMock = new PrestamoResponseDto(
                "002",
                "Oro 10k",
                10.0,
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(800.0)
        );

        when(prestamoService.calcularPrestamo(any(PrestamoRequestDto.class)))
                .thenReturn(respuestaMock);

        PrestamoRequestDto request = new PrestamoRequestDto("002", 10.0);

        ResponseEntity<PrestamoResponseDto> responseEntity = controller.calcularPrestamo(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("002", responseEntity.getBody().getIdMaterial());
        assertEquals("Oro 10k", responseEntity.getBody().getMaterial());
        assertEquals(10.0, responseEntity.getBody().getGramos());
        assertEquals(new BigDecimal("100.00"), responseEntity.getBody().getPrecioGramo());
        assertEquals("800.00", responseEntity.getBody().getMontoPrestamo());
    }

    @Test
    void estatus_devuelve200YMensaje() {
        ResponseEntity<String> response = controller.health();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Servicio disponible", response.getBody());
    }
}
