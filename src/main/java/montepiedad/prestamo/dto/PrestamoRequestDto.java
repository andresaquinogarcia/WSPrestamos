package montepiedad.prestamo.dto;

/*
 * DTO de para calcular el prestamo.
*/
public class PrestamoRequestDto 
{
	/*
	 * Variables que identifican el material para calcular prestamo
	 * idMaterial - Identificador del material
	 * gramos - Peso en gramos del material
	*/
    private String idMaterial;
    private double gramos;

    
    /*
     * Declaracion de constructores 
    */
    public PrestamoRequestDto() {
    }

    public PrestamoRequestDto(String idMaterial, double gramos) {
        this.idMaterial = idMaterial;
        this.gramos = gramos;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getGramos() {
        return gramos;
    }

    public void setGramos(double gramos) {
        this.gramos = gramos;
    }

    @Override
    public String toString() {
        return "PrestamoRequestDto [idMaterial=" + idMaterial + ", gramos=" + gramos + "]";
    }
}
