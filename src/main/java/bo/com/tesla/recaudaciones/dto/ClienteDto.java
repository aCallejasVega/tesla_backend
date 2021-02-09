package bo.com.tesla.recaudaciones.dto;

public class ClienteDto {

    private String codigoCliente;
    private String nombreCliente;
    private String nroDocumento;

    public ClienteDto() {
    }

    public ClienteDto(String codigoCliente, String nombreCliente, String nroDocumento) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

}
