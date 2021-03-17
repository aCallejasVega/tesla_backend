package bo.com.tesla.recaudaciones.services;

import java.util.List;
import java.util.Optional;

import bo.com.tesla.administracion.entity.DominioEntity;

public interface IDominioService {
	List<DominioEntity> findByDominio(String dominio);

}
