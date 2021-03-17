package bo.com.tesla.recaudaciones.services;

import java.util.List;

import bo.com.tesla.administracion.entity.RecaudadorEntity;

public interface IRecaudadoraService {
	
	public RecaudadorEntity findRecaudadorByUserId(Long usuarioId);
	
	public RecaudadorEntity findByRecaudadorId(Long recaudadorId);

	public List<RecaudadorEntity> findRecaudadoresByEntidadId(Long entidadId);

}
