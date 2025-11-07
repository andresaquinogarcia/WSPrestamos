package montepiedad.prestamo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Entiity que leee la collecion materiales de MongoDB, donde 
 * contiene toda la informacion de cada material  
*/
@Document(collection = "materiales")
public class MaterialEntity 
{

	/*
	 * Variables que describen el material 
	 * idMaterial - Identificador de material
	 * nombre - Nombre del material
	 * precioGramo - Precio por gramo
	*/
    @Id
    private String idMaterial;
    private String nombre;
    private double precioGramo;

    /*
     * Declaracion de constructores 
    */
    public MaterialEntity() {
    }

    public MaterialEntity(String idMaterial, String nombre, double precioGramo) {
		super();
		this.idMaterial = idMaterial;
		this.nombre = nombre;
		this.precioGramo = precioGramo;
	}

	public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioGramo() {
        return precioGramo;
    }

    public void setPrecioGramo(double precioGramo) {
        this.precioGramo = precioGramo;
    }

	@Override
	public String toString() {
		return "MaterialEntity [idMaterial=" + idMaterial + ", nombre=" + nombre + ", precioGramo=" + precioGramo + "]";
	}

}
