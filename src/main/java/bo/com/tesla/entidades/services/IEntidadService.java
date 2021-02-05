package bo.com.tesla.entidades.services;

import bo.com.tesla.administracion.entity.EntidadEntity;

public interface IEntidadService {
	
	public EntidadEntity findEntidadByUserId(Long usuarioId);

}
