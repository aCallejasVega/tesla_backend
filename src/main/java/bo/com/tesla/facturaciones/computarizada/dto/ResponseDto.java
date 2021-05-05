package bo.com.tesla.facturaciones.computarizada.dto;


public class ResponseDto<T> {
    public Boolean status;
    public String message;
    public T result;
    public String report;

}
