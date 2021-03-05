package bo.com.tesla.security.services;

import java.util.Date;
import java.util.List;


public interface ITransaccionCobrosService {
	public  List<Object[]> getDeudasforDate(Long entidadId,String recaudadorId, String estado, Date fechaInicio, Date fechaFin);
	public  List<Object[]> getDeudasforServicio(Long entidadId,String recaudadorId, String estado, Date fechaInicio, Date fechaFin);
	public  List<Object[]> getDeudasforTipoServicio(Long entidadId,String recaudadorId, String estado, Date fechaInicio, Date fechaFin);
	

}
