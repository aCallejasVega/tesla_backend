package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.recaudaciones.dto.DominioDto;

import java.util.List;

public interface IDominioService {
    public List<DominioDto> getListDominios(String dominio);
    public List<DominioEntity> findByDominio(String dominio);
}
