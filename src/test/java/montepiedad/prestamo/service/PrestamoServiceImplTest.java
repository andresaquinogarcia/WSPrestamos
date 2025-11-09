package montepiedad.prestamo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import montepiedad.prestamo.dto.PrestamoRequestDto;
import montepiedad.prestamo.dto.PrestamoResponseDto;
import montepiedad.prestamo.entity.MaterialEntity;
import montepiedad.prestamo.repositories.MaterialRepository;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    @Test
    void prestamovalido() {
        PrestamoRequestDto request = new PrestamoRequestDto("002", 10.0);
        MaterialEntity material = new MaterialEntity("002", "Oro 10k", 100.0);
        when(materialRepository.findById("002")).thenReturn(Optional.of(material));

        PrestamoResponseDto response = prestamoService.calcularPrestamo(request);

        assertNotNull(response);
        assertEquals("002", response.getIdMaterial());
        assertEquals("Oro 10k", response.getMaterial());
        assertEquals(10.0, response.getGramos());
        assertEquals(new BigDecimal("100.00"), response.getPrecioGramo());
        assertEquals("800.00", response.getMontoPrestamo());
    }

    @Test
    void sinmaterial() {
        PrestamoRequestDto request = new PrestamoRequestDto(null, 10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prestamoService.calcularPrestamo(request));

        assertEquals("El material es obligatorio.", ex.getMessage());
    }

    @Test
    void materialvacio() {
        PrestamoRequestDto request = new PrestamoRequestDto("  ", 10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prestamoService.calcularPrestamo(request));

        assertEquals("El material es obligatorio.", ex.getMessage());
    }

    @Test
    void gramoscero() {
        PrestamoRequestDto request = new PrestamoRequestDto("002", 0.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prestamoService.calcularPrestamo(request));

        assertEquals("Los gramos deben ser mayores a cero.", ex.getMessage());
    }

    @Test
    void materialnoencontrado() {
        // 001–006 son válidos; usamos 008 como inexistente
        PrestamoRequestDto request = new PrestamoRequestDto("008", 5.0);
        when(materialRepository.findById("008")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prestamoService.calcularPrestamo(request));

        assertEquals("Material no valido: 008", ex.getMessage());
        // si quieres ser menos estricto:
        // assertTrue(ex.getMessage().startsWith("Material no valido"));
    }
}
