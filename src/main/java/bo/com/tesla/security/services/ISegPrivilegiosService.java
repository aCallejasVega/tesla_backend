package bo.com.tesla.security.services;

import java.util.List;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;

public interface ISegPrivilegiosService {
	
	public List<SegPrivilegioEntity> getMenuByUserId(Long usuarioId);
	

}
