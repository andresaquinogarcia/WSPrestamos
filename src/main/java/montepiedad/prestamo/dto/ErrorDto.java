package montepiedad.prestamo.dto;

/*
 * DTO para el manejo de excepciones en la respuesta del servicio
*/
public class ErrorDto 
{

	/*
	 * Variables que describen el error
	 * message - Mensaje descriptivo del error
	 * error - Mensaje breve del error
	 * status - Codigo de respuesta del error
	 * date - Fecha y hora del error
	*/
	
    private String message;
    private String error;
    private int status;
    private String date;
    
    /*
     * Declaración de constructores
    */
    public ErrorDto() {
    }

    public ErrorDto(String message, String error, int status, String date) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

	@Override
	public String toString() {
		return "ErrorDto [message=" + message + ", error=" + error + ", status=" + status + ", date=" + date + "]";
	}

}
