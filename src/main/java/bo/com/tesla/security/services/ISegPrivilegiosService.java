package bo.com.tesla.security.services;

import java.util.List;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.security.dto.OperacionesDto;

public interface ISegPrivilegiosService {
	
	public List<SegPrivilegioEntity> getMenuByUserId(Long usuarioId);
	
	public  List<OperacionesDto> getOperaciones(String login, String tabla);

	public List<OperacionesDto> getOperacionesByEstadoInicial(String login, String tabla, String estadoInicial);

}
