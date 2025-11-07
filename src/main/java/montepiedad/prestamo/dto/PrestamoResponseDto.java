package montepiedad.prestamo.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/*
 * DTO de respuesta para con el calculo realizado del prestamo.
*/
public class PrestamoResponseDto 
{

	/*
	 * Variables que describen el resultado del calculo del prestamo
	 * idMaterial - Identificador del material
	 * material - Nombre del material
	 * gramos - Peso en gramos del material
	 * precioGramo - Precio por gramo del material
	 * montoPrestamo - Monto calculado del prestamo
	*/
    private String idMaterial;
    private String material;
    private double gramos;
    private BigDecimal precioGramo;
    private BigDecimal montoPrestamo;

    /*
     * Declaracion de constructores 
    */
    public PrestamoResponseDto() {
    }

    
    public PrestamoResponseDto(String idMaterial, String material, double gramos, BigDecimal precioGramo,
			BigDecimal montoPrestamo) {
		super();
		this.idMaterial = idMaterial;
		this.material = material;
		this.gramos = gramos;
		this.precioGramo = precioGramo;
		this.montoPrestamo = montoPrestamo;
	}

	public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getGramos() {
        return gramos;
    }

    public void setGramos(double gramos) {
        this.gramos = gramos;
    }

    public BigDecimal getPrecioGramo() {
        if (precioGramo == null) {
            return null;
        }
        return precioGramo.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPrecioGramo(BigDecimal precioGramo) {
        this.precioGramo = precioGramo;
    }

    public String getMontoPrestamo() {
        if (montoPrestamo == null) {
            return null;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "MX"));
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(montoPrestamo);
    }

    public void setMontoPrestamo(BigDecimal montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

	@Override
	public String toString() {
		return "PrestamoResponseDto [idMaterial=" + idMaterial + ", material=" + material + ", gramos=" + gramos
				+ ", precioGramo=" + precioGramo + ", montoPrestamo=" + montoPrestamo + "]";
	}

}
